package com.belairline.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.belairline.model.Passenger;

public interface PassengerRepository extends JpaRepository<Passenger, UUID> {

    List<Passenger> findByLastNameIgnoreCaseOrderByFirstNameAsc(String lastName);

    @Query("SELECT DISTINCT e.passenger FROM Booking e WHERE e.agent.id = :bookingAgentId")
    List<Passenger> findPassengersOfBookingAgent(@Param("bookingAgentId") UUID bookingAgentId);
}
