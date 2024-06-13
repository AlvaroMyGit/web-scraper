import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.math.BigDecimal;

public class PriceExtractor {

    public static void main(String[] args) {
        // URLs for AMD and Intel CPU product pages
        String amdUrl = "https://www.newegg.com/amd-ryzen-5-7600x-ryzen-5-7000-series/p/N82E16819113770";
        String intelUrl = "https://www.newegg.com/intel-core-i7-14700k-core-i7-14th-gen/p/N82E16819118466";

        try {
            // Fetch and parse HTML for AMD CPU
            Document amdDoc = Jsoup.connect(amdUrl).get();
            BigDecimal amdPrice = parsePrice(amdDoc);
            System.out.println("AMD CPU Price: $" + amdPrice);

            // Fetch and parse HTML for Intel CPU
            Document intelDoc = Jsoup.connect(intelUrl).get();
            BigDecimal intelPrice = parsePrice(intelDoc);
            System.out.println("Intel CPU Price: $" + intelPrice);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static BigDecimal parsePrice(Document doc) {
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
}
