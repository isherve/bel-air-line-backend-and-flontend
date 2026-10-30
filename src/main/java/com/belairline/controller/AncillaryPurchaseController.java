package com.belairline.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.belairline.model.AncillaryPurchase;
import com.belairline.service.AncillaryPurchaseService;

@RestController
@RequestMapping("/api/ancillary-purchases")
public class AncillaryPurchaseController {

    @Autowired
    private AncillaryPurchaseService ancillaryPurchaseService;

    @PostMapping("/save")
    public ResponseEntity<String> saveAncillaryPurchase(@RequestParam UUID bookingId, @RequestBody AncillaryPurchase ancillaryPurchase) {
        String message = ancillaryPurchaseService.saveAncillaryPurchase(bookingId, ancillaryPurchase);
        if (message.equals("Ancillary purchase saved successfully")) {
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        } else if (message.equals("The booking with that id does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/passenger/{passportNumber}")
    public ResponseEntity<List<AncillaryPurchase>> getPurchasesOfPassenger(@PathVariable String passportNumber) {
        return new ResponseEntity<>(ancillaryPurchaseService.getPurchasesOfPassenger(passportNumber), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteAncillaryPurchase(@PathVariable UUID id) {
        String message = ancillaryPurchaseService.deleteAncillaryPurchase(id);
        if (message.equals("Ancillary purchase deleted successfully")) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }
}
