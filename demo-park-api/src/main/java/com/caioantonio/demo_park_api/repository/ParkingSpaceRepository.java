package com.caioantonio.demo_park_api.repository;

import com.caioantonio.demo_park_api.entity.ParkingSpace;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingSpaceRepository extends JpaRepository<ParkingSpace, Long> {
}
