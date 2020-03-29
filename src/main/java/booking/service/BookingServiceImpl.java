package booking.service;

import booking.models.Booking;
import storage.IStorageService;
import vehicle.models.Vehicle;
import vehicle.services.IVehicleService;

import java.util.List;
import java.util.UUID;

public class BookingServiceImpl implements IBookingService {
    IVehicleService vehicleService;
    IStorageService storageService;

    public BookingServiceImpl(IVehicleService vehicleService, IStorageService storageService) {
        this.vehicleService = vehicleService;
        this.storageService = storageService;
    }

    @Override
    public Booking book(String riderUserId, Double lat, Double lon) {
        //TODO check for valid rider
        //find cab
        Vehicle vehicle = vehicleService.find(lat, lon);
        //TODO lock the cab
        Booking booking = new Booking();
        booking.setBookingId(UUID.randomUUID().toString());
        booking.setCarNumber(vehicle.getCarNumber());
        booking.setRiderUserId(riderUserId);
        storageService.book(booking);
        return booking;
    }

    @Override
    public List<Booking> history(String riderUserId) {
        //TODO check for valid rider
        List<Booking> bookingHistory = storageService.rideHistory(riderUserId);
        return bookingHistory;
    }

    @Override
    public Boolean endTrip(Long timeStamp, String bookingId) {
        storageService.endTrip(timeStamp, bookingId);
        return true;
    }


}
