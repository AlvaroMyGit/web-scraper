package Divergent;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;

public class Small {
    public static void main(String[] args) {
        String url = "https://www.newegg.com/Processors-Desktops/SubCategory/ID-343";

        try {
            // Send HTTP GET request with a user-agent header
            Connection connection = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36");
            Document doc = connection.get();
            System.out.println(doc);

            // Extract CPU elements
            Elements cpuElements = doc.select(".item-container");

            // Process each CPU element
            for (Element cpuElement : cpuElements) {
                // Extract CPU name, price, specifications, etc.
                String name = cpuElement.select(".item-title").text();
                String price = cpuElement.select(".price-current").text();
                String specifications = cpuElement.select(".item-features").text();
                String productUrl = cpuElement.select(".item-title").attr("href");

                // Print CPU information
                System.out.println("Name: " + name);
                System.out.println("Price: " + price);
                System.out.println("Specifications: " + specifications);
                System.out.println("Product URL: " + productUrl);
                System.out.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
