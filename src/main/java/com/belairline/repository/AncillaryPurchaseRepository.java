package com.belairline.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.belairline.model.AncillaryPurchase;
import com.belairline.model.Booking;

public interface AncillaryPurchaseRepository extends JpaRepository<AncillaryPurchase, UUID> {

    @Query("SELECT d FROM AncillaryPurchase d WHERE d.booking.passenger.passportNumber = :passportNumber ORDER BY d.purchaseDate DESC")
    List<AncillaryPurchase> findByPassengerPassportNumber(@Param("passportNumber") String passportNumber);

    boolean existsByBooking(Booking booking);
}
