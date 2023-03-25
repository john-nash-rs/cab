package driver.models;


import rider.models.PersonalInfo;

import java.lang.reflect.Field;

public class Driver extends PersonalInfo {

    @Override
    public boolean isNull() {
        if(this.getId() != null || this.getName() != null || this.getCountryCode() != null || this.getPhoneNumber() != null){
            return false;
        }
        return true;

    }
}
