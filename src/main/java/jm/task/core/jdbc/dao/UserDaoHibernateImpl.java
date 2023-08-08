package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try (Session session = Util.OpenConnectionHibernate()){
            session.beginTransaction();
            session.createSQLQuery("""
                create table if not exists users
                (
                    id serial PRIMARY KEY,
                    name text not null,
                    last_name text not null,
                    age smallint not null
                )
                """);
            session.getTransaction().commit();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.OpenConnectionHibernate()){
            session.beginTransaction();
            session.createSQLQuery("drop table if exists users");
            session.getTransaction().commit();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
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

    @Override
    public void removeUserById(long id) {
        User user = new User();
        user.setId(id);
        try (Session session = Util.OpenConnectionHibernate()){
            session.beginTransaction();
            session.delete(user);
            session.getTransaction().commit();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Session session = Util.OpenConnectionHibernate()){
            session.beginTransaction();
            users = (List<User>)session.createQuery("from users").list();
            session.getTransaction().commit();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.OpenConnectionHibernate()){
            session.beginTransaction();
            session.createSQLQuery("delete from users");
            session.getTransaction().commit();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
