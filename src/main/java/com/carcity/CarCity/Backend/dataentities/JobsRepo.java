package com.carcity.CarCity.Backend.dataentities;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface JobsRepo extends JpaRepository<Jobs,Integer> {
	Jobs findByAssignedto(ApplicationUser assignedto);
}
