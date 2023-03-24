package vehicle.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vehicle {
    private String carNumber;
    private Double lat;
    private Double lon;
    private String type;
    private Boolean isAvailable;
    private String driverId;

    public boolean isNull() {
        if(this.getCarNumber() != null || this.getLat() != null || this.getLon() != null || this.getType() != null || this.getIsAvailable() != null || this.getDriverId() != null){
            return false;
        }
        return true;

    }
}
