package org.example.scraping;

import java.util.concurrent.Callable;

public interface ProductScraper<T> extends Callable<T> {
    @Override
    T call();

    String extractBrand();

    void saveProduct(T product);

    void setProductUrl(String productUrl);
}
