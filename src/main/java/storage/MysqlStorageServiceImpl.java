package storage;

import booking.models.Booking;
import driver.models.Driver;
import rider.models.Rider;
import vehicle.models.Vehicle;

import java.sql.*;
import java.util.*;

public class MysqlStorageServiceImpl implements IStorageService {
    public static final String COMPLETED = "COMPLETED";
    private static Connection dbDriver = null;
    private static String databaseName = "cabdb";
    private static String url = "jdbc:mysql://localhost:3306/" + databaseName +"?useSSL=false";
    private static String userName = "root";
    private static String password = "";
    private MysqlStorageHelper mysqlStorageHelper;

    public MysqlStorageServiceImpl() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        createDbConnection();
        mysqlStorageHelper = new MysqlStorageHelper(dbDriver);
    }

    private static void createDbConnection() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        if(dbDriver == null) {
            synchronized (MysqlStorageServiceImpl.class) {
                if(dbDriver == null){
                    createConnection();
                }
            }
        }
    }

    private static Connection createConnection() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        dbDriver = DriverManager.getConnection(url, userName, password);
        return dbDriver;
    }

    public Boolean saveRider(Rider rider) {
        StringBuffer sb = new StringBuffer();
        sb.append(rider.getCountryCode()).append(rider.getPhoneNumber());
        String riderUniqueId = sb.toString();
        if(!mysqlStorageHelper.findRiderByRiderUniqueId(riderUniqueId).isNull()){
            throw new RuntimeException("Rider already exist in the system");
        }
        mysqlStorageHelper.addRider(rider);
        System.out.println("******* Rider Added Successfully******");
        return true;
    }

    @Override
    public Boolean saveDriver(Driver driver) {
        StringBuffer sb = new StringBuffer();
        sb.append(driver.getCountryCode()).append(driver.getPhoneNumber());
        String driverUniqueId = sb.toString();
        if(!mysqlStorageHelper.findDriverByDriverUniqueId(driverUniqueId).isNull()){
            throw new RuntimeException("Driver already exist in the system");
        }
        mysqlStorageHelper.addDriver(driver);
        System.out.println("******* Driver added Successfully******");
        return true;
    }

    @Override
    public Boolean saveVehicle(Vehicle vehicle) {
        if(mysqlStorageHelper.findVehicleByCarNumber(vehicle.getCarNumber()) != null){
            throw new RuntimeException("Vehicle already exist in the system");
        }
        mysqlStorageHelper.addVehicle(vehicle);
        System.out.println("******* Vehicle added successfully ******");
        return true;
    }

    @Override
    public Boolean updateLocation(Vehicle vehicle) {
        if(mysqlStorageHelper.findVehicleByCarNumber(vehicle.getCarNumber()) == null){
            throw new RuntimeException("Vehicle does not exist in the system");
        }
        Vehicle vehicleInDb = mysqlStorageHelper.findVehicleByCarNumber(vehicle.getCarNumber());
        vehicleInDb.setLat(vehicle.getLat());
        vehicleInDb.setLon(vehicle.getLon());
        mysqlStorageHelper.updateVehicleLocation(vehicleInDb);
        System.out.println("******* vehicle Updating successfully******");
        return true;
    }

    @Override
    public Boolean book(Booking booking) {
        mysqlStorageHelper.saveBooking(booking);
        Rider rider = mysqlStorageHelper.findRiderByUserId(booking.getRiderUserId());
        List<String> bookingHistory = rider.getBookingHistory();
        if(bookingHistory == null){
            bookingHistory = new ArrayList<>();
        }
        bookingHistory.add(booking.getBookingId());
        rider.setBookingHistory(bookingHistory);
        mysqlStorageHelper.addBookingHistory(booking, rider);
        System.out.println("******* Rider history added usccessfully ******");
        return true;
    }

    @Override
    public Vehicle find(Double lat, Double lon, Double maxDistance) {
        List<String> vehicles = mysqlStorageHelper.getAllVehicle();
        TreeMap<Double, Vehicle> distanceVehicleMap = new TreeMap<>();
        for(String vehicleId : vehicles){
            Vehicle vehicle = mysqlStorageHelper.findVehicleByCarNumber(vehicleId);
            Double distance = Math.sqrt((lon)*(vehicle.getLon()) +(lat)*(vehicle.getLat()));
            if(distance < maxDistance) {
                distanceVehicleMap.put(distance, vehicle);
            }
        }
        return distanceVehicleMap.pollFirstEntry().getValue();
    }

    @Override
    public List<Booking> rideHistory(String riderUserId) {
        Rider rider = mysqlStorageHelper.findRiderByUserId(riderUserId);
        List<String> riderBookingIdsHistory = rider.getBookingHistory();
        List<Booking> bookingHistory = new ArrayList<>();
        for(String bookingId : riderBookingIdsHistory){
            Booking booking = mysqlStorageHelper.findBookingById(bookingId);
            bookingHistory.add(booking);
        }
        return bookingHistory;
    }

    @Override
    public Boolean endTrip(Long timeStamp, String bookingId) {
        Booking booking = mysqlStorageHelper.findBookingById(bookingId);
        if(booking == null){
            throw new RuntimeException("No trip by this Id");
        }
        if(booking.getStatus() != null){
            throw new RuntimeException("Booking already ended");
        }
        booking.setEndTime(timeStamp);
        booking.setStatus(COMPLETED);
        mysqlStorageHelper.updateBooking(booking);
        return true;
    }

}
