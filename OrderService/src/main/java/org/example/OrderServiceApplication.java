package org.example;


import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;
import io.dropwizard.jdbi3.JdbiFactory;
import org.checkerframework.checker.units.qual.C;
import org.example.dao.CartsDao;
import org.example.dao.OrderProductsDao;
import org.example.dao.OrdersDao;
import org.example.mapper.CartsMapper;
import org.example.mapper.OrdersMapper;
import org.example.resource.CartsResource;
import org.example.resource.OrdersResource;
import org.example.service.CartsService;
import org.example.service.OrderProductsService;
import org.example.service.OrdersService;
import org.jdbi.v3.core.Jdbi;

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
        final JdbiFactory factory = new JdbiFactory();
        final Jdbi dbi = factory.build(environment, configuration.getDataSourceFactory(), "sql");
        dbi.registerRowMapper(new CartsMapper());
        dbi.registerRowMapper(new OrdersMapper());

        environment.jersey().register(new CartsResource(new CartsService(dbi.onDemand(CartsDao.class))));
        environment.jersey().register(new OrdersResource(new OrdersService(dbi.onDemand(OrderProductsDao.class), dbi
                .onDemand(OrdersDao.class)), new OrderProductsService(dbi.onDemand(OrderProductsDao.class)), new CartsService(
                        dbi.onDemand(CartsDao.class)
        )));


    }

}
