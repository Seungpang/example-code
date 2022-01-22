package com.example.houseutils.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.example.houseutils.repository.ApartmentRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ApartmentService {
	private final ApartmentRepository apartmentRepository;

	@Transactional
	public Long getPriceOrThrow(Long apartmentId) {
		return apartmentRepository.findById(apartmentId)
			.orElseThrow(() -> new HouseUtilException(ErrorCode.ENTITY_NOT_FOUND))
			.getPrice();
	}
}
