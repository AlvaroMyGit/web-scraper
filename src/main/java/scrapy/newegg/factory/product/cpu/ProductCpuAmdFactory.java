package scrapy.newegg.factory.product.cpu;

import org.springframework.stereotype.Component;
import scrapy.newegg.model.Category;
import scrapy.newegg.model.cpu.ProductCpu;
import scrapy.newegg.model.cpu.ProductCpuAmd;

@Component
public class ProductCpuAmdFactory implements ProductCpuFactory{

    @Override
    public ProductCpu createProductCpu(Category category) {
        return new ProductCpuAmd(category);
    }
}
