package scrapy.newegg.scraper;

import org.springframework.boot.CommandLineRunner;

public interface ProductUrlScraper extends CommandLineRunner {
    void run(String... args);
    void fetchProductUrls();
    String getRandomUserAgent();
    String getBrandFromUrl(String productUrl);
}
