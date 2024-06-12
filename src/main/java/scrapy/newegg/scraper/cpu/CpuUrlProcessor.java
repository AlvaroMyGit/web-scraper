package scrapy.newegg.scraper.cpu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import scrapy.newegg.factory.product.cpu.ProductCpuFactoryProvider;
import scrapy.newegg.model.cpu.ProductCpu;
import scrapy.newegg.model.cpu.ProductCpuAmd;
import scrapy.newegg.model.cpu.ProductCpuIntel;
import scrapy.newegg.repository.category.cpu.ProductCpuAmdRepository;
import scrapy.newegg.repository.category.cpu.ProductCpuIntelRepository;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class URLProcessor {

    private static final Logger logger = Logger.getLogger(URLProcessor.class.getName());

    @Autowired
    private BlockingQueue<String> urlQueue;

    @Autowired
    private ProductCpuFactoryProvider productCpuFactoryProvider;

    @Autowired
    private ProductCpuAmdRepository productCpuAmdRepository;

    @Autowired
    private ProductCpuIntelRepository productCpuIntelRepository;

    private final ExecutorService scrapingExecutor = Executors.newSingleThreadExecutor();

    public void startProcessing() {
        scrapingExecutor.submit(() -> {
            while (true) {
                try {
                    processNextProductUrl();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
    }

    private void processNextProductUrl() throws InterruptedException {
        String productUrl = urlQueue.take();
        if (productUrl == null) return;

        String brand = getBrandFromUrl(productUrl);

        try {
            ProductCpu product = createProductFromUrl(productUrl, brand);
            if (product != null) {
                saveProduct(product);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error scraping or saving product data from URL: " + productUrl, e);
        }
    }

    private String getBrandFromUrl(String productUrl) {
        if (productUrl.contains("amd")) {
            return "amd";
        } else if (productUrl.contains("intel")) {
            return "intel";
        } else {
            return "";
        }
    }

    private ProductCpu createProductFromUrl(String productUrl, String brand) {
        String categoryName = brand.equalsIgnoreCase("amd") ? "CPU_AMD" : "CPU_INTEL";
        return productCpuFactoryProvider.createProductCpu(categoryName);
    }

    private void saveProduct(ProductCpu product) {
        if (product instanceof ProductCpuAmd) {
            productCpuAmdRepository.save((ProductCpuAmd) product);
        } else if (product instanceof ProductCpuIntel) {
            productCpuIntelRepository.save((ProductCpuIntel) product);
        }
    }
}
