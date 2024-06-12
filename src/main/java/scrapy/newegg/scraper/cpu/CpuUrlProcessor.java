package scrapy.newegg.scraper.cpu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import scrapy.newegg.factory.product.cpu.ProductCpuFactoryProvider;
import scrapy.newegg.model.cpu.ProductCpu;
import scrapy.newegg.model.cpu.ProductCpuAmd;
import scrapy.newegg.model.cpu.ProductCpuIntel;
import scrapy.newegg.repository.category.cpu.ProductCpuAmdRepository;
import scrapy.newegg.repository.category.cpu.ProductCpuIntelRepository;
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
    private ProductCpuFactoryProvider productCpuFactoryProvider;

    @Autowired
    private ProductCpuAmdRepository productCpuAmdRepository;

    @Autowired
    private ProductCpuIntelRepository productCpuIntelRepository;

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
                saveProduct(product);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error scraping or saving product data from URL: " + productUrl, e);
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
        String categoryName = brand.equalsIgnoreCase("amd") ? "CPU_AMD" : "CPU_INTEL";
        return productCpuFactoryProvider.createProductCpu(categoryName);
    }

    public void saveProduct(ProductCpu product) {
        if (product instanceof ProductCpuAmd) {
            productCpuAmdRepository.save((ProductCpuAmd) product);
        } else if (product instanceof ProductCpuIntel) {
            productCpuIntelRepository.save((ProductCpuIntel) product);
        }
    }
}