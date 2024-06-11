package scrapy.newegg.repository.category.cpu;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import scrapy.newegg.model.product_cpu.ProductCpuAmd;


@Repository
public interface ProductCpuAmdRepository extends JpaRepository<ProductCpuAmd, Long> {
}
