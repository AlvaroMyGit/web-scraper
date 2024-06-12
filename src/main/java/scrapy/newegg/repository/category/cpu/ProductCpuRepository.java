package scrapy.newegg.repository.category.cpu;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import scrapy.newegg.model.cpu.ProductCpu;

import java.util.List;

@Repository
public interface ProductCpuRepository extends JpaRepository<ProductCpu, Long> {
    List<ProductCpu> findAllByCategoryName(String categoryName);
}
