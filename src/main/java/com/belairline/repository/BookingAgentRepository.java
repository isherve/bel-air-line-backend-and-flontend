package com.belairline.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.belairline.model.BookingAgent;

public interface BookingAgentRepository extends JpaRepository<BookingAgent, UUID> {
}
