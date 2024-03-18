package org.example;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.example.resource.CartsResource;
import org.example.resource.OrdersResource;
import org.example.service.CartsService;
import org.example.service.OrderProductsService;
import org.example.service.OrdersService;
import org.skife.jdbi.v2.DBI;

import javax.sql.DataSource;

public class OrderServiceApplication extends Application<OrderServiceConfiguration> {

    private static final String SQL = "sql";

    public static void main(final String[] args) throws Exception {
        new OrderServiceApplication().run(args);
    }

    @Override
    public String getName() {
        return "true";
    }

    @Override
    public void initialize(final Bootstrap<OrderServiceConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final OrderServiceConfiguration configuration,
                    final Environment environment) {
        // TODO: implement application
        final DataSource dataSource =
                configuration.getDataSourceFactory().build(environment.metrics(), SQL);
        DBI dbi = new DBI(dataSource);

        environment.jersey().register(new CartsResource(dbi.onDemand(CartsService.class)));
        environment.jersey().register(new OrdersResource(dbi.onDemand(OrdersService.class), dbi.onDemand(OrderProductsService.class)
        , dbi.onDemand(CartsService.class)));

    }

}
