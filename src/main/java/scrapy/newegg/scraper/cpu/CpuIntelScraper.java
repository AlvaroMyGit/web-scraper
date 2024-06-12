package scrapy.newegg.scraper.cpu;

import org.jsoup.nodes.Element;
import scrapy.newegg.model.cpu.ProductCpu;
import scrapy.newegg.model.cpu.ProductCpuIntel;
import scrapy.newegg.scraper.ProductScraper;

public class CpuIntelScraper implements ProductScraper<ProductCpu> {

    @Override
    public ProductCpu call() {
        return null;
    }

    @Override
    public Element getSpecsTabPane() {
        return null;
    }

    @Override
    public Element getSpecsTable(Element specsTabPane) {
        return null;
    }

    @Override
    public void saveProduct(ProductCpu product) {

    }
}
