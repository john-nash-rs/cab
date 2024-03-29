package storage;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class StorageFactory {
    static Map<String, IStorageService> storageDictonary = new HashMap<>();
    public StorageFactory() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        storageDictonary.put("InMemory", new InMemoryStorageServiceImpl());
//        storageDictonary.put("Mysql", new MysqlStorageServiceImpl());
    }
    public static IStorageService getStorageInstance(String storageType){
        if(storageDictonary.get(storageType) == null){
            System.out.println("********** Invalid storage type selection ************");
            throw new RuntimeException("Invalid storage");
        }
        return storageDictonary.get(storageType);
    }
}
