package dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;

import org.hibernate.Hibernate;
import org.hibernate.IdentifierLoadAccess;
import org.hibernate.NonUniqueResultException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.proxy.HibernateProxy;

import Database.HibernateHelper;
import org.hibernate.query.Query;

public class BasicDao<T> {

    private final Class<T> c;


    public BasicDao(Class<T> c) {
        this.c = c;
    }

    public T create(T entity) {

        Session session = HibernateHelper.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.save(entity);
        transaction.commit();

        return entity;
    }

    public T edit(T entity) {

        Session session = HibernateHelper.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.merge(entity);
        transaction.commit();

        return entity;
    }

    public boolean delete(T entity) {
        Session session = HibernateHelper.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.delete(entity);
            transaction.commit();
            return true;
        } catch (Exception ex) {
            transaction.rollback();
            return false;
        }

    }

    public T findById(long id) {
        Session session = HibernateHelper.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        IdentifierLoadAccess<T> ila = session.byId(c);
        T entity = ila != null ? ila.getReference(id) : null;
        if (entity != null) {
            entity = initializeAndUnproxy(entity);
        }
        transaction.commit();

        return entity;
    }

    @SuppressWarnings("unchecked")
    public List<T> getAll() {
        Session session = HibernateHelper.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Query<T> q = session.createQuery("FROM " + c.getSimpleName());

        List<T> list = q.getResultList();
        transaction.commit();

        return list != null ? list : new ArrayList<>();
    }

    @SuppressWarnings("unchecked")
    public List<T> getByField(String fieldName, String fieldValue) {

        Session session = HibernateHelper.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Query<T> q = session.createQuery("SELECT o FROM " + c.getSimpleName() + " o where o." + fieldName + " = :fieldValue");
        q.setParameter("fieldValue", fieldValue);

        List<T> list = q.list();
        transaction.commit();

        return list != null ? list : new ArrayList<>();

    }

    @SuppressWarnings("unchecked")
    public T getUniqueByField(String fieldName, String fieldValue) {

        Session session = HibernateHelper.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Query<T> q = session.createQuery("SELECT o FROM " + c.getSimpleName() + " o where o." + fieldName + " = :fieldValue");
        q.setParameter("fieldValue", fieldValue);

        T entity;
        try {
            entity = q.uniqueResult();
        } catch (NonUniqueResultException e) {
            entity = null;
        }
        transaction.commit();

        return entity;

    }

    @SuppressWarnings("unchecked")
    public List<T> searchByField(String fieldName, String fieldValue) {

        Session session = HibernateHelper.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        fieldValue = fieldValue.replaceAll("\\*", "%");
        Query<T> q = session.createQuery("SELECT o FROM " + c.getSimpleName() + " o where o." + fieldName + " LIKE :fieldValue");
        q.setParameter("fieldValue", fieldValue);

        List<T> list = q.list();
        transaction.commit();

        return list != null ? list : new ArrayList<>();

    }


    @SuppressWarnings({"unchecked", "rawtypes"})
    public List<T> searchByProperties(Properties properties) {

        if (properties == null) {
            return new ArrayList<>();
        }

        Session session = HibernateHelper.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();

        StringBuilder queryString = new StringBuilder("SELECT o FROM " + c.getSimpleName() + " o");

        boolean firstProperty = true;
        for (Entry<Object, Object> property : properties.entrySet()) {
            if (!firstProperty) {
                queryString.append(" AND ");
            } else {
                queryString.append(" where ");
                firstProperty = false;
            }
            if (property.getValue() instanceof List) {
                queryString.append(" o.").append(property.getKey()).append(" in (:").append(property.getKey()).append(")");
            } else {
                queryString.append(" o.").append(property.getKey()).append(" LIKE :").append(property.getKey());
            }
        }


        Query q = session.createQuery(queryString.toString());
        for (Entry<Object, Object> property : properties.entrySet()) {
            Object value = property.getValue();
            if (value instanceof String) {
                value = ((String) property.getValue()).replaceAll("\\*", "%");
                q.setParameter((String) property.getKey(), value);
            }
            if (value instanceof Collection) {
                q.setParameterList((String) property.getKey(), (Collection) value);
            }
        }

        List<T> list = q.list();
        transaction.commit();

        return list != null ? list : new ArrayList<>();

    }

    @SuppressWarnings("unchecked")
    public static <T> T initializeAndUnproxy(T entity) {
        if (entity == null) {
            throw new
                    NullPointerException("Entity passed for initialization is null");
        }

        Hibernate.initialize(entity);
        if (entity instanceof HibernateProxy) {
            entity = (T) ((HibernateProxy) entity).getHibernateLazyInitializer()
                    .getImplementation();
        }
        return entity;
    }

}
