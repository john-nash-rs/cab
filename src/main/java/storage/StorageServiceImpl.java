package storage;

import booking.models.Booking;
import driver.models.Driver;
import rider.models.Rider;
import vehicle.models.Vehicle;

import java.util.*;

public class StorageServiceImpl implements IStorageService {
    public static final String COMPLETED = "COMPLETED";
    private Map<String, Rider> riderStorage;
    private Map<String, Driver> driverStorage;
    private Map<String, Vehicle> vehicleStorage;
    private Map<String, Booking> bookingStorage;

    public StorageServiceImpl() {
        this.riderStorage = new HashMap<>();
        this.driverStorage = new HashMap<>();
        this.vehicleStorage = new HashMap<>();
        this.bookingStorage = new HashMap<>();
    }

    public Boolean saveRider(Rider rider) {
        StringBuffer sb = new StringBuffer();
        sb.append(rider.getCountryCode()).append(rider.getPhoneNumber());
        String riderUniqueId = sb.toString();
        if(this.riderStorage.get(riderUniqueId) != null){
            throw new RuntimeException("Rider already exist in the system");
        }
        this.riderStorage.put(riderUniqueId, rider);
        System.out.println("******* riderStorage******"+riderStorage);
        return true;
    }

    @Override
    public Boolean saveDriver(Driver driver) {
        StringBuffer sb = new StringBuffer();
        sb.append(driver.getCountryCode()).append(driver.getPhoneNumber());
        String driverUniqueId = sb.toString();
        if(this.driverStorage.get(driverUniqueId) != null){
            throw new RuntimeException("Driver already exist in the system");
        }
        this.driverStorage.put(driverUniqueId, driver);
        System.out.println("******* driverStorage******"+driverStorage);
        return true;
    }

    @Override
    public Boolean saveVehicle(Vehicle vehicle) {
        if(this.vehicleStorage.get(vehicle.getCarNumber()) != null){
            throw new RuntimeException("Vehicle already exist in the system");
        }
        this.vehicleStorage.put(vehicle.getCarNumber(), vehicle);
        System.out.println("******* vehicleStorage******"+vehicleStorage);
        return true;
    }

    @Override
    public Boolean updateLocation(Vehicle vehicle) {
        if(this.vehicleStorage.get(vehicle.getCarNumber()) == null){
            throw new RuntimeException("Vehicle does not exist in the system");
        }
        Vehicle vehicleInDb = this.vehicleStorage.get(vehicle.getCarNumber());
        vehicleInDb.setLat(vehicle.getLat());
        vehicleInDb.setLon(vehicle.getLon());
        this.vehicleStorage.put(vehicle.getCarNumber(), vehicleInDb);
        System.out.println("******* vehicleStorage After Updating lat lon******"+vehicleStorage);
        return true;
    }

    @Override
    public Boolean book(Booking booking) {
        this.bookingStorage.put(booking.getBookingId(), booking);
        Rider rider = this.riderStorage.get(booking.getRiderUserId());
        List<String> bookingHistory = rider.getBookingHistory();
        if(bookingHistory == null){
            bookingHistory = new ArrayList<>();
        }
        bookingHistory.add(booking.getBookingId());
        rider.setBookingHistory(bookingHistory);
        this.riderStorage.put(booking.getRiderUserId(), rider);
        System.out.println("******* bookingStorage ******"+bookingStorage);
        System.out.println("******* riderStorage after updating ride history ******"+riderStorage.get(booking.getRiderUserId()).getBookingHistory().toString());
        return true;
    }

    @Override
    public Vehicle find(Double lat, Double lon, Double maxDistance) {
        TreeMap<Double, Vehicle> distanceVehicleMap = new TreeMap<>();
        for(String vehicleId : this.vehicleStorage.keySet()){
            Vehicle vehicle = this.vehicleStorage.get(vehicleId);
            Double distance = Math.sqrt((lon)*(vehicle.getLon()) +(lat)*(vehicle.getLat()));
            if(distance < maxDistance) {
                distanceVehicleMap.put(distance, vehicle);
            }
        }
        return distanceVehicleMap.pollFirstEntry().getValue();
    }

    @Override
    public List<Booking> rideHistory(String riderUserId) {
        Rider rider = this.riderStorage.get(riderUserId);
        List<String> riderBookingIdsHistory = rider.getBookingHistory();
        List<Booking> bookingHistory = new ArrayList<>();
        for(String bookingId : riderBookingIdsHistory){
            Booking booking = this.bookingStorage.get(bookingId);
            bookingHistory.add(booking);
        }
        return bookingHistory;
    }

    @Override
    public Boolean endTrip(Long timeStamp, String bookingId) {
        Booking booking = this.bookingStorage.get(bookingId);
        if(booking == null){
            throw new RuntimeException("No trip by this Id");
        }
        if(booking.getStatus() != null){
            throw new RuntimeException("Booking already ended");
        }
        booking.setEndTime(timeStamp);
        booking.setStatus(COMPLETED);
        return true;
    }
}
