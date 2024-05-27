package org.example.scraping;

import org.example.model.ProductRyzenCPU;

import java.util.concurrent.Callable;

public interface ProductScraper extends Callable {
    @Override
    ProductRyzenCPU call();

    String extractBrand();

    void saveProduct(ProductRyzenCPU product);

    void setProductUrl(String productUrl);
}
