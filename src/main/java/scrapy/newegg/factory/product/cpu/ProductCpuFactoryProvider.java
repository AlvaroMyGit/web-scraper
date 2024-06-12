package scrapy.newegg.factory.product.cpu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import scrapy.newegg.model.Category;
import scrapy.newegg.model.product_cpu.ProductCpu;
import scrapy.newegg.repository.CategoryRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@Component
public class ProductCpuFactoryProvider {

    private static final Logger logger = Logger.getLogger(ProductCpuFactoryProvider.class.getName());
    private Map<String, ProductCpuFactory> factoryMap = new HashMap<>();
    @Autowired
    private CategoryRepository categoryRepository;

    public ProductCpuFactoryProvider(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
        factoryMap.put("CPU_AMD", new ProductCpuAmdFactory());
        factoryMap.put("CPU_INTEL", new ProductCpuIntelFactory());
        // Add more mappings as needed
    }

    public ProductCpu createProductCpu(String categoryName) {
        Category category = categoryRepository.findByName(categoryName);
        if (category == null) {
            String errorMessage = "Category not found in the database: " + categoryName;
            logger.warning(errorMessage);
            throw new RuntimeException(errorMessage);
        }

        ProductCpuFactory factory = factoryMap.get(categoryName);
        if (factory == null) {
            String errorMessage = "No factory registered for category: " + categoryName;
            logger.warning(errorMessage);
            throw new RuntimeException(errorMessage);
        }

        return factory.createProductCpu(category);
    }
}
