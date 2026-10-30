package com.belairline.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.belairline.model.Route;

public interface RouteRepository extends JpaRepository<Route, UUID> {

    @Query("SELECT r FROM Route r WHERE r.agents IS EMPTY")
    List<Route> findUnusedRoutes();
}
