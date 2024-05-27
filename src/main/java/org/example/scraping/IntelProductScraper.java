package org.example.scraping;

import org.example.model.ProductRyzenCPU;
import org.example.repository.ProductRepository;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class IntelProductScraper implements ProductScraper {

    private static final Logger logger = Logger.getLogger(IntelProductScraper.class.getName());
    private String productUrl;
    private final ProductRepository productRepository;

    public IntelProductScraper(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductRyzenCPU call() {
        // Initialize ChromeOptions to configure WebDriver
        ChromeOptions options = new ChromeOptions();
        // Run WebDriver in headless mode for better performance
        options.addArguments("--headless");

        // Initialize WebDriver to interact with the web page
        WebDriver driver = new ChromeDriver(options);

        try {
            // Navigate to the Intel product URL
            driver.get(productUrl);
            // Log the navigation event
            logger.info("Navigated to product URL: " + productUrl);

            // Scroll down the page to load additional content if necessary
            ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
            // Wait for stability after scrolling
            //Thread.sleep(2000); // Adjust the wait time as needed
            //logger.info("Waited for stability after scrolling");

            // Extract product name from the page
            WebElement productNameElement = driver.findElement(By.xpath("//h1[@class='product-title']"));
            String productName = productNameElement.getText();
            // Log the extracted product name
            logger.info("Product Name: " + productName);

            // Extract product price from the page
            WebElement priceElement = driver.findElement(By.xpath("//span[@class='price']"));
            String price = priceElement.getText();
            // Log the extracted price
            logger.info("Price: " + price);

            // Extract other relevant product details such as specifications, features, etc.
            // This involves locating elements on the page and extracting their text
            // using findElement() and getText() methods similarly to the above examples

            // Once all necessary information is extracted, create a Product object
            // Example:
            // ProductIntelCPU product = new Product(productName, price, ...);

            // Return the scraped product
            // return product;

        } catch (Exception e) {
            // Handle any exceptions that might occur during scraping
            logger.log(Level.SEVERE, "Error scraping product data from URL: " + productUrl, e);
        } finally {
            // Quit the WebDriver instance to free up system resources
            driver.quit();
            // Log the WebDriver quitting event
            logger.info("Driver quit");
        }

        // If scraping fails or encounters an error, return null or handle the error accordingly
        return null;
    }

    @Override
    public String extractBrand() {
        // Extract the brand from the product URL
        if (productUrl.contains("intel")) {
            return "Intel";
        } else {
            return "Unknown"; // Handle the case where brand is not found or unrecognized
        }
    }

    @Override
    public void saveProduct(ProductRyzenCPU product) {
        if (product != null) {
            productRepository.save(product);
            logger.info("Product saved successfully");
        } else {
            logger.warning("Attempted to save null product");
        }
    }

    public String getProductUrl() {
        return productUrl;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }
}



