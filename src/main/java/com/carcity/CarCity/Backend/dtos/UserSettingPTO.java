package com.carcity.CarCity.Backend.dtos;

import java.util.ArrayList;
import java.util.List;

public class UserSettingPTO {
    String settingname;
    List<String> selectablevalues = new ArrayList<String>();
    String selectedvalue;

    public String getSettingname() {
        return settingname;
    }

    public void setSettingname(String settingname) {
        this.settingname = settingname;
    }

    public List<String> getSelectablevalues() {
        return selectablevalues;
    }

    public void setSelectablevalues(List<String> selectablevalues) {
        this.selectablevalues = selectablevalues;
    }

    public String getSelectedvalue() {
        return selectedvalue;
    }

    public void setSelectedvalue(String selectedvalue) {
        this.selectedvalue = selectedvalue;
    }
}
