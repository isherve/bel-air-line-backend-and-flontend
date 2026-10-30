package com.belairline.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.belairline.model.SalesDesk;
import com.belairline.repository.SalesDeskRepository;

@Service
public class SalesDeskService {

    @Autowired
    private SalesDeskRepository salesDeskRepository;

    public String saveSalesDesk(SalesDesk salesDesk) {
        salesDeskRepository.save(salesDesk);
        return "Sales desk saved successfully";
    }

    public List<SalesDesk> getAllSalesDesks() {
        return salesDeskRepository.findAll();
    }

    public Optional<SalesDesk> getSalesDeskById(UUID id) {
        return salesDeskRepository.findById(id);
    }

    public String updateSalesDesk(UUID id, SalesDesk newSalesDesk) {
        Optional<SalesDesk> existing = salesDeskRepository.findById(id);
        if (existing.isEmpty()) {
            return "The sales desk with that id does not exist";
        }
        SalesDesk salesDesk = existing.get();
        salesDesk.setDeskNumber(newSalesDesk.getDeskNumber());
        salesDesk.setName(newSalesDesk.getName());
        salesDeskRepository.save(salesDesk);
        return "Sales desk updated successfully";
    }

    public String deleteSalesDesk(UUID id) {
        if (!salesDeskRepository.existsById(id)) {
            return "The sales desk with that id does not exist";
        }
        salesDeskRepository.deleteById(id);
        return "Sales desk deleted successfully";
    }

    public List<SalesDesk> getFreeSalesDesks() {
        return salesDeskRepository.findFreeSalesDesks();
    }
}
