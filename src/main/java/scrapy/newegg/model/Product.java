package scrapy.newegg.model;

import java.math.BigDecimal;

public interface Product {
    Long getId();
    String getCategory();
    String getName();
    String getBrand();
    BigDecimal getPrice();
}
