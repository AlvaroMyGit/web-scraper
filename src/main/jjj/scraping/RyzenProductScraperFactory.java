package org.example.scraping;

import org.example.model.ProductRyzenCPU;
import org.example.repository.CategoryRepository;
import org.example.repository.RyzenProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RyzenProductScraperFactory extends ProductScraperFactory<ProductRyzenCPU> {
    private final RyzenProductRepository ryzenProductRepository;

    private final CategoryRepository categoryRepository;

    @Autowired
    public RyzenProductScraperFactory(RyzenProductRepository ryzenProductRepository, CategoryRepository categoryRepository) {
        this.ryzenProductRepository = ryzenProductRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public ProductScraper<ProductRyzenCPU> getScraper(String productUrl) {
        if (productUrl.contains("amd")) {
            return new RyzenProductScraper(ryzenProductRepository, categoryRepository);
        }
        return null; // Handle other cases or return a default scraper
    }
}
