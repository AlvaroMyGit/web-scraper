package scrapy.newegg.factory.scraper.cpu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import scrapy.newegg.model.ProductCategory;
import scrapy.newegg.model.cpu.ProductCpu;
import scrapy.newegg.repository.CategoryRepository;
import scrapy.newegg.scraper.ProductScraper;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@Component
public class ProductCpuScraperFactoryProvider {

    private static final Logger logger = Logger.getLogger(ProductCpuScraperFactoryProvider.class.getName());
    private final Map<String, ProductCpuScraperFactory> factoryMap = new HashMap<>();

    @Autowired
    CategoryRepository categoryRepository;

    public ProductCpuScraperFactoryProvider() {
        // Initialize factory mappings here
    }

    @Autowired
    public void setFactories(ProductCpuAmdScraperFactory amdFactory, ProductCpuIntelScraperFactory intelFactory) {
        factoryMap.put("CPU_AMD", amdFactory);
        factoryMap.put("CPU_INTEL", intelFactory);
        // Add more mappings as needed
    }

    public ProductScraper<?> getScraper(String categoryName) {
        ProductCpuScraperFactory factory = factoryMap.get(categoryName);
        if (factory == null) {
            String errorMessage = "No factory registered for category: " + categoryName;
            logger.warning(errorMessage);
            throw new RuntimeException(errorMessage);
        }
            ProductCategory productCategory = categoryRepository.findByName("CPU");

        // Here you can pass the category object if needed
        return factory.createProductScraper(productCategory);
    }
}
