package scrapy.newegg.model;

import java.math.BigDecimal;

public interface Product {

    Long getId();

    String getName();
    void setName(String name);
    String getBrand();
    void setBrand(String brand);

    BigDecimal getPrice();
    void setPrice(BigDecimal price);

    Category getCategory();
    void setCategory(Category category);
}
