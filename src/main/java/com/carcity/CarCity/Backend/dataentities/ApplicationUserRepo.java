package com.carcity.CarCity.Backend.dataentities;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface ApplicationUserRepo extends JpaRepository<ApplicationUser,Integer> {
	ApplicationUser findByCell(long cell);
	ApplicationUser findBySessiontoken(String sessiontoken);
	
	List<ApplicationUser> findAllByUt(UserTypes ut);
}
