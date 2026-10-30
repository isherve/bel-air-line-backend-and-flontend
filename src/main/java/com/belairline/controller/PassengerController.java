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

import com.belairline.model.Passenger;
import com.belairline.service.PassengerService;

@RestController
@RequestMapping("/api/passengers")
public class PassengerController {

    @Autowired
    private PassengerService passengerService;

    @PostMapping("/save")
    public ResponseEntity<String> savePassenger(@RequestBody Passenger passenger) {
        String message = passengerService.savePassenger(passenger);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Passenger>> getAllPassengers() {
        return new ResponseEntity<>(passengerService.getAllPassengers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPassengerById(@PathVariable UUID id) {
        Optional<Passenger> passenger = passengerService.getPassengerById(id);
        if (passenger.isPresent()) {
            return new ResponseEntity<>(passenger.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>("The passenger with that id does not exist", HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updatePassenger(@PathVariable UUID id, @RequestBody Passenger passenger) {
        String message = passengerService.updatePassenger(id, passenger);
        if (message.equals("Passenger updated successfully")) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePassenger(@PathVariable UUID id) {
        String message = passengerService.deletePassenger(id);
        if (message.equals("Passenger deleted successfully")) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }
}
