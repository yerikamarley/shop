package com.shop.repository;

import com.shop.entity.VentaArticulo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VentaArticuloRepository extends JpaRepository<VentaArticulo, Long> {
}