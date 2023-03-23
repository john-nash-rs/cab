package database;

import booking.models.Booking;
import driver.models.Driver;
import rider.models.Rider;
import vehicle.models.Vehicle;

import java.sql.SQLException;

public interface IDatabaseService {
    public Rider findRiderByRiderUniqueId(String riderUniqueId);

    public void addRider(Rider rider);

    public Rider findRiderByUserId(String userId);

    public Driver findDriverByDriverUniqueId(String driverUniqueId);

    public void addDriver(Driver driver);

    public Vehicle findVehicleByCarNumber(String carNumber);

    public void addVehicle(Vehicle vehicle);

    public void updateVehicleLocation(Vehicle vehicle);

    public Booking findBookingById(String id);

    public void saveBooking(Booking booking);

    public void updateBooking(Booking booking);

}
