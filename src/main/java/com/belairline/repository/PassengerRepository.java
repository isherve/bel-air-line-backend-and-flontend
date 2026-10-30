package com.belairline.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.belairline.model.Passenger;

public interface PassengerRepository extends JpaRepository<Passenger, UUID> {

    List<Passenger> findByLastNameIgnoreCaseOrderByFirstNameAsc(String lastName);
}
