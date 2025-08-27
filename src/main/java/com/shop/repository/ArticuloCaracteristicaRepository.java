package com.shop.repository;

import com.shop.entity.ArticuloCaracteristica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticuloCaracteristicaRepository extends JpaRepository<ArticuloCaracteristica, Long> {
}