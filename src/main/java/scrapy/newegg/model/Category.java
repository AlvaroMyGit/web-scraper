package scrapy.newegg.model;

public interface Category {
    Long getId();
    String getName();
    void setName(String name);
    String getDescription();
    void setDescription(String description);
}
