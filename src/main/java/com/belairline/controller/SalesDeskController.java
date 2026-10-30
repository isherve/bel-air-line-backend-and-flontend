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

import com.belairline.model.SalesDesk;
import com.belairline.service.SalesDeskService;

@RestController
@RequestMapping("/api/sales-desks")
public class SalesDeskController {

    @Autowired
    private SalesDeskService salesDeskService;

    @PostMapping("/save")
    public ResponseEntity<String> saveSalesDesk(@RequestBody SalesDesk salesDesk) {
        String message = salesDeskService.saveSalesDesk(salesDesk);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<SalesDesk>> getAllSalesDesks() {
        return new ResponseEntity<>(salesDeskService.getAllSalesDesks(), HttpStatus.OK);
    }

    @GetMapping("/free")
    public ResponseEntity<List<SalesDesk>> getFreeSalesDesks() {
        return new ResponseEntity<>(salesDeskService.getFreeSalesDesks(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSalesDeskById(@PathVariable UUID id) {
        Optional<SalesDesk> salesDesk = salesDeskService.getSalesDeskById(id);
        if (salesDesk.isPresent()) {
            return new ResponseEntity<>(salesDesk.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>("The sales desk with that id does not exist", HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateSalesDesk(@PathVariable UUID id, @RequestBody SalesDesk salesDesk) {
        String message = salesDeskService.updateSalesDesk(id, salesDesk);
        if (message.equals("Sales desk updated successfully")) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteSalesDesk(@PathVariable UUID id) {
        String message = salesDeskService.deleteSalesDesk(id);
        if (message.equals("Sales desk deleted successfully")) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }
}
