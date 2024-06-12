package scrapy.newegg.scraper.cpu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import scrapy.newegg.factory.scraper.cpu.ProductCpuScraperFactoryProvider;
import scrapy.newegg.model.cpu.ProductCpu;
import scrapy.newegg.scraper.ProductScraper;
import scrapy.newegg.scraper.ProductUrlProcessor;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;


@Component
public class CpuUrlProcessor implements ProductUrlProcessor {
    private static final Logger logger = Logger.getLogger(CpuUrlProcessor.class.getName());

    @Autowired
    private BlockingQueue<String> urlQueue;

    @Autowired
    private ProductCpuScraperFactoryProvider productCpuScraperFactoryProvider;

    private final ExecutorService scrapingExecutor = Executors.newSingleThreadExecutor();

    public void startProcessing() {
        scrapingExecutor.submit(() -> {
            while (true) {
                processNextProductUrl();
            }
        });
    }

    public void processNextProductUrl() {
        String productUrl = null;
        try {
            productUrl = urlQueue.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (productUrl == null) return;

        String brand = getBrandFromUrl(productUrl);

        try {
            ProductCpu product = createProductFromUrl(productUrl, brand);
            if (product != null) {
                // Do not save the product here
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error scraping product data from URL: " + productUrl, e);
        }
    }

    public String getBrandFromUrl(String productUrl) {
        if (productUrl.contains("amd")) {
            return "amd";
        } else if (productUrl.contains("intel")) {
            return "intel";
        } else {
            return "";
        }
    }

    public ProductCpu createProductFromUrl(String productUrl, String brand) {
        if ("amd".equalsIgnoreCase(brand)) {
            ProductScraper<?> scraper = productCpuScraperFactoryProvider.getScraper("CPU_AMD");
            if (scraper != null) {
                try {
                    scraper.call();
                } catch (Exception e) {
                    logger.log(Level.SEVERE, "Error scraping product data for AMD CPU", e);
                }
            } else {
                logger.warning("No scraper available for AMD CPUs");
            }
        } else if ("intel".equalsIgnoreCase(brand)) {
            ProductScraper<?> scraper = productCpuScraperFactoryProvider.getScraper("CPU_INTEL");
            if (scraper != null) {
                try {
                    scraper.call();
                } catch (Exception e) {
                    logger.log(Level.SEVERE, "Error scraping product data for Intel CPU", e);
                }
            } else {
                logger.warning("No scraper available for Intel CPUs");
            }
        } else {
            logger.warning("Unknown CPU brand");
        }
        return null; // Since the scraper handles the product directly
    }
}