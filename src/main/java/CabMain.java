import booking.models.Booking;
import booking.service.BookingServiceImpl;
import booking.service.IBookingService;
import driver.models.Driver;
import driver.services.DriverServiceImpl;
import driver.services.IDriverService;
import rider.models.Rider;
import rider.services.IRiderService;
import rider.services.RiderServiceImpl;
import storage.IStorageService;
import storage.StorageServiceImpl;
import vehicle.models.Vehicle;
import vehicle.services.IVehicleService;
import vehicle.services.VehicleServiceImpl;

import java.util.List;

public class CabMain {
    private static IStorageService storageService = new StorageServiceImpl();
    private static IRiderService riderService = new RiderServiceImpl(storageService);
    private static IDriverService driverService = new DriverServiceImpl(storageService);
    private static IVehicleService vehicleService = new VehicleServiceImpl(storageService);
    private static IBookingService bookingService = new BookingServiceImpl(vehicleService, storageService);

    public static void main(String args[]){
        Rider rider = new Rider();
        rider.setName("harsh");
        rider.setCountryCode("+91");
        rider.setPhoneNumber("910");
        riderService.register(rider);

        Driver driver = new Driver();
        driver.setName("harsh Driver");
        driver.setCountryCode("+91");
        driver.setPhoneNumber("9431");
        driverService.register(driver);

        Vehicle vehicle = new Vehicle();
        vehicle.setCarNumber("KA01HK");
        vehicle.setLat(1D);
        vehicle.setLon(1D);
        vehicleService.registerVehicle(vehicle);

        vehicle.setLat(2D);
        vehicle.setLon(2D);
        vehicleService.updateLocation(vehicle);

        bookingService.book("+91910", 1D, 2D);

        List<Booking> bookingHistory = bookingService.history("+91910");
        System.out.println("bookingHistory"+bookingHistory);


    }
}
