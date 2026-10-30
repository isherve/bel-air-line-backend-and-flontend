package com.belairline.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.belairline.model.BookingAgent;
import com.belairline.model.Route;
import com.belairline.model.SalesDesk;
import com.belairline.repository.BookingAgentRepository;
import com.belairline.repository.RouteRepository;
import com.belairline.repository.SalesDeskRepository;

@Service
public class BookingAgentService {

    @Autowired
    private BookingAgentRepository bookingAgentRepository;

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private SalesDeskRepository salesDeskRepository;

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

    // One transaction so the route and the routes list share the same objects, which contains() compares.
    @Transactional
    public String addRouteToBookingAgent(UUID bookingAgentId, UUID routeId) {
        Optional<BookingAgent> existingAgent = bookingAgentRepository.findById(bookingAgentId);
        if (existingAgent.isEmpty()) {
            return "The booking agent with that id does not exist";
        }
        Optional<Route> existingRoute = routeRepository.findById(routeId);
        if (existingRoute.isEmpty()) {
            return "That route does not exist";
        }
        BookingAgent bookingAgent = existingAgent.get();
        Route route = existingRoute.get();
        if (bookingAgent.getRoutes().contains(route)) {
            return "The booking agent already has that route";
        }
        bookingAgent.getRoutes().add(route);
        bookingAgentRepository.save(bookingAgent);
        return "Route added to the booking agent";
    }

    public String changeSalesDesk(UUID bookingAgentId, int deskNumber) {
        Optional<BookingAgent> existingAgent = bookingAgentRepository.findById(bookingAgentId);
        if (existingAgent.isEmpty()) {
            return "The booking agent with that id does not exist";
        }
        Optional<SalesDesk> existingDesk = salesDeskRepository.findByDeskNumber(deskNumber);
        if (existingDesk.isEmpty()) {
            return "The sales desk with that deskNumber does not exist";
        }
        BookingAgent bookingAgent = existingAgent.get();
        SalesDesk salesDesk = existingDesk.get();
        // Asking for the desk the agent already sits at is not a clash with another agent.
        SalesDesk currentDesk = bookingAgent.getSalesDesk();
        boolean sameDesk = currentDesk != null && currentDesk.getId().equals(salesDesk.getId());
        if (!sameDesk && bookingAgentRepository.existsBySalesDesk(salesDesk)) {
            return "That sales desk is taken by another booking agent";
        }
        bookingAgent.setSalesDesk(salesDesk);
        bookingAgentRepository.save(bookingAgent);
        return "Sales desk changed successfully";
    }

    public List<BookingAgent> getBookingAgentsByRoute(String name) {
        return bookingAgentRepository.findByRouteName(name);
    }
}
