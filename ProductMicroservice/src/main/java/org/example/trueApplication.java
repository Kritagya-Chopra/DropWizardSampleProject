package org.example;

import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Environment;

import org.example.db.ProductsDao;
import org.example.mapper.ProductsMapper;
import org.example.resources.ProductsResource;
import org.example.service.ProductsService;
import io.dropwizard.jdbi3.JdbiFactory;
import org.jdbi.v3.core.Jdbi;


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

        final JdbiFactory factory = new JdbiFactory();
        final Jdbi jdbi = factory.build(environment, configuration.getDataSourceFactory(), "sql");
        jdbi.registerRowMapper(new ProductsMapper());


        environment.jersey().register(new ProductsResource(new ProductsService(jdbi.onDemand(ProductsDao.class)) {
        }));
    }

}
