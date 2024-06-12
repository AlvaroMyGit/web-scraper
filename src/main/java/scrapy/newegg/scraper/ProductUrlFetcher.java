package scrapy.newegg.scraper;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;

import java.util.List;

public interface ProductUrlFetcher {

    void fetchProductUrls();
    Document fetchPage(String url, String userAgent);
    void addProductUrlsToQueue(Elements productRows);
    String getRandomUserAgent();
}
