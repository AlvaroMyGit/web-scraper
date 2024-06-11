package scrapy.newegg.scraper;

import org.springframework.boot.CommandLineRunner;

import java.util.List;

public interface ProductUrlScraper extends CommandLineRunner {
    void run();
    void fetchProductUrls();
    String getRandomUserAgent();
    String getBrandFromUrl();
}
