package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;


import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl extends Util implements UserDao {
    private final SessionFactory sessionFactory = getSessionFactory();

    public UserDaoHibernateImpl() {
    }


    @Override
    public void createUsersTable() {
        Session session = sessionFactory.openSession();
            session.beginTransaction();
            session.createSQLQuery("create table if not exists Users (" +
                    "id bigint auto_increment," +
                    "name varchar(30) not null," +
                    "lastName varchar(30) not null," +
                    "age tinyint null," +
                    "constraint Users_pk" +
                    " primary key (id))").executeUpdate();
            session.getTransaction().commit();
    }

    @Override
    public void dropUsersTable() {
        Session session = sessionFactory.openSession();
            session.beginTransaction();
            session.createSQLQuery("drop table if exists Users").executeUpdate();
            session.getTransaction().commit();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = sessionFactory.openSession();
            session.beginTransaction();
            session.save(new User(name, lastName, age));
            session.getTransaction().commit();
            System.out.println("User с именем – "
                    + name
                    + " добавлен в базу данных");
    }

    @Override
    public void removeUserById(long id) {
        Session session = sessionFactory.openSession();
            session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                session.delete(user);
                System.out.println("User с ID – "
                        + id
                        + " удалён");
                session.getTransaction().commit();
            }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> result = new ArrayList<>();
        Session session = sessionFactory.openSession();
            session.beginTransaction();
            result = session.createQuery("from User").list();
            session.getTransaction().commit();
        return result;
    }

    @Override
    public void cleanUsersTable() {
        Session session = sessionFactory.openSession();
            session.beginTransaction();
            session.createQuery("delete from User").executeUpdate();
            session.getTransaction().commit();
    }
}