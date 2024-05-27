package org.example.scraping;

import org.example.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductScraperFactory {

    private final ProductRepository productRepository;

    @Autowired
    public ProductScraperFactory(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductScraper getScraper(String productUrl) {
        if (productUrl.contains("intel")) {
            return new IntelProductScraper(productRepository);
        } else if (productUrl.contains("amd")) {
            return new RyzenProductScraper(productRepository);
        } else {
            return null; // Handle other cases or return a default scraper
        }
    }

    private boolean isIntelProductUrl(String productUrl) {
        // Logic to determine if the product URL is for an Intel CPU
        ProductScraper scraper = getScraper(productUrl);
        return scraper != null && scraper.extractBrand().equals("Intel");
    }

    private boolean isRyzenProductUrl(String productUrl) {
        // Logic to determine if the product URL is for a Ryzen CPU
        ProductScraper scraper = getScraper(productUrl);
        return scraper != null && scraper.extractBrand().equals("AMD");
    }
}

