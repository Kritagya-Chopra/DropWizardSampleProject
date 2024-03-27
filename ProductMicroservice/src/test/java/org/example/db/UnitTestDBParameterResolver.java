package org.example.db;/*
 * Copyright (c) 2019 Trux, Inc. All rights reserved.
 */


import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.jodatime2.JodaTimePlugin;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import java.util.HashMap;
import java.util.Map;

public class UnitTestDBParameterResolver implements ParameterResolver {

    /**
     * System properties (allows us to load stuff from the maven profiles)
     */

    private static String DB_ROOT_URL = "";
    private static String DB_USER = "";
    private static String DB_PASSWORD = "";

    /**
     * JDBI instance for Unit Test DB
     */

    public static Jdbi jdbi;

    /**
     * Store of objects used as parameters
     */

    private static Map<String, Object> parameterStore = new HashMap<>();


    /*
     * Load and vet the test configuration. This loaded configuration is used when the application is starting up.
     */

    static {

        /*
         * Configure the Unit Test DB
         */

        /*
         * System properties (allows us to load stuff from the maven profiles)
         */

        String DB_ROOT_URL = "jdbc:mysql://localhost:3306";
        String DB_USER = "root";
        String DB_PASSWORD = "kcwaa@12";

        /*
         * JDBI instance for Unit Test DB
         */

        jdbi = Jdbi.create(DB_ROOT_URL + "/Testing?useSSL=false", DB_USER, DB_PASSWORD).installPlugin(new SqlObjectPlugin()).installPlugin(new JodaTimePlugin());
        jdbi.registerRowMapper(new ProductMapper());
        /*
         * Populate the parameter store with the appropriate injectable objects
         */

        ProductsDao productsDao = jdbi.onDemand(ProductsDao.class);
        parameterStore.put(ProductsDao.class.getSimpleName(), productsDao);

    }

    @Override
    public boolean supportsParameter(ParameterContext paramContext, ExtensionContext extContext) throws ParameterResolutionException {
        return parameterStore.containsKey(paramContext.getParameter().getType().getSimpleName());
    }

    @Override
    public Object resolveParameter(ParameterContext paramContext, ExtensionContext extContext) throws ParameterResolutionException {
        return parameterStore.get(paramContext.getParameter().getType().getSimpleName());
    }
}
