package rider.models;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Rider extends PersonalInfo {
    private List<String> bookingHistory;

    public List<String> getBookingHistory() {
        return bookingHistory;
    }

    public void setBookingHistory(List<String> bookingHistory) {
        this.bookingHistory = bookingHistory;
    }
    @Override
    public boolean isNull() {
        if(this.getId() != null || this.getName() != null || this.getCountryCode() != null || this.getPhoneNumber() != null){
            return false;
        }
        return true;

    }
}
