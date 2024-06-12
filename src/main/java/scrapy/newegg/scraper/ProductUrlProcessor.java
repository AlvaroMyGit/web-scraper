package scrapy.newegg.scraper;

import scrapy.newegg.model.cpu.ProductCpu;

public interface ProductUrlProcessor {

    void startProcessing();
    void processNextProductUrl();
    String getBrandFromUrl(String productUrl);
    ProductCpu createProductFromUrl(String productUrl, String brand);
    void saveProduct(ProductCpu product);
}
