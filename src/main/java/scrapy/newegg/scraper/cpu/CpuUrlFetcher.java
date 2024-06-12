package scrapy.newegg.scraper.cpu;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.checkerframework.checker.units.qual.C;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import scrapy.newegg.scraper.ProductUrlFetcher;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;


@Component
public class CpuUrlFetcher implements ProductUrlFetcher {

    private static final Logger logger = Logger.getLogger(CpuUrlFetcher.class.getName());

    @Autowired
    private BlockingQueue<String> urlQueue;

    @Autowired
    private List<String> userAgents; // Injected user agents list

    public void fetchProductUrls() {
        WebDriverManager.chromedriver().setup();
        String baseUrl = "https://www.newegg.com/Processors-Desktops/SubCategory/ID-343?PageSize=96";
        int page = 1;
        boolean hasNextPage = true;

        while (hasNextPage) {
            String url = baseUrl + "&Page=" + page;
            logger.info("Fetching URL: " + url);

            String userAgent = getRandomUserAgent();
            WebDriver driver = setupWebDriver(userAgent);

            try {
                driver.get(url);
                logger.info("Page loaded successfully");

                List<WebElement> productRows = driver.findElements(By.cssSelector(".item-cell"));
                if (productRows.isEmpty()) {
                    hasNextPage = false;
                    logger.info("No more products found, ending pagination.");
                } else {
                    addProductUrlsToQueue(productRows);
                }
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Error during scraping", e);
            } finally {
                driver.quit();
            }
            page++;
        }
    }

    public WebDriver setupWebDriver(String userAgent) {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--user-agent=" + userAgent);
        options.addArguments("--headless");
        options.setExperimentalOption("useAutomationExtension", false);
        options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));

        return new ChromeDriver(options);
    }

    public void addProductUrlsToQueue(List<WebElement> productRows) {
        for (WebElement row : productRows) {
            String productUrl = row.findElement(By.cssSelector(".item-title")).getAttribute("href");
            try {
                urlQueue.put(productUrl);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            logger.info("Added product URL to the queue: " + productUrl);
        }
    }

    public String getRandomUserAgent() {
        Random random = new Random();
        int index = random.nextInt(userAgents.size());
        return userAgents.get(index);
    }
}
