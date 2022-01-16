package bicycle.dao.impl;

import bicycle.dao.BicycleDao;
import bicycle.exception.DataProcessingException;
import bicycle.lib.Dao;
import bicycle.model.Bicycle;
import bicycle.model.Manufacturer;
import bicycle.model.User;
import bicycle.util.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Dao
public class BicycleDaoImpl implements BicycleDao {
    private static final int ZERO_PLACEHOLDER = 0;
    private static final int SHIFT = 2;

    @Override
    public Bicycle create(Bicycle bicycle) {
        String insertQuery = "INSERT INTO bicycles (model, manufacturer_id)"
                + "VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createCarStatement =
                        connection.prepareStatement(
                             insertQuery, Statement.RETURN_GENERATED_KEYS)) {
            createCarStatement.setString(1, bicycle.getModel());
            createCarStatement.setLong(2, bicycle.getManufacturer().getId());
            createCarStatement.executeUpdate();
            ResultSet resultSet = createCarStatement.getGeneratedKeys();
            if (resultSet.next()) {
                bicycle.setId(resultSet.getObject(1, Long.class));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create bicycle " + bicycle, e);
        }
        insertAllUsers(bicycle);
        return bicycle;
    }

    @Override
    public Optional<Bicycle> get(Long id) {
        String selectQuery = "SELECT c.id as id, "
                + "model, "
                + "manufacturer_id, "
                + "m.name as manufacturer_name, "
                + "m.country as manufacturer_country "
                + "FROM bicycles c"
                + " JOIN manufacturers m on c.manufacturer_id = m.id"
                + " where c.id = ? AND c.is_deleted = false";
        Bicycle bicycle = null;
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getCarStatement =
                        connection.prepareStatement(selectQuery)) {
            getCarStatement.setLong(1, id);
            ResultSet resultSet = getCarStatement.executeQuery();
            if (resultSet.next()) {
                bicycle = parseBicycleFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get bicycle by id: " + id, e);
        }
        if (bicycle != null) {
            bicycle.setUsers(getAllUsersByCarId(bicycle.getId()));
        }
        return Optional.ofNullable(bicycle);
    }

    @Override
    public List<Bicycle> getAll() {
        String selectQuery = "SELECT c.id as id, "
                + "model, "
                + "manufacturer_id, "
                + "m.name as manufacturer_name, "
                + "m.country as manufacturer_country "
                + "FROM bicycles c"
                + " JOIN manufacturers m on c.manufacturer_id = m.id"
                + " where c.is_deleted = false";
        List<Bicycle> bicycles = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getAllCarsStatement =
                        connection.prepareStatement(selectQuery)) {
            ResultSet resultSet = getAllCarsStatement.executeQuery();
            while (resultSet.next()) {
                bicycles.add(parseBicycleFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all bicycles", e);
        }
        bicycles.forEach(car -> car.setUsers(getAllUsersByCarId(car.getId())));
        return bicycles;
    }

    @Override
    public Bicycle update(Bicycle bicycle) {
        String selectQuery = "UPDATE bicycles SET model = ?, manufacturer_id = ? WHERE id = ?"
                + " and is_deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateCarStatement =
                        connection.prepareStatement(selectQuery)) {
            updateCarStatement.setString(1, bicycle.getModel());
            updateCarStatement.setLong(2, bicycle.getManufacturer().getId());
            updateCarStatement.setLong(3, bicycle.getId());
            updateCarStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update car " + bicycle, e);
        }
        deleteAllUsersExceptList(bicycle);
        insertAllUsers(bicycle);
        return bicycle;
    }

    @Override
    public boolean delete(Long id) {
        String selectQuery = "UPDATE bicycles SET is_deleted = true WHERE id = ?"
                + " and is_deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement deleteCarStatement =
                         connection.prepareStatement(selectQuery)) {
            deleteCarStatement.setLong(1, id);
            return deleteCarStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete car by id " + id, e);
        }
    }

    @Override
    public List<Bicycle> getAllByUser(Long driverId) {
        String selectQuery = "SELECT c.id as id, "
                + "model, "
                + "manufacturer_id, "
                + "m.name as manufacturer_name, "
                + "m.country as manufacturer_country "
                + "FROM bicycles c"
                + " JOIN manufacturers m on c.manufacturer_id = m.id"
                + " JOIN bicycles_users cd on c.id = cd.bicycle_id"
                + " JOIN users d on cd.user_id = d.id"
                + " where c.is_deleted = false and user_id = ?"
                + " and d.is_deleted = false";
        List<Bicycle> bicycles = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getAllCarsByDriverStatement =
                        connection.prepareStatement(selectQuery)) {
            getAllCarsByDriverStatement.setLong(1, driverId);
            ResultSet resultSet = getAllCarsByDriverStatement.executeQuery();
            while (resultSet.next()) {
                bicycles.add(parseBicycleFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get bicycles cars", e);
        }
        bicycles.forEach(car -> car.setUsers(getAllUsersByCarId(car.getId())));
        return bicycles;
    }

    private void insertAllUsers(Bicycle bicycle) {
        Long carId = bicycle.getId();
        List<User> users = bicycle.getUsers();
        if (users.size() == 0) {
            return;
        }
        String insertQuery = "INSERT INTO bicycles_users (bicycle_id, user_id) VALUES "
                + users.stream().map(driver -> "(?, ?)").collect(Collectors.joining(", "))
                + " ON DUPLICATE KEY UPDATE bicycle_id = bicycle_id";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement linkDriverToCarStatement =
                        connection.prepareStatement(insertQuery)) {
            for (int i = 0; i < users.size(); i++) {
                User user = users.get(i);
                linkDriverToCarStatement.setLong((i * SHIFT) + 1, carId);
                linkDriverToCarStatement.setLong((i * SHIFT) + 2, user.getId());
            }
            linkDriverToCarStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert users " + users, e);
        }
    }

    private void deleteAllUsersExceptList(Bicycle bicycle) {
        Long carId = bicycle.getId();
        List<User> exceptions = bicycle.getUsers();
        int size = exceptions.size();
        String insertQuery = "DELETE FROM bicycles_users WHERE bicycle_id = ? "
                + "AND NOT user_id IN ("
                + ZERO_PLACEHOLDER + ", ?".repeat(size)
                + ");";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteAllDriversExceptLinkedStatement =
                        connection.prepareStatement(insertQuery)) {
            deleteAllDriversExceptLinkedStatement.setLong(1, carId);
            for (int i = 0; i < size; i++) {
                User user = exceptions.get(i);
                deleteAllDriversExceptLinkedStatement.setLong((i) + SHIFT, user.getId());
            }
            deleteAllDriversExceptLinkedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete users " + exceptions, e);
        }
    }

    private List<User> getAllUsersByCarId(Long carId) {
        String selectQuery = "SELECT id, name, license_number, login, password"
                + " FROM bicycles_users cd "
                + "JOIN users d on cd.user_id = d.id "
                + "where bicycle_id = ? AND is_deleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getDriversByCarIdStatement =
                        connection.prepareStatement(selectQuery)) {
            getDriversByCarIdStatement.setLong(1, carId);
            ResultSet resultSet = getDriversByCarIdStatement.executeQuery();
            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                users.add(parseUserFromResultSet(resultSet));
            }
            return users;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all users by bicycle id" + carId, e);
        }
    }

    private User parseUserFromResultSet(ResultSet resultSet) throws SQLException {
        Long driverId = resultSet.getObject("id", Long.class);
        String name = resultSet.getNString("name");
        String licenseNumber = resultSet.getNString("license_number");
        String login = resultSet.getNString("login");
        String password = resultSet.getNString("password");
        User user = new User(name, licenseNumber, login, password);
        user.setId(driverId);
        return user;
    }

    private Bicycle parseBicycleFromResultSet(ResultSet resultSet) throws SQLException {
        Long manufacturerId = resultSet.getObject("manufacturer_id", Long.class);
        String manufacturerName = resultSet.getNString("manufacturer_name");
        String manufacturerCountry = resultSet.getNString("manufacturer_country");
        Manufacturer manufacturer = new Manufacturer(manufacturerName, manufacturerCountry);
        manufacturer.setId(manufacturerId);
        Long carId = resultSet.getObject("id", Long.class);
        String model = resultSet.getNString("model");
        Bicycle bicycle = new Bicycle(model, manufacturer);
        bicycle.setId(carId);
        return bicycle;
    }
}
