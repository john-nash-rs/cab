package rider.services;

import rider.models.Rider;
import storage.IStorageService;

public class RiderServiceImpl implements  IRiderService{
    private IStorageService storageService;

    public RiderServiceImpl(IStorageService storageService) {
        this.storageService = storageService;
    }

    public Boolean register(Rider rider) {
        return this.storageService.saveRider(rider);
    }
}
