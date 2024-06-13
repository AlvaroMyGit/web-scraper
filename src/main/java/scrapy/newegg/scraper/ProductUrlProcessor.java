package scrapy.newegg.scraper;

import scrapy.newegg.model.cpu.ProductCpu;

public interface ProductUrlProcessor {

    void startProcessing();
    void processNextProductUrl();
    String getBrandFromUrl(String productUrl);
    void createProductFromUrl(String productUrl, String brand);

}
