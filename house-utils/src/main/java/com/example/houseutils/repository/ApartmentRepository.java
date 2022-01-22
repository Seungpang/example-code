package com.example.houseutils.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.houseutils.entity.Apartment;

public interface ApartmentRepository extends JpaRepository<Apartment, Long> {
}
