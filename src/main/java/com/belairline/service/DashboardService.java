package com.belairline.service;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.belairline.model.BookingStatus;
import com.belairline.repository.AncillaryPurchaseRepository;
import com.belairline.repository.BookingAgentRepository;
import com.belairline.repository.BookingRepository;
import com.belairline.repository.PassengerRepository;
import com.belairline.repository.SalesDeskRepository;

@Service
public class DashboardService {

    @Autowired
    private PassengerRepository passengerRepository;

    @Autowired
    private BookingAgentRepository bookingAgentRepository;

    @Autowired
    private SalesDeskRepository salesDeskRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private AncillaryPurchaseRepository ancillaryPurchaseRepository;

    public Map<String, Long> getSummary() {
        Map<String, Long> summary = new LinkedHashMap<>();
        summary.put("passengers", passengerRepository.count());
        summary.put("agents", bookingAgentRepository.count());
        summary.put("freeDesks", (long) salesDeskRepository.findFreeSalesDesks().size());
        summary.put("bookingsToday", bookingRepository.countByTravelDate(LocalDate.now()));
        summary.put("reserved", bookingRepository.countByStatus(BookingStatus.RESERVED));
        summary.put("purchases", ancillaryPurchaseRepository.count());
        return summary;
    }
}
