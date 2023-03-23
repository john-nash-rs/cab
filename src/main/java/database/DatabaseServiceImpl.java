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
        String sql = "SELECT id, name, country_code, phone_number " +
                "FROM rider where rider_unique_id = " + riderUniqueId;
        try (
                Statement stmt  = dbDriver.createStatement();

                ResultSet rs    = stmt.executeQuery(sql)) {
            while (rs.next()) {
                rider.setId(rs.getLong("id"));
                rider.setName(rs.getString("name"));
                rider.setCountryCode(rs.getString("country_code"));
                rider.setPhoneNumber(rs.getString("phone_number"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return rider;
    }

    @Override
    public void addRider(Rider rider) {
        String sql = " insert into rider (name, country_code, phone_number)"
                + " values (?, ?, ?)";
        try (
             PreparedStatement pstmt = dbDriver.prepareStatement(sql);) {

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
    public Rider findRiderByUserId(String userId) {
        Rider rider = new Rider();
        String sql = "SELECT id, name, country_code, phone_number " +
                "FROM rider where id = " + userId;
        try (
                Statement stmt  = dbDriver.createStatement();

                ResultSet rs    = stmt.executeQuery(sql)) {
            while (rs.next()) {
                rider.setId(rs.getLong("id"));
                rider.setName(rs.getString("first_name"));
                rider.setCountryCode(rs.getString("country_code"));
                rider.setPhoneNumber(rs.getString("phone_number"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return rider;
    }

    @Override
    public Driver findDriverByDriverUniqueId(String driverUniqueId) {
        Driver driver = new Driver();
        String sql = "SELECT id, name, country_code, phone_number " +
                "FROM rider where rider_unique_id = " + driverUniqueId;
        try (
                Statement stmt  = dbDriver.createStatement();
                ResultSet rs    = stmt.executeQuery(sql)) {
            while (rs.next()) {
                driver.setId(rs.getLong("id"));
                driver.setName(rs.getString("name"));
                driver.setCountryCode(rs.getString("country_code"));
                driver.setPhoneNumber(rs.getString("phone_number"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return driver;
    }

    @Override
    public void addDriver(Driver driver) {
        String sql = " insert into driver (name, country_code, phone_number)"
                + " values (?, ?, ?)";
        try (
                PreparedStatement pstmt = dbDriver.prepareStatement(sql);) {

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
        String sql = "SELECT car_number, lat, lon, type, is_available, driver_id" +
                "FROM vehicle where car_number = " + carNumber;
        try (
                Statement stmt  = dbDriver.createStatement();
                ResultSet rs    = stmt.executeQuery(sql)) {
            while (rs.next()) {
                vehicle.setCarNumber(rs.getString("car_number"));
                vehicle.setLat(rs.getDouble("lat"));
                vehicle.setLon(rs.getDouble("lon"));
                vehicle.setType(rs.getString("type"));
                vehicle.setIsAvailable(rs.getBoolean("is_available"));
                vehicle.setDriverId(rs.getString("driver_id"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return vehicle;
    }

    @Override
    public void addVehicle(Vehicle vehicle) {
        String sql = " insert into vehicle (car_number, lat, lon, type, is_available, driver_id)"
                + " values (?, ?, ?, ?, ?, ?)";
        try (
                PreparedStatement pstmt = dbDriver.prepareStatement(sql);) {

            // set parameters for statement
            pstmt.setString(1, vehicle.getCarNumber());
            pstmt.setDouble(2, vehicle.getLat());
            pstmt.setDouble(3, vehicle.getLon());
            pstmt.setString(4, vehicle.getType());
            pstmt.setBoolean(5, vehicle.getIsAvailable());
            pstmt.setString(6, vehicle.getDriverId());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void updateVehicleLocation(Vehicle vehicle) {
        String sql = "UPDATE vehicle "
                + "SET lat = ? , lon = ?"
                + "WHERE car_number = ?";
        try (
                PreparedStatement pstmt = dbDriver.prepareStatement(sql);) {
            // set parameters for statement
            pstmt.setDouble(1, vehicle.getLat());
            pstmt.setDouble(2, vehicle.getLon());
            pstmt.setString(3, vehicle.getCarNumber());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public Booking findBookingById(String id) {
        Booking booking = new Booking();
        String sql = "SELECT booking_id, rider_user_id, car_number, start_time, end_time, status" +
                "FROM booking where booking_id = " + id;
        try (
                Statement stmt  = dbDriver.createStatement();
                ResultSet rs    = stmt.executeQuery(sql)) {
            while (rs.next()) {
                booking.setCarNumber(rs.getString("car_number"));
                booking.setBookingId(rs.getString("booking_id"));
                booking.setRiderUserId(rs.getString("rider_user_id"));
                booking.setStartTime(rs.getLong("start_time"));
                booking.setEndTime(rs.getLong("end_time"));
                booking.setStatus(rs.getString("status"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return booking;
    }

    @Override
    public void saveBooking(Booking booking) {
        String sql = " insert into vehicle (booking_id, rider_user_id, car_number, start_time, end_time, status)"
                + " values (?, ?, ?, ?, ?, ?, ?)";
        try (
                PreparedStatement pstmt = dbDriver.prepareStatement(sql);) {

            // set parameters for statement
            pstmt.setString(1, booking.getBookingId());
            pstmt.setString(2, booking.getRiderUserId());
            pstmt.setString(3, booking.getCarNumber());
            pstmt.setString(4, booking.getCarNumber());
            pstmt.setLong(5, booking.getStartTime());
            pstmt.setLong(6, booking.getEndTime());
            pstmt.setString(7, booking.getStatus());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void updateBooking(Booking booking) {
        String sql = "UPDATE booking "
                + "SET end_time = ? , status = ?"
                + "WHERE booking_id = ?";
        try (
                PreparedStatement pstmt = dbDriver.prepareStatement(sql);) {
            // set parameters for statement
            pstmt.setString(1, booking.getBookingId());
            pstmt.setLong(2, booking.getEndTime());
            pstmt.setString(3, booking.getStatus());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
