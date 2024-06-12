package scrapy.newegg.factory.product.cpu;

import scrapy.newegg.model.Category;
import scrapy.newegg.model.ProductCategory;
import scrapy.newegg.model.cpu.ProductCpu;
import scrapy.newegg.scraper.ProductScraper;

public interface ProductCpuFactory {
    ProductCpu createProductCpu(ProductCategory category);
}
