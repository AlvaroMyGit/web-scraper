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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class WebScraper<T> implements CommandLineRunner {

    private static final Logger logger = Logger.getLogger(WebScraper.class.getName());

    @Autowired
    private RyzenProductRepository ryzenProductRepository;

    @Autowired
    private IntelProductRepository intelProductRepository;

    private static final int THREAD_POOL_SIZE = 10;

    @Override
    public void run(String... args) throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

        try {
            String baseUrl = "https://www.newegg.com/Processors-Desktops/SubCategory/ID-343?PageSize=96";
            int page = 1;
            boolean hasNextPage = true;

            ProductScraperFactory<ProductRyzenCPU> ryzenScraperFactory = new RyzenProductScraperFactory(ryzenProductRepository);
            ProductScraperFactory<ProductIntelCPU> intelScraperFactory = new IntelProductScraperFactory(intelProductRepository);

            while (hasNextPage) {
                String url = baseUrl + "&Page=" + page;
                logger.info("Fetching URL: " + url);

                WebDriver driver = new ChromeDriver();
                driver.get(url);
                logger.info("Page loaded successfully");

                List<WebElement> productRows = driver.findElements(By.cssSelector(".item-cell"));
                if (productRows.isEmpty()) {
                    hasNextPage = false;
                    logger.info("No more products found, ending pagination.");
                } else {
                    for (WebElement row : productRows) {
                        String productUrl = row.findElement(By.cssSelector(".item-title")).getAttribute("href");
                        logger.info("Scraping product from URL: " + productUrl);

                        // Determine the brand of the CPU
                        String brand = getBrandFromUrl(productUrl);

                        // Get the appropriate scraper based on the brand
                        @SuppressWarnings("rawtypes")
                        final ProductScraper scraper;
                        if ("amd".equalsIgnoreCase(brand)) {
                            Thread.sleep(3000);
                            scraper = ryzenScraperFactory.getScraper(productUrl);
                        } else if ("intel".equalsIgnoreCase(brand)) {
                            scraper = intelScraperFactory.getScraper(productUrl);
                        } else {
                            continue; // Skip if brand is neither AMD nor Intel
                        }

                        if (scraper != null) {
                            scraper.setProductUrl(productUrl); // Set the product URL
                            final ProductScraper<T> finalScraper = scraper; // Final or effectively final variable
                            // Submit the scraper to the executor service
                            executorService.submit(() -> {
                                T product = finalScraper.call();
                                if (product != null) {
                                    finalScraper.saveProduct(product);
                                }
                            });
                            try {
                                Thread.sleep(4000); // Sleep for 4 seconds (adjust as needed)
                            } catch (InterruptedException e) {
                                logger.log(Level.WARNING, "Thread sleep interrupted", e);
                                Thread.currentThread().interrupt(); // Preserve interrupted status
                            }
                        }
                    }
                }
            }
        } finally {
            System.out.println("Yup");
        }
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
