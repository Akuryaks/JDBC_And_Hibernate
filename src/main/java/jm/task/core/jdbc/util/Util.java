package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.cfg.Configuration;

import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class Util {
    private static final Connection connectionJDBC;
    private static SessionFactory sessionFactory;
    private static final String URL_KEY = "db.url";
    private static final String USER_KEY = "db.username";
    private static final String PASSWORD_KEY = "db.password";

    static {
        try {
            connectionJDBC = OpenConnectionJDBC();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Session OpenConnectionHibernate() throws MalformedURLException {
        if (sessionFactory != null){
            if (!sessionFactory.isClosed()){
                return sessionFactory.openSession();
            }
        }

        Configuration configuration = new Configuration();
        configuration.setProperty("hibernate.connection.url", PropertiesUtil.get(URL_KEY));
        configuration.setProperty("hibernate.connection.username", PropertiesUtil.get(USER_KEY));
        configuration.setProperty("hibernate.connection.password", PropertiesUtil.get(PASSWORD_KEY));
        configuration.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        configuration.setProperty("hibernate.hbm2ddl.auto", "create-drop");
        configuration.setProperty("hibernate.format_sql", "true");
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());
        configuration.addAnnotatedClass(User.class);
        sessionFactory = configuration.buildSessionFactory();

        return sessionFactory.openSession();
    }

    public static void CloseConnectionHibernate() throws SQLException {
        sessionFactory.close();
    }

    public static Connection OpenConnectionJDBC() throws SQLException {
        if (connectionJDBC != null){
            if (!connectionJDBC.isClosed()){
                return connectionJDBC;
            }
        }

        try {
            return DriverManager.getConnection(PropertiesUtil.get(URL_KEY), PropertiesUtil.get(USER_KEY), PropertiesUtil.get(PASSWORD_KEY));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void CloseConnectionJDBC() throws SQLException {
        connectionJDBC.close();
    }
}
