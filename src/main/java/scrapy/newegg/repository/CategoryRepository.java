package scrapy.newegg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import scrapy.newegg.model.Category;
import scrapy.newegg.model.ProductCategory;

@Repository
public interface CategoryRepository extends JpaRepository<ProductCategory, Long> {
    ProductCategory findByName(String name);
}
