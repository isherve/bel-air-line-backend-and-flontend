package com.belairline.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.belairline.model.SalesDesk;

public interface SalesDeskRepository extends JpaRepository<SalesDesk, UUID> {

    @Query("SELECT l FROM SalesDesk l WHERE NOT EXISTS (SELECT p FROM BookingAgent p WHERE p.salesDesk = l) ORDER BY l.deskNumber")
    List<SalesDesk> findFreeSalesDesks();
}
