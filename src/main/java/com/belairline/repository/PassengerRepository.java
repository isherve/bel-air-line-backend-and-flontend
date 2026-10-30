package com.belairline.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.belairline.model.Passenger;

public interface PassengerRepository extends JpaRepository<Passenger, UUID> {
}
