package scrapy.newegg.model;

public interface Category {
    Long getId();
    void setId(Long id);
    String getName();
    void setName(String name);
    String getDescription();
    void setDescription(String description);
}
