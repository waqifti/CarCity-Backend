package com.carcity.CarCity.Backend.dataentities;

import java.util.List;
import java.util.Set;

import com.carcity.CarCity.Backend.projections.ProjectionOnlyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;



@Repository
public interface JobsRepo extends JpaRepository<Jobs,Integer> {
	Jobs findByAssignedto(ApplicationUser assignedto);
	Jobs findByCreatedbyAndStateIn(ApplicationUser customer,List<JobState> states);
	List<Jobs> findAllByCreatedbyAndStateIn(ApplicationUser customer,List<JobState> states);
	List<Jobs> findAllByState(JobState js);

	@Query("select j.assignedto.id from Jobs j")
	Set<Integer> getIdsOfAssignedUsers();
}
