package scrapy.newegg.scraper;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public interface ProductUrlFetcher {

    void fetchProductUrls();
    WebDriver setupWebDriver(String userAgent);
    void addProductUrlsToQueue(List<WebElement> productRows);
    String getRandomUserAgent();
}
