package service.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import models.User;
import service.utils.HibernateSessionFactory;

public class UserDaoPostgres implements UserDAO {

    @Override
    public User getByToken(String token) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Query<User> query = session.createQuery("from User where token = :token", User.class);
        query.setParameter("token", token);
        User user = query.getSingleResultOrNull();
        session.close();
        return user;
    }

    @Override
    public User getById(long id) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        User user = session.get(User.class, id);
        session.close();
        return user;
    }

    @Override
    public void save(User user) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.persist(user);
        tx.commit();
        session.close();
    }

    @Override
    public void update(User user) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.merge(user);
        tx1.commit();
        session.close();
    }

    @Override
    public void delete(User user) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.remove(user);
        tx1.commit();
        session.close();
    }

    @Override
    public User getByLoginAndPassword(String login, String password) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Query<User> query = session.createQuery("from User where login = :login AND password = :password", User.class);

        query.setParameter("login", login);
        query.setParameter("password", password);
        User user = query.getSingleResultOrNull();
        session.close();
        return user;
    }

    @Override
    public boolean containsUser(String login){
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Query<User> query = session.createQuery("from User where login = :login", User.class);
        query.setParameter("login", login);
        User user = query.setMaxResults(1).getSingleResultOrNull();
        session.close();
        return user != null;
    }
    
}
