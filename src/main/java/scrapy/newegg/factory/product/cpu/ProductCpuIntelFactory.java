package scrapy.newegg.factory.product.cpu;

import org.springframework.stereotype.Component;
import scrapy.newegg.model.Category;
import scrapy.newegg.model.ProductCategory;
import scrapy.newegg.model.cpu.ProductCpu;
import scrapy.newegg.model.cpu.ProductCpuIntel;


@Component
public class ProductCpuIntelFactory implements ProductCpuFactory{
        @Override
        public ProductCpu createProductCpu(ProductCategory category) {
            return new ProductCpuIntel(category);
        }

}
