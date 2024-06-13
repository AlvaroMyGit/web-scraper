package scrapy.newegg.parser;

import org.jsoup.nodes.Element;


@FunctionalInterface
public interface ValueParserFunction<T> {
    T apply(Element element, String label, ValueParser parser);
}
