package com.caioantonio.demo_park_api.service;

import com.caioantonio.demo_park_api.entity.ParkingSpace;
import com.caioantonio.demo_park_api.exception.CodeUniqueViolationException;
import com.caioantonio.demo_park_api.exception.EntityNotFoundException;
import com.caioantonio.demo_park_api.repository.ParkingSpaceRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ParkingSpaceService {

    private final ParkingSpaceRepository parkingSpaceRepository;

    @Transactional
    public ParkingSpace create(ParkingSpace parkingSpace) {
        try {
            return parkingSpaceRepository.save(parkingSpace);
        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            throw new CodeUniqueViolationException(
                    String.format("Vaga com código %s já cadastrada", parkingSpace.getCode()));
        }
    }

    @ReadOnlyProperty
    public ParkingSpace getByCode(String code) {
        return parkingSpaceRepository.findByCode(code).orElseThrow(
                () -> new EntityNotFoundException(String.format("Vaga com código %s não encontrada", code))
        );
    }
}
