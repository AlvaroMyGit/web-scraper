package scrapy.newegg.factory.scraper.cpu;

import org.springframework.stereotype.Component;
import scrapy.newegg.model.Category;
import scrapy.newegg.model.ProductCategory;
import scrapy.newegg.scraper.cpu.CpuAmdScraper;

@Component
public class ProductCpuAmdScraperFactory implements ProductCpuScraperFactory {
    @Override
    public CpuAmdScraper createProductScraper(ProductCategory category) {
        return new CpuAmdScraper();
    }
}
