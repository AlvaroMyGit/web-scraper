package scrapy.newegg.parser;

import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;


@Component
public class DefaultValueParser implements ValueParser {

    private static final Logger logger = Logger.getLogger(DefaultValueParser.class.getName());

    @Override
    public String parseString(Element element, String label) {
        return element.select("tr:has(th:contains(" + label + ")) td").text();
    }

    @Override
    public int parseInt(Element element, String label) {
        try {
            String text = parseString(element, label).replaceAll("\\D", "");
            return text.isEmpty() ? 0 : Integer.parseInt(text);
        } catch (Exception e) {
            logger.log(Level.WARNING, "Failed to parse int for label: " + label, e);
            return 0;
        }
    }

    @Override
    public double parseDouble(Element element, String label) {
        try {
            String text = parseString(element, label).replaceAll("[^\\d.]", "");
            return text.isEmpty() ? 0.0 : Double.parseDouble(text);
        } catch (Exception e) {
            logger.log(Level.WARNING, "Failed to parse double for label: " + label, e);
            return 0.0;
        }
    }

    @Override
    public BigDecimal parseBigDecimal(Element element, String label) {
        try {
            String text = parseString(element, label).replaceAll("[^\\d.]", "");
            return text.isEmpty() ? BigDecimal.ZERO : new BigDecimal(text);
        } catch (Exception e) {
            logger.log(Level.WARNING, "Failed to parse BigDecimal for selector: " + label, e);
            return BigDecimal.ZERO;
        }
    }
}