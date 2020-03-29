package rider.models;

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
}
