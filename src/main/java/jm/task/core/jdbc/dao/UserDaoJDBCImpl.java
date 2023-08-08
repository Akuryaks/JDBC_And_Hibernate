package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try {
            Connection connection = Util.OpenConnectionJDBC();
            Statement statement = connection.createStatement();

            statement.execute("""
                create table if not exists users
                (
                    id serial PRIMARY KEY,
                    name text not null,
                    last_name text not null,
                    age smallint not null
                )
                """);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        try {
            Connection connection = Util.OpenConnectionJDBC();
            Statement statement = connection.createStatement();

            statement.executeUpdate("drop table if exists users");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            Connection connection = Util.OpenConnectionJDBC();
            Statement statement = connection.createStatement();

            statement.executeUpdate("insert into users (name, last_name, age) values ('" + name + "', '" + lastName + "', " + age + ")");
            System.out.println("User с именем " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        try {
            Connection connection = Util.OpenConnectionJDBC();
            Statement statement = connection.createStatement();

            statement.executeUpdate("delete from users where id = " + id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        ArrayList<User> users = new ArrayList<>();
        try {
            Connection connection = Util.OpenConnectionJDBC();
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("select * from users");
            while (resultSet.next()){
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setAge(resultSet.getByte("age"));
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void cleanUsersTable() {
        try {
            Connection connection = Util.OpenConnectionJDBC();
            Statement statement = connection.createStatement();

            statement.executeUpdate("delete from users");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
