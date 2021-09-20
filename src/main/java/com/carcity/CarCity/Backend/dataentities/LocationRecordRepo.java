package com.carcity.CarCity.Backend.dataentities;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface LocationRecordRepo extends JpaRepository<LocationRecord,Integer> {
	List<LocationRecord> findAllByOf(ApplicationUser of);
	
	
	LocationRecord findTopByOfOrderByTimeondeviceDesc(ApplicationUser of);
}
