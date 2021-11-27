package com.carcity.CarCity.Backend.dtos;

import java.util.List;

public class UserSettingInDO {

    List<UserSettingPTO> settings;

    public List<UserSettingPTO> getSettings() {
        return settings;
    }

    public void setSettings(List<UserSettingPTO> settings) {
        this.settings = settings;
    }
}
