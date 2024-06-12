package scrapy.newegg.scraper.cpu;


import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import scrapy.newegg.scraper.ProductUrlFetcher;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;


@Component
public class CpuUrlFetcher implements ProductUrlFetcher {

    private static final Logger logger = Logger.getLogger(CpuUrlFetcher.class.getName());

    @Autowired
    private BlockingQueue<String> urlQueue;

    @Autowired
    private List<String> userAgents;

    @Override
    public void fetchProductUrls() {
        String baseUrl = "https://www.newegg.com/Processors-Desktops/SubCategory/ID-343?PageSize=96";
        int page = 1;
        boolean hasNextPage = true;

        while (hasNextPage) {
            String url = baseUrl + "&Page=" + page;
            logger.info("Fetching URL: " + url);

            String userAgent = getRandomUserAgent();

            Document doc = fetchPage(url, userAgent);
            Elements productRows = doc.select(".item-container");

            if (productRows.isEmpty()) {
                hasNextPage = false;
                logger.info("No more products found, ending pagination.");
            } else {
                addProductUrlsToQueue(productRows);
            }

            page++;
        }
    }

    public Document fetchPage(String url, String userAgent) {
        Connection connection = Jsoup.connect(url).userAgent(userAgent);
        try {
            return connection.get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addProductUrlsToQueue(Elements productRows) {
        for (Element row : productRows) {
            String productUrl = row.select(".item-title").attr("href");
            try {
                urlQueue.put(productUrl);
                logger.info("Added product URL to the queue: " + productUrl);
            } catch (InterruptedException e) {
                logger.log(Level.SEVERE, "Error adding product URL to queue", e);
                Thread.currentThread().interrupt();
            }
        }
    }

    public String getRandomUserAgent() {
        Random random = new Random();
        int index = random.nextInt(userAgents.size());
        return userAgents.get(index);
    }
}
