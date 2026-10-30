package com.belairline.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.belairline.model.SalesDesk;

public interface SalesDeskRepository extends JpaRepository<SalesDesk, UUID> {
}
