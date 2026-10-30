package com.belairline.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.belairline.model.BookingAgent;
import com.belairline.repository.BookingAgentRepository;

@Service
public class BookingAgentService {

    @Autowired
    private BookingAgentRepository bookingAgentRepository;

    public String saveBookingAgent(BookingAgent bookingAgent) {
        bookingAgentRepository.save(bookingAgent);
        return "Booking agent saved successfully";
    }

    public List<BookingAgent> getAllBookingAgents() {
        return bookingAgentRepository.findAll();
    }

    public Optional<BookingAgent> getBookingAgentById(UUID id) {
        return bookingAgentRepository.findById(id);
    }

    public String updateBookingAgent(UUID id, BookingAgent newBookingAgent) {
        Optional<BookingAgent> existing = bookingAgentRepository.findById(id);
        if (existing.isEmpty()) {
            return "The booking agent with that id does not exist";
        }
        BookingAgent bookingAgent = existing.get();
        bookingAgent.setFirstName(newBookingAgent.getFirstName());
        bookingAgent.setLastName(newBookingAgent.getLastName());
        bookingAgent.setEmail(newBookingAgent.getEmail());
        // Relationships left out of the JSON body stay as they are.
        if (newBookingAgent.getSalesDesk() != null) {
            bookingAgent.setSalesDesk(newBookingAgent.getSalesDesk());
        }
        if (newBookingAgent.getRoutes() != null && !newBookingAgent.getRoutes().isEmpty()) {
            bookingAgent.setRoutes(newBookingAgent.getRoutes());
        }
        bookingAgentRepository.save(bookingAgent);
        return "Booking agent updated successfully";
    }

    public String deleteBookingAgent(UUID id) {
        if (!bookingAgentRepository.existsById(id)) {
            return "The booking agent with that id does not exist";
        }
        bookingAgentRepository.deleteById(id);
        return "Booking agent deleted successfully";
    }

    public List<BookingAgent> getBookingAgentsByRoute(String name) {
        return bookingAgentRepository.findByRouteName(name);
    }
}
