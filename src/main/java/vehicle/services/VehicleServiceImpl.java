package vehicle.services;

import storage.IStorageService;
import vehicle.models.Vehicle;

public class VehicleServiceImpl implements IVehicleService {
    IStorageService storageService;

    private static final double MAX_DISTANCE = 110D;

    public VehicleServiceImpl(IStorageService storageService) {
        this.storageService = storageService;
    }

    @Override
    public Boolean registerVehicle(Vehicle vehicle) {
        this.storageService.saveVehicle(vehicle);
        return true;
    }

    @Override
    public Boolean updateLocation(Vehicle vehicle) {
        this.storageService.updateLocation(vehicle);
        return true;
    }

    @Override
    public Vehicle find(Double lat, Double lon) {
        Vehicle vehicle = this.storageService.find(lat, lon, MAX_DISTANCE);
        if(vehicle == null){
            throw new RuntimeException("Vehicle not available");
        }
        return vehicle;
    }
}
