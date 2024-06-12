package scrapy.newegg.factory.product.cpu;

import scrapy.newegg.model.Category;
import scrapy.newegg.model.product_cpu.ProductCpu;

public interface ProductCpuFactory {
    ProductCpu createProductCpu(Category category);
}
