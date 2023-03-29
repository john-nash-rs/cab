package booking;

import booking.models.Booking;
import booking.service.BookingServiceImpl;
import booking.service.IBookingService;
import controller.RiderServelet;
import driver.models.Driver;
import driver.services.DriverServiceImpl;
import driver.services.IDriverService;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import rider.models.Rider;
import rider.services.IRiderService;
import rider.services.RiderServiceImpl;
import storage.IStorageService;
import storage.StorageFactory;
import vehicle.models.Vehicle;
import vehicle.services.IVehicleService;
import vehicle.services.VehicleServiceImpl;

import java.sql.SQLException;
import java.util.List;

public class CabMain {
    private static StorageFactory storageFactory;

    static {
        try {
            storageFactory = new StorageFactory();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
    private static IStorageService storageService = StorageFactory.getStorageInstance("Mysql");
//    private static IStorageService storageService = StorageFactory.getStorageInstance("InMemory"); // Argument will be storage type
    private static IRiderService riderService = new RiderServiceImpl(storageService);
    private static IDriverService driverService = new DriverServiceImpl(storageService);
    private static IVehicleService vehicleService = new VehicleServiceImpl(storageService);
    private static IBookingService bookingService = new BookingServiceImpl(vehicleService, storageService);

    public static void start() throws Exception {
        Server server = new Server(8090);
        ServletHandler handler = new ServletHandler();
        ServletHolder holder = new ServletHolder(new RiderServelet(storageService, riderService));
        handler.addServletWithMapping(holder, "/rider");
        server.setHandler(handler);
        server.start();
        server.join();
    }
    public static void main(String args[]) throws Exception {
        start();

        Rider rider = new Rider();
        rider.setName("harsh13443");
        rider.setCountryCode("+913233322");
        rider.setPhoneNumber("91033323432");
        riderService.register(rider);

        Driver driver = new Driver();
        driver.setName("harsh Driver1");
        driver.setCountryCode("+931");
        driver.setPhoneNumber("94331");
        driverService.register(driver);

        Vehicle vehicle = new Vehicle();
        vehicle.setCarNumber("KA01WKT");
        vehicle.setLat(1D);
        vehicle.setLon(1D);
        vehicleService.registerVehicle(vehicle);

        vehicle.setLat(2D);
        vehicle.setLon(2D);
        vehicleService.updateLocation(vehicle);

        bookingService.book("+91910", 1D, 2D);

        List<Booking> bookingHistory = bookingService.history("+91910");
        System.out.println("bookingHistory "+bookingHistory);


    }
}
