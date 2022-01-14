package bicycle.service;

import bicycle.model.User;
import java.util.Optional;

public interface UserService extends GenericService<User> {
    Optional<User> findByLogin(String login);
}
