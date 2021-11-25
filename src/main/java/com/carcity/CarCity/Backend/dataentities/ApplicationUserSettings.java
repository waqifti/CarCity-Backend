package com.carcity.CarCity.Backend.dataentities;

import javax.persistence.*;

@Entity
public class ApplicationUserSettings {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    private String settingname;
    private String settingvalue;

    @OneToOne
    private ApplicationUser user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSettingname() {
        return settingname;
    }

    public void setSettingname(String settingname) {
        this.settingname = settingname;
    }

    public String getSettingvalue() {
        return settingvalue;
    }

    public void setSettingvalue(String settingvalue) {
        this.settingvalue = settingvalue;
    }

    public ApplicationUser getUser() {
        return user;
    }

    public void setUser(ApplicationUser user) {
        this.user = user;
    }
}
