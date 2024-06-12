package org.example.model;

import jakarta.persistence.*;

@Entity
@Table(name = "compatibility")
public class Compatibility {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "category1_id")
    private Category category1;

    @ManyToOne
    @JoinColumn(name = "category2_id")
    private Category category2;

    @Column(name = "compatibility_rule", nullable = false)
    private String compatibilityRule;

    // Default constructor
    public Compatibility() {}

    // Parameterized constructor
    public Compatibility(Category category1, Category category2, String compatibilityRule) {
        this.category1 = category1;
        this.category2 = category2;
        this.compatibilityRule = compatibilityRule;
    }

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
