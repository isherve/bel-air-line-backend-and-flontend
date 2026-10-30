package com.belairline.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.belairline.model.AncillaryPurchase;
import com.belairline.model.Booking;
import com.belairline.model.BookingStatus;
import com.belairline.repository.AncillaryPurchaseRepository;
import com.belairline.repository.BookingRepository;

@Service
public class AncillaryPurchaseService {

    @Autowired
    private AncillaryPurchaseRepository ancillaryPurchaseRepository;

    @Autowired
    private BookingRepository bookingRepository;

    public String saveAncillaryPurchase(UUID bookingId, AncillaryPurchase ancillaryPurchase) {
        Optional<Booking> existing = bookingRepository.findById(bookingId);
        if (existing.isEmpty()) {
            return "The booking with that id does not exist";
        }
        Booking booking = existing.get();
        if (booking.getStatus() != BookingStatus.TICKETED) {
            return "Ancillary purchases are only for TICKETED bookings";
        }
        ancillaryPurchase.setPurchaseDate(LocalDate.now());
        ancillaryPurchase.setBooking(booking);
        ancillaryPurchaseRepository.save(ancillaryPurchase);
        return "Ancillary purchase saved successfully";
    }

    public List<AncillaryPurchase> getPurchasesOfPassenger(String passportNumber) {
        return ancillaryPurchaseRepository.findByPassengerPassportNumber(passportNumber);
    }

    public String deleteAncillaryPurchase(UUID id) {
        if (!ancillaryPurchaseRepository.existsById(id)) {
            return "The ancillary purchase with that id does not exist";
        }
        ancillaryPurchaseRepository.deleteById(id);
        return "Ancillary purchase deleted successfully";
    }
}
