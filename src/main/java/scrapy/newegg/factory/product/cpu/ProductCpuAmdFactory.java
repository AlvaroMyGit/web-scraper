package scrapy.newegg.factory.product.cpu;

import org.springframework.stereotype.Component;
import scrapy.newegg.model.ProductCategory;
import scrapy.newegg.model.cpu.ProductCpu;
import scrapy.newegg.model.cpu.ProductCpuAmd;

@Component
public class ProductCpuAmdFactory implements ProductCpuFactory{

    @Override
    public ProductCpu createProductCpu(ProductCategory category) {
        return new ProductCpuAmd(category);
    }
}
