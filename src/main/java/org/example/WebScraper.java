package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.example.model.ProductRyzenCPU;
import org.example.repository.ProductRepository;
import org.example.scraping.ProductScraper;
import org.example.scraping.ProductScraperFactory;
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
public class WebScraper implements CommandLineRunner {

    private static final Logger logger = Logger.getLogger(WebScraper.class.getName());

    @Autowired
    private ProductRepository productRepository;

    private static final int THREAD_POOL_SIZE = 10;

    @Override
    public void run(String... args) {
        WebDriverManager.chromedriver().setup();
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

        try {
            String baseUrl = "https://www.newegg.com/Processors-Desktops/SubCategory/ID-343?PageSize=96";
            int page = 1;
            boolean hasNextPage = true;

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

                        // Use the factory to obtain the appropriate scraper
                        // Create a factory and use it to obtain the appropriate scraper
                        ProductScraperFactory scraperFactory = new ProductScraperFactory(productRepository);
                        ProductScraper scraper = scraperFactory.getScraper(productUrl);
                        if (scraper != null) {
                            scraper.setProductUrl(productUrl); // Set the product URL
                            // Submit the scraper to the executor service
                            executorService.submit(() -> {
                                ProductRyzenCPU product = scraper.call();
                                if (product != null) {
                                    scraper.saveProduct(product);
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

                    page++;
                }

                driver.quit();
                logger.info("Browser closed");
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred while fetching the webpage", e);
        } finally {
            executorService.shutdown();
        }
    }
}
