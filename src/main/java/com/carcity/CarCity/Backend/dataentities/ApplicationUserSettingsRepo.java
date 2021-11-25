package com.carcity.CarCity.Backend.dataentities;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ApplicationUserSettingsRepo extends JpaRepository<ApplicationUserSettings,Integer> {

	ApplicationUserSettings findAllByUserAndSettingname(ApplicationUser user,String settingname);
	List<ApplicationUserSettings> findAllByUser(ApplicationUser user);
}
