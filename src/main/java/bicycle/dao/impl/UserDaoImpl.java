package bicycle.dao.impl;

import bicycle.dao.UserDao;
import bicycle.exception.DataProcessingException;
import bicycle.lib.Dao;
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

@Dao
public class UserDaoImpl implements UserDao {
    @Override
    public User create(User user) {
        String query = "INSERT INTO users (name, license_number, login, password) "
                + "VALUES (?, ?, ?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createDriverStatement = connection.prepareStatement(query,
                        Statement.RETURN_GENERATED_KEYS)) {
            createDriverStatement.setString(1, user.getName());
            createDriverStatement.setString(2, user.getLicenseNumber());
            createDriverStatement.setString(3, user.getLogin());
            createDriverStatement.setString(4, user.getPassword());
            createDriverStatement.executeUpdate();
            ResultSet resultSet = createDriverStatement.getGeneratedKeys();
            if (resultSet.next()) {
                user.setId(resultSet.getObject(1, Long.class));
            }
            return user;
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't create "
                    + user + ". ", e);
        }
    }

    @Override
    public Optional<User> get(Long id) {
        String query = "SELECT id, name, license_number, login, password"
                + " FROM users WHERE id = ? AND is_deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getDriverStatement = connection.prepareStatement(query)) {
            getDriverStatement.setLong(1, id);
            ResultSet resultSet = getDriverStatement.executeQuery();
            User user = null;
            if (resultSet.next()) {
                user = getUser(resultSet);
            }
            return Optional.ofNullable(user);
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't get user by id " + id, e);
        }
    }

    @Override
    public List<User> getAll() {
        String query = "SELECT id, name, license_number, login, password"
                + " FROM users WHERE is_deleted = FALSE";
        List<User> users = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getAllDriversStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = getAllDriversStatement.executeQuery();
            while (resultSet.next()) {
                users.add(getUser(resultSet));
            }
            return users;
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't get a list of users from driversDB.",
                    e);
        }
    }

    @Override
    public User update(User user) {
        String query = "UPDATE users "
                + "SET name = ?, license_number = ? "
                + "SET login = ?, password = ? "
                + "WHERE id = ? AND is_deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateDriverStatement
                        = connection.prepareStatement(query)) {
            updateDriverStatement.setString(1, user.getName());
            updateDriverStatement.setString(2, user.getLicenseNumber());
            updateDriverStatement.setString(3, user.getLogin());
            updateDriverStatement.setString(4, user.getPassword());
            updateDriverStatement.setLong(5, user.getId());
            updateDriverStatement.executeUpdate();
            return user;
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't update "
                    + user + " in driversDB.", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String query = "UPDATE users SET is_deleted = TRUE WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteDriverStatement = connection.prepareStatement(query)) {
            deleteDriverStatement.setLong(1, id);
            return deleteDriverStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't delete user with id " + id, e);
        }
    }

    @Override
    public Optional<User> findByLogin(String login) {
        String query = "SELECT id, name, license_number, login, password"
                + " FROM users WHERE login = ? AND is_deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
                    PreparedStatement preparedStatement =
                        connection.prepareStatement(query)) {
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            User user = null;
            if (resultSet.next()) {
                user = getUser(resultSet);
            }
            return Optional.ofNullable(user);
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't find a user with login " + login, e);
        }
    }

    private User getUser(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject("id", Long.class);
        String name = resultSet.getString("name");
        String licenseNumber = resultSet.getString("license_number");
        String login = resultSet.getString("login");
        String password = resultSet.getString("password");
        User user = new User(name, licenseNumber, login, password);
        user.setId(id);
        return user;
    }
}
