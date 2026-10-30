package com.belairline.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.belairline.model.Booking;
import com.belairline.model.BookingStatus;
import com.belairline.repository.AncillaryPurchaseRepository;
import com.belairline.repository.BookingRepository;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private AncillaryPurchaseRepository ancillaryPurchaseRepository;

    public String saveBooking(Booking booking) {
        if (booking.getTravelDate().isBefore(LocalDate.now())) {
            return "Booking date cannot be in the past";
        }
        if (bookingRepository.existsByAgentAndTravelDateAndStatusNot(booking.getAgent(), booking.getTravelDate(), BookingStatus.CANCELLED)) {
            return "Agent already has a booking on that date";
        }
        booking.setStatus(BookingStatus.RESERVED);
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
        booking.setFlightNumber(newBooking.getFlightNumber());
        booking.setFareClass(newBooking.getFareClass());
        booking.setPassenger(newBooking.getPassenger());
        booking.setAgent(newBooking.getAgent());
        bookingRepository.save(booking);
        return "Booking updated successfully";
    }

    public String deleteBooking(UUID id) {
        Optional<Booking> existing = bookingRepository.findById(id);
        if (existing.isEmpty()) {
            return "The booking with that id does not exist";
        }
        if (ancillaryPurchaseRepository.existsByBooking(existing.get())) {
            return "That booking has ancillary purchases, delete them first";
        }
        bookingRepository.deleteById(id);
        return "Booking deleted successfully";
    }

    public String updateBookingStatus(UUID id, BookingStatus newStatus) {
        Optional<Booking> existing = bookingRepository.findById(id);
        if (existing.isEmpty()) {
            return "The booking with that id does not exist";
        }
        Booking booking = existing.get();
        BookingStatus current = booking.getStatus();
        boolean allowed = false;
        if (current == BookingStatus.RESERVED) {
            allowed = newStatus == BookingStatus.PAID || newStatus == BookingStatus.CANCELLED;
        } else if (current == BookingStatus.PAID) {
            allowed = newStatus == BookingStatus.TICKETED || newStatus == BookingStatus.CANCELLED;
        }
        if (!allowed) {
            return "Cannot change status from " + current + " to " + newStatus;
        }
        booking.setStatus(newStatus);
        bookingRepository.save(booking);
        return "Booking status updated successfully";
    }

    public Page<Booking> getBookingsPage(Pageable pageable) {
        return bookingRepository.findAll(pageable);
    }

    public List<Object[]> getBookingCountsPerStatus() {
        return bookingRepository.countBookingsPerStatus();
    }

    @Transactional
    public String cancelBookingsOfDay(UUID bookingAgentId, LocalDate date) {
        int cancelled = bookingRepository.cancelBookingsOfDay(bookingAgentId, date);
        return cancelled + " bookings cancelled";
    }

    public List<Booking> getBookingsByStatus(BookingStatus status) {
        return bookingRepository.findByStatusOrderByTravelDateAsc(status);
    }

    public List<Booking> getBookingsBetween(LocalDate start, LocalDate end) {
        return bookingRepository.findByTravelDateBetweenOrderByTravelDateAsc(start, end);
    }
}
