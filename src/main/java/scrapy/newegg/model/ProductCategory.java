package scrapy.newegg.model;

public class ProductCategory implements Category {

    private Long Id;

    private String name;

    private String description;


    @Override
    public Long getId() {
        return Id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }
}
