package scrapy.newegg.parser;

import org.jsoup.nodes.Element;

import java.math.BigDecimal;

public interface ValueParser {

    String parseString(Element element, String label);
    int parseInt(Element element, String label);
    double parseDouble(Element element, String label);
    BigDecimal parseBigDecimal(Element element, String label);
    <T> T apply(Element element, String label, ValueParserFunction<T> function);
}
