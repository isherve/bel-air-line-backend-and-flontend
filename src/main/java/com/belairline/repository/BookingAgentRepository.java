package com.belairline.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.belairline.model.BookingAgent;
import com.belairline.model.SalesDesk;

public interface BookingAgentRepository extends JpaRepository<BookingAgent, UUID> {

    boolean existsBySalesDesk(SalesDesk salesDesk);

    @Query("SELECT DISTINCT p FROM BookingAgent p JOIN p.routes c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<BookingAgent> findByRouteName(@Param("name") String name);
}
