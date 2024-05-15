package service.dao;

import models.UPRecord;
import models.User;
import service.utils.HibernateSessionFactory;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class UPRecordDaoPostgres implements UPRecordDAO{

    @Override
    public UPRecord getById(long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getById'");
    }

    @Override
    public List<UPRecord> getByUser(User user) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Query<UPRecord> query = session.createQuery("from UPRecord where user_id = :user_id", UPRecord.class);

        query.setParameter("user_id", user.getId());
        
        List<UPRecord> records = query.getResultList();
        session.close();
        return records;
    }

    @Override
    public void update(UPRecord record) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.merge(record);
        tx1.commit();
        session.close();
    }

    @Override
    public void save(UPRecord record) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.persist(record);
        tx.commit();
        session.close();
    }

    @Override
    public void delete(UPRecord record) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.remove(record);
        tx1.commit();
        session.close();
    }
    
}
