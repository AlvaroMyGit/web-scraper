package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.example.model.ProductIntelCPU;
import org.example.model.ProductRyzenCPU;
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class WebScraper<T> implements CommandLineRunner {

    private static final Logger logger = Logger.getLogger(WebScraper.class.getName());

    @Autowired
    private RyzenProductRepository ryzenProductRepository;

    @Autowired
    private IntelProductRepository intelProductRepository;

    private final Deque<String> urlQueue = new LinkedList<>();

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

    // Semaphore to control access to the WebDriver
    private final Semaphore semaphore = new Semaphore(1);

    @Override
    public void run(String... args) throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        // Use single-thread executor for sequential execution
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        try {
            String baseUrl = "https://www.newegg.com/Processors-Desktops/SubCategory/ID-343?PageSize=96";
            int page = 1;
            boolean hasNextPage = true;

            ProductScraperFactory<ProductRyzenCPU> ryzenScraperFactory = new RyzenProductScraperFactory(ryzenProductRepository);
            ProductScraperFactory<ProductIntelCPU> intelScraperFactory = new IntelProductScraperFactory(intelProductRepository);

            while (hasNextPage) {
                String url = baseUrl + "&Page=" + page;
                logger.info("Fetching URL: " + url);


                // Randomly select a user-agent string
                String userAgent = getRandomUserAgent();

                ChromeOptions options = new ChromeOptions();
                options.addArguments("--user-agent=" + userAgent);

                WebDriver driver = new ChromeDriver(options);

                // Log WebDriver activities
                // Path to log file
                System.setProperty("webdriver.chrome.logfile", "C:\\Users\\Álvaro\\Documents\\chromedriver.log");
                System.setProperty("webdriver.chrome.verboseLogging", "true");

                try {
                    driver.get(url);
                    logger.info("Page loaded successfully");

                    List<WebElement> productRows = driver.findElements(By.cssSelector(".item-cell"));
                    if (productRows.isEmpty()) {
                        hasNextPage = false;
                        logger.info("No more products found, ending pagination.");
                    } else {
                        synchronized (this) {
                            for (WebElement row : productRows) {
                                String productUrl = row.findElement(By.cssSelector(".item-title")).getAttribute("href");
                                logger.info("Fetching URL from table: " + productUrl);
                                urlQueue.add(productUrl);
                                logger.info("Adding product URL to the Deque List: " + productUrl);

                                // Determine the brand of the CPU
                                String brand = getBrandFromUrl(productUrl);

                                // Get the appropriate scraper based on the brand
                                @SuppressWarnings("rawtypes") final ProductScraper scraper;
                                if ("amd".equalsIgnoreCase(brand)) {
                                    scraper = ryzenScraperFactory.getScraper(productUrl);
                                } else if ("intel".equalsIgnoreCase(brand)) {
                                    scraper = intelScraperFactory.getScraper(productUrl);
                                } else {
                                    // Skip if brand is neither AMD nor Intel
                                    continue;
                                }

                                if (scraper != null) {
                                    // Set the product URL
                                    scraper.setProductUrl(productUrl);
                                    // Final or effectively final variable
                                    final ProductScraper<T> finalScraper = scraper;

                                    // Acquire the semaphore
                                    semaphore.acquire();

                                    // Submit the scraper to the executor service
                                    executorService.submit(() -> {
                                        try {
                                            T product = finalScraper.call();
                                            if (product != null) {
                                                finalScraper.saveProduct(product);
                                            }
                                        } finally {
                                            semaphore.release();
                                        }
                                    });
                                }
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
            while (!urlQueue.isEmpty()) {
                String currentUrl = urlQueue.poll(); // Dequeue URL from the queue
                logger.info("Scraping product from URL: " + currentUrl);
            }
        } finally {
            System.out.println("Scraping complete");
            executorService.shutdown();
        }
    }

    private String getRandomUserAgent() {
        Random random = new Random();
        int index = random.nextInt(USER_AGENTS.size());
        return USER_AGENTS.get(index);
    }

    // Method to extract brand from URL
    private String getBrandFromUrl(String productUrl) {
        if (productUrl.contains("amd")) {
            return "amd";
        } else if (productUrl.contains("intel")) {
            return "intel";
        } else {
            return ""; // Handle other cases
        }
    }
}
