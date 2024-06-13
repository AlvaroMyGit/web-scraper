package scrapy.newegg.factory.scraper.cpu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import scrapy.newegg.model.ProductCategory;
import scrapy.newegg.scraper.cpu.CpuAmdScraper;

@Component
public class ProductCpuAmdScraperFactory implements ProductCpuScraperFactory {
    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public CpuAmdScraper createProductScraper(ProductCategory category) {
        return applicationContext.getBean(CpuAmdScraper.class);
    }
}
