package database;

import booking.models.Booking;
import driver.models.Driver;
import rider.models.Rider;
import vehicle.models.Vehicle;

import java.sql.*;

public class DatabaseServiceImpl implements IDatabaseService {
    private static Connection dbDriver = null;
    private static String databaseName = "cabdb";
    private static String url = "jdbc:mysql://localhost:3306/" + databaseName;
    private static String userName = "root";
    private static String password = "";

    public static void createDbConnection() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        if(dbDriver == null) {
            synchronized (DatabaseServiceImpl.class) {
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

    @Override
    public Rider findRiderByRiderUniqueId(String riderUniqueId) {
        Rider rider = new Rider();
        String sql = "SELECT id, name, countrycode, phonenumber " +
                "FROM rider where rideruniqueid = " + riderUniqueId;
        try (
                Statement stmt  = dbDriver.createStatement();

                ResultSet rs    = stmt.executeQuery(sql)) {
            while (rs.next()) {
                rider.setId(rs.getLong("id"));
                rider.setName(rs.getString("first_name"));
                rider.setCountryCode(rs.getString("countrycode"));
                rider.setPhoneNumber(rs.getString("phonenumber"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return rider;
    }

    @Override
    public void addRider(Rider rider) throws SQLException {
        String sql = " insert into rider (name, countrycode, phonenumber)"
                + " values (?, ?, ?)";
        try (
             PreparedStatement pstmt = dbDriver.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);) {

            // set parameters for statement
            pstmt.setString(1, rider.getName());
            pstmt.setString(2, rider.getCountryCode());
            pstmt.setString(3, rider.getPhoneNumber());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public Driver findDriverByDriverUniqueId(String driverUniqueId) {
        Driver driver = new Driver();
        String sql = "SELECT id, name, countrycode, phonenumber " +
                "FROM rider where rideruniqueid = " + driverUniqueId;
        try (
                Statement stmt  = dbDriver.createStatement();
                ResultSet rs    = stmt.executeQuery(sql)) {
            while (rs.next()) {
                driver.setId(rs.getLong("id"));
                driver.setName(rs.getString("first_name"));
                driver.setCountryCode(rs.getString("countrycode"));
                driver.setPhoneNumber(rs.getString("phonenumber"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return driver;
    }

    @Override
    public void addDriver(Driver driver) {
        String sql = " insert into driver (name, countrycode, phonenumber)"
                + " values (?, ?, ?)";
        try (
                PreparedStatement pstmt = dbDriver.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);) {

            // set parameters for statement
            pstmt.setString(1, driver.getName());
            pstmt.setString(2, driver.getCountryCode());
            pstmt.setString(3, driver.getPhoneNumber());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public Vehicle findVehicleByCarNumber(String carNumber) {
        Vehicle vehicle = new Vehicle();
        String sql = "SELECT carnumber, lat, lon, type, isavailable, driverId" +
                "FROM vehicle where carnumber = " + carNumber;
        try (
                Statement stmt  = dbDriver.createStatement();
                ResultSet rs    = stmt.executeQuery(sql)) {
            while (rs.next()) {
                vehicle.setCarNumber(rs.getString("carnumber"));
                vehicle.setLat(rs.getDouble("lat"));
                vehicle.setLon(rs.getDouble("lon"));
                vehicle.setType(rs.getString("type"));
                vehicle.setIsAvailable(rs.getBoolean("isavailable"));
                vehicle.setDriverId(rs.getString("driverId"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return vehicle;
    }

    @Override
    public void addVehicle(Vehicle vehicle) {

    }

    @Override
    public void updateVehicleLocation(Vehicle vehicle) {

    }

    @Override
    public Booking findBookingById(String id) {
        Booking booking = new Booking();
        String sql = "SELECT bookingid, rideruserid, carnumber, starttime, endtime, status" +
                "FROM booking where bookingId = " + id;
        try (
                Statement stmt  = dbDriver.createStatement();
                ResultSet rs    = stmt.executeQuery(sql)) {
            while (rs.next()) {
                booking.setCarNumber(rs.getString("carNumber"));
                booking.setBookingId(rs.getString("bookingid"));
                booking.setRiderUserId(rs.getString("rideruserid"));
                booking.setStartTime(rs.getLong("starttime"));
                booking.setEndTime(rs.getLong("endtime"));
                booking.setStatus(rs.getString("status"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return booking;
    }

    @Override
    public void saveBooking(Booking booking) {

    }

    @Override
    public Rider findRiderByUserId(String userId) {
        Rider rider = new Rider();
        String sql = "SELECT id, name, countrycode, phonenumber " +
                "FROM rider where id = " + userId;
        try (
                Statement stmt  = dbDriver.createStatement();

                ResultSet rs    = stmt.executeQuery(sql)) {
            while (rs.next()) {
                rider.setId(rs.getLong("id"));
                rider.setName(rs.getString("first_name"));
                rider.setCountryCode(rs.getString("countrycode"));
                rider.setPhoneNumber(rs.getString("phonenumber"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return rider;
    }
}
