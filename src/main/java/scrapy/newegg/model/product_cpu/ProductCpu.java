package scrapy.newegg.model.product_cpu;

import scrapy.newegg.model.Product;

public interface ProductCpu extends Product {

    int getNumberOfCores();
    void setNumberOfCores(int numberOfCores);
    int getNumberOfThreads();
    void setNumberOfThreads(int numberOfThreads);
}
