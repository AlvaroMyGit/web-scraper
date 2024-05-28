package org.example.scraping;

public abstract class ProductScraperFactory<T> {
    public abstract ProductScraper<?> getScraper(String productUrl);
}
