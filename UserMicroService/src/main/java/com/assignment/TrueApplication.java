package com.assignment;


import com.assignment.api.UserController;
import com.assignment.core.UserService;
import com.assignment.dao.UserDao;
import com.assignment.db.UserMap;
import com.assignment.security.User;
import com.assignment.security.UserAuthenticator;
import com.assignment.security.UserAuthorizer;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.UnauthorizedHandler;
import io.dropwizard.auth.oauth.OAuthCredentialAuthFilter;
import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;
import io.dropwizard.jdbi3.JdbiFactory;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.jdbi.v3.core.Jdbi;

import javax.ws.rs.core.Response;


public class TrueApplication extends Application<trueConfiguration> {

    public static void main(final String[] args) throws Exception {
        new TrueApplication().run(args);
    }

    @Override
    public String getName() {
        return "true";
    }

    @Override
    public void initialize(final Bootstrap<trueConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final trueConfiguration configuration, final Environment environment) {
        final JdbiFactory factory = new JdbiFactory();
        final Jdbi jdbi = factory.build(environment, configuration.getDataSourceFactory(), "sql");
        jdbi.registerRowMapper(new UserMap());

        UserDao userDao = jdbi.onDemand(UserDao.class);


        environment.jersey().register(new UserController(new UserService(userDao)));

        environment.jersey()
                .register(new AuthDynamicFeature(new OAuthCredentialAuthFilter.Builder<User>()
                        .setAuthenticator(new UserAuthenticator())
                        .setAuthorizer(new UserAuthorizer())
                        .setPrefix("Bearer")
                        .buildAuthFilter()));


        environment.jersey().register(RolesAllowedDynamicFeature.class);

    }



}
