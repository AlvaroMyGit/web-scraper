package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.example.model.ProductIntelCPU;
import org.example.model.ProductRyzenCPU;
import org.example.repository.CategoryRepository;
import org.example.repository.IntelProductRepository;
import org.example.repository.RyzenProductRepository;
import org.example.scraping.IntelProductScraperFactory;
import org.example.scraping.ProductScraper;
import org.example.scraping.ProductScraperFactory;
import org.example.scraping.RyzenProductScraperFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class WebScraper<T> implements CommandLineRunner {

    private static final Logger logger = Logger.getLogger(WebScraper.class.getName());

    @Autowired
    private RyzenProductRepository ryzenProductRepository;

    @Autowired
    private IntelProductRepository intelProductRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private final Set<String> processedUrls = new HashSet<>();
    private final BlockingQueue<String> urlQueue = new LinkedBlockingQueue<>();

    private static final List<String> USER_AGENTS = Arrays.asList(
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/94.0.4606.61 Safari/537.36",
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/93.0.4577.82 Safari/537.36",
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.131 Safari/537.36",
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:100.0) Gecko/20100101 Firefox/100.0",
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Edge/100.0.10586.0 Safari/537.36",
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.93 Safari/537.36 Edg/90.0.818.62",
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:91.0) Gecko/20100101 Firefox/91.0",
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4298.0 Safari/537.36",
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.83 Safari/537.36",
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36"
    );

    private final Semaphore semaphore = new Semaphore(1);

    @Override
    public void run(String... args) {

        WebDriverManager.chromedriver().setup();

        ExecutorService scrapingExecutor = Executors.newSingleThreadExecutor();
        ScheduledExecutorService urlFetchingExecutor = Executors.newScheduledThreadPool(1);

        ProductScraperFactory<ProductRyzenCPU> ryzenScraperFactory = new RyzenProductScraperFactory(ryzenProductRepository, categoryRepository);
        ProductScraperFactory<ProductIntelCPU> intelScraperFactory = new IntelProductScraperFactory(intelProductRepository, categoryRepository);

        urlFetchingExecutor.scheduleWithFixedDelay(() -> {
            try {
                fetchProductUrls();
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Error fetching URLs", e);
            }
        }, 0, 1, TimeUnit.SECONDS);

        scrapingExecutor.submit(() -> {
            while (true) {
                try {
                    String productUrl = urlQueue.take();
                    if (productUrl == null) continue;

                    String brand = getBrandFromUrl(productUrl);

                    @SuppressWarnings("rawtypes")
                    final ProductScraper scraper;
                    if ("amd".equalsIgnoreCase(brand)) {
                        scraper = ryzenScraperFactory.getScraper(productUrl);
                    } else if ("intel".equalsIgnoreCase(brand)) {
                        scraper = intelScraperFactory.getScraper(productUrl);
                    } else {
                        continue;
                    }

                    if (scraper != null) {
                        scraper.setProductUrl(productUrl);
                        logger.info("Set the Product URL for the scraper: " + productUrl);
                        final ProductScraper<T> finalScraper = scraper;

                        semaphore.acquire();

                        try {
                            T product = finalScraper.call();
                            if (product != null) {
                                finalScraper.saveProduct(product);
                            }
                        } catch (Exception e) {
                            logger.log(Level.SEVERE, "Error scraping or saving product data from URL: " + productUrl, e);
                        } finally {
                            semaphore.release();
                        }

                        // Introduce a random delay between product scrapes
                        try {
                            Thread.sleep(new Random().nextInt(1000) + 500);
                        } catch (InterruptedException e) {
                            logger.log(Level.WARNING, "Interrupted during delay", e);
                            Thread.currentThread().interrupt();
                        }
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                scrapingExecutor.shutdown();
                scrapingExecutor.awaitTermination(10, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                urlFetchingExecutor.shutdownNow();
            }
        }));
    }

    private void fetchProductUrls() {
        String baseUrl = "https://www.newegg.com/Processors-Desktops/SubCategory/ID-343?PageSize=96";
        int page = 1;
        boolean hasNextPage = true;

        while (hasNextPage) {
            String url = baseUrl + "&Page=" + page;
            logger.info("Fetching URL: " + url);

            String userAgent = getRandomUserAgent();

            ChromeOptions options = new ChromeOptions();
            options.addArguments("--user-agent=" + userAgent);
            options.addArguments("--headless");
            options.setExperimentalOption("useAutomationExtension", false);
            options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));

            WebDriver driver = new ChromeDriver(options);

            try {
                driver.get(url);
                logger.info("Page loaded successfully");

                List<WebElement> productRows = driver.findElements(By.cssSelector(".item-cell"));
                if (productRows.isEmpty()) {
                    hasNextPage = false;
                    logger.info("No more products found, ending pagination.");
                } else {
                    for (WebElement row : productRows) {
                        String productUrl = row.findElement(By.cssSelector(".item-title")).getAttribute("href");
                        if (processedUrls.add(productUrl)) {
                            logger.info("Adding product URL to the queue: " + productUrl);
                            urlQueue.put(productUrl);
                        }
                    }
                }
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Error during scraping", e);
            } finally {
                driver.quit();
            }
            page++;
        }
    }

    private String getRandomUserAgent() {
        Random random = new Random();
        int index = random.nextInt(USER_AGENTS.size());
        return USER_AGENTS.get(index);
    }

    private String getBrandFromUrl(String productUrl) {
        if (productUrl.contains("amd")) {
            return "amd";
        } else if (productUrl.contains("intel")) {
            return "intel";
        } else {
            return "";
        }
    }
}
