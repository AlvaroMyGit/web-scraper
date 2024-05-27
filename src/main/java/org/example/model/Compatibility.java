package org.example.model;


import jakarta.persistence.*;

@Entity
public class Compatibility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "category1_id", nullable = false)
    private Category category1;

    @ManyToOne
    @JoinColumn(name = "category2_id", nullable = false)
    private Category category2;

    @Column(columnDefinition = "TEXT")
    private String compatibilityRule;

    // Getters and setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Category getCategory1() {
        return category1;
    }

    public void setCategory1(Category category1) {
        this.category1 = category1;
    }

    public Category getCategory2() {
        return category2;
    }

    public void setCategory2(Category category2) {
        this.category2 = category2;
    }

    public String getCompatibilityRule() {
        return compatibilityRule;
    }

    public void setCompatibilityRule(String compatibilityRule) {
        this.compatibilityRule = compatibilityRule;
    }
}
