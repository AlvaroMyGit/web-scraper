package scrapy.newegg.factory.scraper.cpu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import scrapy.newegg.model.Category;
import scrapy.newegg.model.ProductCategory;
import scrapy.newegg.scraper.cpu.CpuAmdScraper;
import scrapy.newegg.scraper.cpu.CpuIntelScraper;
@Component
public class ProductCpuIntelScraperFactory implements ProductCpuScraperFactory {

    @Autowired
    ApplicationContext applicationContext;



    @Override
    public CpuIntelScraper createProductScraper(ProductCategory category) {
        return applicationContext.getBean(CpuIntelScraper.class);
    }
}
