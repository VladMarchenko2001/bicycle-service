package bicycle.service;

import bicycle.exception.AuthenticationException;
import bicycle.model.User;

public interface AuthenticationService {
    User login(String login, String password) throws AuthenticationException;
}
