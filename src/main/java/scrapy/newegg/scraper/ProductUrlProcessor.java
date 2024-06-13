package scrapy.newegg.scraper;

public interface ProductUrlProcessor {

    void startProcessing();
    void processNextProductUrl();
    String getBrandFromUrl(String productUrl);
    void createScraperFromUrl(String productUrl, String brand);

}
