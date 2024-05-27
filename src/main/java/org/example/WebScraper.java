package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.example.model.Product;
import org.example.repository.ProductRepository;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
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

                Thread.sleep(5000); // Adjust the wait time as needed
                logger.info("Waited for 5 seconds to load the page");

                List<WebElement> productRows = driver.findElements(By.cssSelector(".item-cell"));
                if (productRows.isEmpty()) {
                    hasNextPage = false;
                    logger.info("No more products found, ending pagination.");
                } else {
                    for (WebElement row : productRows) {
                        String productUrl = row.findElement(By.cssSelector(".item-title")).getAttribute("href");

                        Thread.sleep(10000);
                        logger.info("Scraping product from URL: " + productUrl);

                        Callable<Product> task = new ProductScraperTask(productUrl, productRepository);
                        Future<Product> future = executorService.submit(task);
                        Product product = future.get(); // This will block until the task completes
                        if (product != null) {
                            logger.info("Product to be saved: " + product);
                            logger.info("Product details: " + product.getName() + ", " + product.getPrice() + ", " +
                                    product.getManufacturer() + ", " + product.getModel() + ", " + product.getSocket());
                            productRepository.save(product);
                            logger.info("Product saved: " + product.getName());
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

    private static class ProductScraperTask implements Callable<Product> {
        private static final Logger logger = Logger.getLogger(ProductScraperTask.class.getName());
        private String productUrl;
        private ProductRepository productRepository;

        public ProductScraperTask(String productUrl, ProductRepository productRepository) {
            this.productUrl = productUrl;
            this.productRepository = productRepository;
        }

        @Override
        public Product call() {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless");  // Run in headless mode for better performance
            WebDriver driver = new ChromeDriver(options);

            try {
                driver.get(productUrl);
                logger.info("Navigated to product URL: " + productUrl);

                // Scroll down the page to load additional content
                ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");

                // Wait for stability after scrolling
                //Thread.sleep(2000); // Adjust the wait time as needed
                logger.info("Waited for stability after scrolling");

                // Locate the product details section
                WebElement productDetails = driver.findElement(By.xpath("//*[@id='product-details']"));

                // Scroll to the product details section
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", productDetails);
                //Thread.sleep(1000); // Small wait to ensure scroll has completed

                // Scroll a bit further down to ensure the target element is in view
                ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 100)");

                // Click on the target element within the product details section
                WebElement elementToClick = productDetails.findElement(By.xpath("//*[@id=\"product-details\"]/div[1]/div[2]"));
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", elementToClick);
                Thread.sleep(2000); // Wait for the specs tab content to load
                logger.info("Clicked 'Specs' tab");

                // Now locate the elements under the "Specs" tab
                String name = driver.findElement(By.xpath("//*[@id=\"product-details\"]/div[2]/div[2]/table[2]/tbody/tr[4]/td")).getText();
                logger.info("Name: " + name);

                String price = driver.findElement(By.cssSelector(".price-current")).getText();
                logger.info("Price: " + price);

                String manufacturer = driver.findElement(By.xpath("//*[@id=\"product-details\"]/div[2]/div[2]/table[2]/tbody/tr[1]/td")).getText();
                logger.info("Manufacturer: " + manufacturer);

                String model = driver.findElement(By.xpath("//*[@id=\"product-details\"]/div[2]/div[2]/table[2]/tbody/tr[5]/td")).getText();
                logger.info("Model: " + model);

                String socket = driver.findElement(By.xpath("//*[@id=\"product-details\"]/div[2]/div[2]/table[3]/tbody/tr[1]/td")).getText();
                logger.info("Socket: " + socket);

                logger.info("Scraped product details");
                return new Product()
            } catch (NoSuchElementException e) {
                logger.log(Level.SEVERE, "Element not found: " + e.getMessage());
                return null; // Or handle the error as needed
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Error scraping product data from URL: " + productUrl, e);
                return null; // Or handle the error as needed
            } finally {
                driver.quit();
                logger.info("Driver quit");
            }
        }
    }
}
