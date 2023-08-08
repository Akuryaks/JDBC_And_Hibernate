package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        UserDaoJDBCImpl userDaoJDBC = new UserDaoJDBCImpl();
        userDaoJDBC.createUsersTable();

        userDaoJDBC.saveUser("У", "Меня", (byte) 20);
        userDaoJDBC.saveUser("Фантазия", "Так себе", (byte) 21);
        userDaoJDBC.saveUser("Поэтому", "Будет", (byte) 22);
        userDaoJDBC.saveUser("Так", "Вот", (byte) 23);
        userDaoJDBC.removeUserById(1L);

        for (User user : userDaoJDBC.getAllUsers()){
            System.out.println(user.toString());
        }

        userDaoJDBC.cleanUsersTable();

        userDaoJDBC.dropUsersTable();

        try {
            Util.CloseConnectionJDBC();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}