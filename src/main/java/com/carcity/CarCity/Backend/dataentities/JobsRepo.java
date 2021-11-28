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
	Jobs findByCreatedbyStateIn(ApplicationUser customer,JobState ...states);
	List<Jobs> findAllByCreatedbyStateIn(ApplicationUser customer,JobState ...states);
	List<Jobs> findAllByStageIn(JobState js);

	@Query("select j.assignedto.id from Jobs j")
	Set<Integer> getIdsOfAssignedUsers();
}
