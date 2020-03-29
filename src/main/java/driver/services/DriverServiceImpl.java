package driver.services;

import driver.models.Driver;
import storage.IStorageService;

public class DriverServiceImpl implements IDriverService {
    private IStorageService storageService;

    public DriverServiceImpl(IStorageService storageService) {
        this.storageService = storageService;
    }

    public Boolean register(Driver driver) {
        this.storageService.saveDriver(driver);
        return true;
    }
}
