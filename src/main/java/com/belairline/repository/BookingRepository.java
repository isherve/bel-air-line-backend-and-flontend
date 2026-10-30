package com.belairline.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.belairline.model.Booking;
import com.belairline.model.BookingAgent;
import com.belairline.model.BookingStatus;

public interface BookingRepository extends JpaRepository<Booking, UUID> {

    List<Booking> findByStatusOrderByTravelDateAsc(BookingStatus status);

    List<Booking> findByTravelDateBetweenOrderByTravelDateAsc(LocalDate start, LocalDate end);

    boolean existsByAgentAndTravelDateAndStatusNot(BookingAgent agent, LocalDate travelDate, BookingStatus status);

    long countByTravelDate(LocalDate travelDate);

    long countByStatus(BookingStatus status);

    @Query("SELECT e.status, COUNT(e) FROM Booking e GROUP BY e.status")
    List<Object[]> countBookingsPerStatus();

    @Modifying
    @Query("UPDATE Booking e SET e.status = com.belairline.model.BookingStatus.CANCELLED "
            + "WHERE e.agent.id = :bookingAgentId AND e.travelDate = :date "
            + "AND e.status NOT IN (com.belairline.model.BookingStatus.TICKETED, com.belairline.model.BookingStatus.CANCELLED)")
    int cancelBookingsOfDay(@Param("bookingAgentId") UUID bookingAgentId, @Param("date") LocalDate date);
}
