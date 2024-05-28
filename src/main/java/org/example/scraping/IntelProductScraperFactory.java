package org.example.scraping;

import org.example.model.ProductIntelCPU;
import org.example.repository.IntelProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IntelProductScraperFactory extends ProductScraperFactory<ProductIntelCPU> {
    private final IntelProductRepository intelProductRepository;

    @Autowired
    public IntelProductScraperFactory(IntelProductRepository intelProductRepository) {
        this.intelProductRepository = intelProductRepository;
    }

    @Override
    public ProductScraper<ProductIntelCPU> getScraper(String productUrl) {
        if (productUrl.contains("intel")) {
            return new IntelProductScraper(intelProductRepository);
        }
        return null; // Handle other cases or return a default scraper
    }
}