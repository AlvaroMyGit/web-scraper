package scrapy.newegg.scraper;

import org.openqa.selenium.WebDriver;

public interface ProductUrlFetcher {

    void fetchProductUrls();
    WebDriver setupWebDriver();
    void addProductUrlsToQueue();
    String getRandomUserAgent();
}
