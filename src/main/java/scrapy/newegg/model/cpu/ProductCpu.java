package scrapy.newegg.model.cpu;

import scrapy.newegg.model.Product;

public interface ProductCpu extends Product {

    int getNumberOfCores();
    void setNumberOfCores(int numberOfCores);
    int getNumberOfThreads();
    void setNumberOfThreads(int numberOfThreads);
}
