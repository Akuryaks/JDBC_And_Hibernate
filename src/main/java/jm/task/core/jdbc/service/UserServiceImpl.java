package jm.task.core.jdbc.service;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;

import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {
    public void createUsersTable() {
        try (Session session = Util.OpenConnectionHibernate()){
            session.beginTransaction();
            session.createNativeQuery("""
                create table if not exists users
                (
                    id serial PRIMARY KEY,
                    name text not null,
                    last_name text not null,
                    age smallint not null
                )
                """).executeUpdate();
            session.getTransaction().commit();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        try (Session session = Util.OpenConnectionHibernate()){
            session.beginTransaction();
            session.createNativeQuery("drop table if exists users").executeUpdate();
            session.getTransaction().commit();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        User user = new User();
        user.setName(name);
        user.setLastName(lastName);
        user.setAge(age);

        try (Session session = Util.OpenConnectionHibernate()){
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        try (Session session = Util.OpenConnectionHibernate()){
            session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                session.delete(user);
            }
            session.getTransaction().commit();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> users;
        try (Session session = Util.OpenConnectionHibernate()){
            session.beginTransaction();
            users = session.createQuery("from User", User.class).list();
            session.getTransaction().commit();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    public void cleanUsersTable() {
        try (Session session = Util.OpenConnectionHibernate()){
            session.beginTransaction();
            session.createQuery("delete from User").executeUpdate();
            session.getTransaction().commit();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
