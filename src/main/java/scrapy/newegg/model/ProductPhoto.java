package scrapy.newegg.model;

import jakarta.persistence.*;

@Entity
@Table(name = "product_photo")
public class ProductPhoto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "url", nullable = false)
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private AbstractProduct abstractProduct;

    // Constructor

    public ProductPhoto() {
    }

    public ProductPhoto(String url, AbstractProduct abstractProduct) {
        this.url = url;
        this.abstractProduct = abstractProduct;
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public AbstractProduct getAbstractProduct() {
        return abstractProduct;
    }

    public void setAbstractProduct(AbstractProduct abstractProduct) {
        this.abstractProduct = abstractProduct;
    }
}
