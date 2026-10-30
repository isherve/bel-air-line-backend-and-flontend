package com.belairline.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.belairline.model.Booking;
import com.belairline.model.BookingStatus;

public interface BookingRepository extends JpaRepository<Booking, UUID> {

    List<Booking> findByStatusOrderByTravelDateAsc(BookingStatus status);

    List<Booking> findByTravelDateBetweenOrderByTravelDateAsc(LocalDate start, LocalDate end);
}
