package scrapy.newegg.model;

public interface Category {
    Long getId();
    String getName();
    void setName();
    String getDescription(String name);
    void setDescription(String description);
}
