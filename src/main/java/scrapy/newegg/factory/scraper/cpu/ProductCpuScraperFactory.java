package scrapy.newegg.factory.scraper.cpu;

import scrapy.newegg.model.Category;
import scrapy.newegg.model.ProductCategory;
import scrapy.newegg.scraper.ProductScraper;
import scrapy.newegg.scraper.cpu.CpuAmdScraper;

public interface ProductCpuScraperFactory {

    ProductScraper<?> createProductScraper(ProductCategory category);
}
