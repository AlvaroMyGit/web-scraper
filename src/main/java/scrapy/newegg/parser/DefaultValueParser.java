package scrapy.newegg.parser;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class DefaultValueParser implements ValueParser {

    private static final Logger logger = Logger.getLogger(DefaultValueParser.class.getName());

    public DefaultValueParser() {
        logger.info("Creating DefaultValueParser bean...");
    }

    @Override
    public String parseString(Element element, String label) {
        try {
            return element.select("tr:has(th:contains(" + label + ")) td").text();
        } catch (Exception e) {
            logger.log(Level.WARNING, "Failed to parse string for label: " + label, e);
            return "";
        }
    }

    @Override
    public int parseInt(Element element, String label) {
        try {
            String text = parseString(element, label).replaceAll("\\D", "");
            return text.isEmpty() ? 0 : Integer.parseInt(text);
        } catch (NumberFormatException e) {
            logger.log(Level.WARNING, "Failed to parse int for label: " + label, e);
            return 0;
        }
    }

    @Override
    public double parseDouble(Element element, String label) {
        try {
            String text = parseString(element, label).replaceAll("[^\\d.]", "");
            return text.isEmpty() ? 0.0 : Double.parseDouble(text);
        } catch (NumberFormatException e) {
            logger.log(Level.WARNING, "Failed to parse double for label: " + label, e);
            return 0.0;
        }
    }

    @Override
    public BigDecimal parsePrice(Document doc) {
        try {
            // Attempt to select the price element
            Element priceElement = doc.selectFirst("li.price-current > strong");

            // If element is found, extract the text and parse to BigDecimal
            if (priceElement != null) {
                String priceText = priceElement.text().trim().replaceAll("[^\\d.]", "");
                return new BigDecimal(priceText);
            } else {
                System.out.println("Price element not found.");
                return BigDecimal.ZERO;
            }

        } catch (NumberFormatException | NullPointerException e) {
            System.out.println("Failed to parse price: " + e.getMessage());
            return BigDecimal.ZERO;
        }
    }

    @Override
    public BigDecimal parseBigDecimal(Element element, String label) {
        try {
            String text = parseString(element, label).replaceAll("[^\\d.]", "");
            return text.isEmpty() ? BigDecimal.ZERO : new BigDecimal(text);
        } catch (NumberFormatException | ArithmeticException e) {
            logger.log(Level.WARNING, "Failed to parse BigDecimal for selector: " + label, e);
            return BigDecimal.ZERO;
        }
    }

    @Override
    public <T> T apply(Element element, String label, ValueParserFunction<T> function) {
        try {
            return function.apply(element, label, this);
        } catch (Exception e) {
            logger.log(Level.WARNING, "Failed to apply function for label: " + label, e);
            return null;
        }
    }
}
