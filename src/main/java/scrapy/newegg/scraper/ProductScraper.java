package scrapy.newegg.scraper;

import org.jsoup.nodes.Element;
import scrapy.newegg.config.ScrapingResult;
import scrapy.newegg.model.Product;
import scrapy.newegg.model.cpu.ProductCpuAmd;
import scrapy.newegg.parser.ValueParserFunction;

import java.util.concurrent.Callable;
import java.util.function.Consumer;

public interface ProductScraper<T extends Product> extends Callable<ScrapingResult> {
    @Override
    ScrapingResult call();

    Element getSpecsTabPane();

    <T2> void parseAndLog(Element specsTable, T product, String fieldName, ValueParserFunction<T2> parserFunction, Consumer<T2> setter);

    String getValueByLabel(Element table, String label);

    void saveProduct(T product);

    void setProductUrl(String productUrl);

}

