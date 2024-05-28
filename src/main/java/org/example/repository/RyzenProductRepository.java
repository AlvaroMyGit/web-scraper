package org.example.repository;

import org.example.model.ProductRyzenCPU;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RyzenProductRepository extends JpaRepository<ProductRyzenCPU, Long> {
}
