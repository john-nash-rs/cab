package storage;

import booking.models.Booking;
import driver.models.Driver;
import rider.models.Rider;
import vehicle.models.Vehicle;

import java.util.List;

public interface IStorageService {
    Boolean saveRider(Rider rider);
    Boolean saveDriver(Driver driver);
    Boolean saveVehicle(Vehicle vehicle);
    Boolean updateLocation(Vehicle vehicle);
    Boolean book(Booking booking);
    Vehicle find(Double lat, Double lon, Double maxDistance);
    List<Booking> rideHistory(String riderUserId);
    Boolean endTrip(Long timeStamp, String bookingId);
}
