package scrapy.newegg.scraper;

import org.jsoup.nodes.Element;
import scrapy.newegg.config.ScrapingResult;
import scrapy.newegg.model.Product;

import java.util.concurrent.Callable;

public interface ProductScraper<T extends Product> extends Callable<ScrapingResult> {
    @Override
    ScrapingResult call();

    Element getSpecsTabPane();
    Element getSpecsTable(Element specsTabPane);

    void saveProduct(T product);

    void setProductUrl(String productUrl);

}

