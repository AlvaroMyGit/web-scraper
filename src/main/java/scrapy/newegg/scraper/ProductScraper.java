package scrapy.newegg.scraper;

import org.jsoup.nodes.Element;
import scrapy.newegg.config.ScrapingResult;
import scrapy.newegg.model.Product;

import java.util.concurrent.Callable;

public interface ProductScraper<T extends Product> extends Callable<ScrapingResult<T>> {
    @Override
    ScrapingResult<T> call();

    Element getSpecsTabPane();
    Element getSpecsTable(Element specsTabPane);

    void saveProduct(T product);

}

