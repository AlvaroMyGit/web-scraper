package scrapy.newegg.factory.scraper.cpu;

import org.springframework.stereotype.Component;
import scrapy.newegg.model.Category;
import scrapy.newegg.scraper.cpu.CpuAmdScraper;
import scrapy.newegg.scraper.cpu.CpuIntelScraper;
@Component
public class ProductCpuIntelScraperFactory implements ProductCpuScraperFactory {

    @Override
    public CpuIntelScraper createProductScraper(Category category) {
        return new CpuIntelScraper();
    }
}
