package org.example.repository;

import org.example.model.ProductIntelCPU;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IntelProductRepository extends JpaRepository<ProductIntelCPU, Long> {
}
