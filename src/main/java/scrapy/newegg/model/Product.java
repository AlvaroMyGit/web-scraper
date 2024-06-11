package scrapy.newegg.model;

import java.math.BigDecimal;
import java.util.Set;

public interface Product {

    Long getId();
    void setId(Long id);

    String getName();
    void setName(String name);
    String getBrand();
    void setBrand(String brand);

    BigDecimal getPrice();
    void setPrice(BigDecimal price);

    Category getCategory();
    void setCategory(Category category);
}
