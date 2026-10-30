package com.belairline.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.belairline.model.BookingAgent;
import com.belairline.service.BookingAgentService;

@RestController
@RequestMapping("/api/booking-agents")
public class BookingAgentController {

    @Autowired
    private BookingAgentService bookingAgentService;

    @PostMapping("/save")
    public ResponseEntity<String> saveBookingAgent(@RequestBody BookingAgent bookingAgent) {
        String message = bookingAgentService.saveBookingAgent(bookingAgent);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<BookingAgent>> getAllBookingAgents() {
        return new ResponseEntity<>(bookingAgentService.getAllBookingAgents(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBookingAgentById(@PathVariable UUID id) {
        Optional<BookingAgent> bookingAgent = bookingAgentService.getBookingAgentById(id);
        if (bookingAgent.isPresent()) {
            return new ResponseEntity<>(bookingAgent.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>("The booking agent with that id does not exist", HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateBookingAgent(@PathVariable UUID id, @RequestBody BookingAgent bookingAgent) {
        String message = bookingAgentService.updateBookingAgent(id, bookingAgent);
        if (message.equals("Booking agent updated successfully")) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteBookingAgent(@PathVariable UUID id) {
        String message = bookingAgentService.deleteBookingAgent(id);
        if (message.equals("Booking agent deleted successfully")) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }
}
