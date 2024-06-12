package scrapy.newegg.scraper;

import org.jsoup.nodes.Element;
import scrapy.newegg.model.Product;

import java.util.concurrent.Callable;

public interface ProductScraper<T> extends Callable<T> {
    @Override
    T call();

    Element getSpecsTabPane();
    Element getSpecsTable(Element specsTabPane);

    void saveProduct(T product);

}

