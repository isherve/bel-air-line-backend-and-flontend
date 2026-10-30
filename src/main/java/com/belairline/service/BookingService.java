package com.belairline.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.belairline.model.Booking;
import com.belairline.model.BookingStatus;
import com.belairline.repository.BookingRepository;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    public String saveBooking(Booking booking) {
        bookingRepository.save(booking);
        return "Booking saved successfully";
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Optional<Booking> getBookingById(UUID id) {
        return bookingRepository.findById(id);
    }

    public String updateBooking(UUID id, Booking newBooking) {
        Optional<Booking> existing = bookingRepository.findById(id);
        if (existing.isEmpty()) {
            return "The booking with that id does not exist";
        }
        Booking booking = existing.get();
        booking.setTravelDate(newBooking.getTravelDate());
        booking.setStatus(newBooking.getStatus());
        booking.setFlightNumber(newBooking.getFlightNumber());
        booking.setFareClass(newBooking.getFareClass());
        booking.setPassenger(newBooking.getPassenger());
        booking.setAgent(newBooking.getAgent());
        bookingRepository.save(booking);
        return "Booking updated successfully";
    }

    public String deleteBooking(UUID id) {
        if (!bookingRepository.existsById(id)) {
            return "The booking with that id does not exist";
        }
        bookingRepository.deleteById(id);
        return "Booking deleted successfully";
    }

    public List<Booking> getBookingsByStatus(BookingStatus status) {
        return bookingRepository.findByStatusOrderByTravelDateAsc(status);
    }

    public List<Booking> getBookingsBetween(LocalDate start, LocalDate end) {
        return bookingRepository.findByTravelDateBetweenOrderByTravelDateAsc(start, end);
    }
}
