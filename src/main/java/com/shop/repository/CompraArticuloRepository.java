package com.shop.repository;

import com.shop.entity.CompraArticulo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompraArticuloRepository extends JpaRepository<CompraArticulo, Long> {
}