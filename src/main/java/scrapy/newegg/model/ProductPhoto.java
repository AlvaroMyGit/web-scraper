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

    // Constructors, getters, and setters

    public ProductPhoto() {
    }

    public ProductPhoto(String url, AbstractProduct abstractProduct) {
        this.url = url;
        this.abstractProduct = abstractProduct;
    }

    // Getters and setters
}
