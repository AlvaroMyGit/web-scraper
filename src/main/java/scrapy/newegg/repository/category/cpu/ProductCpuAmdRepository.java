package scrapy.newegg.repository.category.cpu;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import scrapy.newegg.model.cpu.ProductCpu;
import scrapy.newegg.model.cpu.ProductCpuAmd;


@Repository
public interface ProductCpuAmdRepository extends JpaRepository<ProductCpuAmd, Long> {
}
