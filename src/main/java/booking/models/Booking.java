package booking.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Booking {
    private String bookingId;
    private String riderUserId;
    private String carNumber;
    private long startTime;
    private long endTime;
    private String status;
}
