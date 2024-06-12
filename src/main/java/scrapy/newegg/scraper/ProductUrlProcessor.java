package scrapy.newegg.scraper;

import scrapy.newegg.model.product_cpu.ProductCpu;

public interface ProductUrlProcessor {

    void startProcessing();
    void processNextProduct();
    String getBrandFromUrl();
    ProductCpu createProductFromUrl();
    void saveProduct();
}
