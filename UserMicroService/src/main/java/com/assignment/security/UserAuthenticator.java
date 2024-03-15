package com.assignment.security;


import java.util.Optional;


import com.assignment.core.TokenEncyption;
import com.assignment.dao.UserDao;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;

public class UserAuthenticator implements Authenticator<String, User> {
    @Override
    public Optional<User> authenticate(String token) throws AuthenticationException {



        String data=TokenEncyption.decrypt(token);

        if ("test_token".equals(token)) {
            return Optional.of(new User());
        }
        return Optional.empty();
    }
}