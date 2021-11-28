package com.carcity.CarCity.Backend.dataentities;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;



@Repository
public interface ApplicationUserRepo extends JpaRepository<ApplicationUser,Integer> {
	ApplicationUser findByCell(long cell);
	ApplicationUser findBySessiontoken(String sessiontoken);
	
	List<ApplicationUser> findAllByUt(UserTypes ut);

	@Query("select a.id from ApplicationUser a where a.ut=?1")
	Set<Integer> getAllSps(UserTypes ut);
}
