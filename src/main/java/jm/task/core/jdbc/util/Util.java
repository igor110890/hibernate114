package jm.task.core.jdbc.util;


import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    /**
     * JDBC Driver, database url
     */
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/mydb";

    /**
     * User and Password
     */
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    /**
     * JDBC connection
     */
    public Connection getConnection() {
        Connection connection = null;
        try {
            // Registering JDBC Driver
            Class.forName(JDBC_DRIVER);
            // Creating database connection
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Соединение не активно");
        }
        return connection;
    }

    /**
     * Hibernate connection
     */
    public SessionFactory getSessionFactory() {
        SessionFactory sessionFactory = null;

        Properties settings = new Properties();
        settings.put(Environment.DRIVER, JDBC_DRIVER);
        settings.put(Environment.URL, URL);
        settings.put(Environment.USER, USER);
        settings.put(Environment.PASS, PASSWORD);
        settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
        settings.put(Environment.SHOW_SQL, "true");
        settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
        settings.put(Environment.HBM2DDL_AUTO, "create-drop");

        Configuration configuration = new Configuration();
        configuration.setProperties(settings);
        configuration.addAnnotatedClass(jm.task.core.jdbc.model.User.class);

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .build();

        try {
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("SessionFactory - не создан");
        }

        return sessionFactory;
    }
}