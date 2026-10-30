package com.belairline.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.belairline.model.Route;

public interface RouteRepository extends JpaRepository<Route, UUID> {
}
