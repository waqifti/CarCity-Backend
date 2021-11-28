package com.carcity.CarCity.Backend.projections;

import com.carcity.CarCity.Backend.dataentities.ApplicationUser;

public interface ProjectionOnlyUser {
    ApplicationUser getAssignedto();
}
