package org.example;

import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Environment;

import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.example.db.ProductsDao;
import org.example.mapper.ProductMapper;
import org.example.resources.ProductsResource;
import org.example.service.ProductsService;
import io.dropwizard.jdbi3.JdbiFactory;
import org.jdbi.v3.core.Jdbi;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.EnumSet;

public class trueApplication extends Application<trueConfiguration> {

    public static void main(final String[] args) throws Exception {
        new trueApplication().run(args);
    }

    @Override
    public String getName() {
        return "true";
    }

    @Override
    public void run(final trueConfiguration configuration,
                    final Environment environment) {
        final FilterRegistration.Dynamic cors =
                environment.servlets().addFilter("CORS", CrossOriginFilter.class);

        // Configure CORS parameters
        cors.setInitParameter("allowedOrigins", "*");
        cors.setInitParameter("allowedHeaders", "X-Requested-With,Content-Type,Accept,Origin");
        cors.setInitParameter("allowedMethods", "OPTIONS,GET,PUT,POST,DELETE,HEAD");

        // Add URL mapping
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");

        final JdbiFactory factory = new JdbiFactory();
        final Jdbi jdbi = factory.build(environment, configuration.getDataSourceFactory(), "sql");
        jdbi.registerRowMapper(new ProductMapper());

        environment.jersey().register(new ProductsResource(new ProductsService(jdbi.onDemand(ProductsDao.class))));
    }
}
