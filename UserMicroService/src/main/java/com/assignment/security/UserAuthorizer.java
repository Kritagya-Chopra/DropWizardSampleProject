package com.assignment.security;


import io.dropwizard.auth.Authorizer;
import org.checkerframework.checker.nullness.qual.Nullable;

import javax.ws.rs.container.ContainerRequestContext;
import java.util.Objects;

public class UserAuthorizer implements Authorizer<User> {
    @Override
    public boolean authorize(User principal, String role, @Nullable ContainerRequestContext containerRequestContext) {
//         Allow any logged in user.
        if (Objects.nonNull(principal)) {
            return true;
        }
        return false;
    }

}