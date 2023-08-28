package edu.step.db.hibernate;

import edu.step.db.CoreEntity;
import edu.step.db.Department;
import edu.step.db.Employee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public abstract class AbstractDao<E extends CoreEntity> {

    public abstract Class<E> getEntityClass();
    protected Session getSession() {
        SessionFactory sessionFactory = HibernateUtil.sessionFactory;
        Session session = sessionFactory.openSession();
        return session;
    }

    public void create(E entity) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        session.save(entity);
        transaction.commit();
        session.close();
    }

    public List<E> findAll() {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        Query<E> query = (Query<E>) session.createQuery("FROM "+getEntityClass().getSimpleName());
        List<E> list = query.list();
        transaction.commit();
        session.close();
        return list;
    }
    public void update(E entity) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        E existingEntity = session.get(getEntityClass(), entity.getId());
        E updatedEntity = updateEntity(entity,existingEntity);
        session.update(updatedEntity);
        transaction.commit();
        session.close();
    }
    public abstract E updateEntity(E source, E target);
    public void delete(int id) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        E entity = session.get(getEntityClass(), id);
        session.delete(entity);
        transaction.commit();
        session.close();
    }


}
