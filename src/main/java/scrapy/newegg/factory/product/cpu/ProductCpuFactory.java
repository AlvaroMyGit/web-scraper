package scrapy.newegg.factory.product.cpu;

import scrapy.newegg.model.Category;
import scrapy.newegg.model.cpu.ProductCpu;

public interface ProductCpuFactory {
    ProductCpu createProductCpu(Category category);
}
