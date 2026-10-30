package com.belairline.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.belairline.model.Booking;

public interface BookingRepository extends JpaRepository<Booking, UUID> {
}
