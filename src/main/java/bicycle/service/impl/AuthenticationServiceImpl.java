package bicycle.service.impl;

import bicycle.exception.AuthenticationException;
import bicycle.lib.Inject;
import bicycle.lib.Service;
import bicycle.model.User;
import bicycle.service.AuthenticationService;
import bicycle.service.UserService;
import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;

    @Override
    public User login(String login, String password) throws AuthenticationException {
        Optional<User> driver = userService.findByLogin(login);
        if (driver.isPresent() && driver.get().getPassword().equals(password)) {
            return driver.get();
        }
        throw new AuthenticationException("Login or password was incorrect!");
    }
}
