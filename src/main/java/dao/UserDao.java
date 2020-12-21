package dao;

import org.hibernate.Hibernate;
import org.hibernate.IdentifierLoadAccess;
import org.hibernate.NonUniqueResultException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import Database.HibernateHelper;
import Database.Entities.User;
import org.hibernate.query.Query;

public class UserDao extends BasicDao<User> {

    public UserDao() {
        super(User.class);
    }

    public User findUserWithReservierungById(long id) {
        Session session = HibernateHelper.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        IdentifierLoadAccess<User> ila = session.byId(User.class);
        User user = ila != null ? ila.getReference(id) : null;
        if (user != null) {
            user = initializeAndUnproxy(user);
            Hibernate.initialize(user.getReservierungen());
        }

        transaction.commit();
        return user;
    }

    public User findUserWithSanktionById(long id) {
        Session session = HibernateHelper.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        IdentifierLoadAccess<User> ila = session.byId(User.class);
        User user = ila != null ? ila.getReference(id) : null;
        if (user != null) {
            user = initializeAndUnproxy(user);
            Hibernate.initialize(user.getSanktionsListe());
        }

        transaction.commit();
        return user;
    }

    public User findUserWithDealsById(long id) {
        Session session = HibernateHelper.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        IdentifierLoadAccess<User> ila = session.byId(User.class);
        User user = ila != null ? ila.getReference(id) : null;
        if (user != null) {
            user = initializeAndUnproxy(user);
            Hibernate.initialize(user.getDeals());
        }

        transaction.commit();
        return user;
    }

    public User findUserWithUserGroupsById(long id) {
        Session session = HibernateHelper.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        IdentifierLoadAccess<User> ila = session.byId(User.class);
        User user = ila != null ? ila.getReference(id) : null;
        if (user != null) {
            user = initializeAndUnproxy(user);
            Hibernate.initialize(user.getMemberOfGroups());
        }

        transaction.commit();
        return user;
    }

    @SuppressWarnings("unchecked")
    public User findUserByUniqueFieldWithUserGroups(String fieldName, String fieldValue) {
        Session session = HibernateHelper.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Query<User> q = session.createQuery("SELECT o FROM " + User.class.getSimpleName() + " o where o." + fieldName + " = :fieldValue");
        q.setParameter("fieldValue", fieldValue);

        User user;
        try {
            user = q.uniqueResult();
            Hibernate.initialize(user.getMemberOfGroups());
        } catch (NonUniqueResultException e) {
            user = null;
        }
        transaction.commit();

        return user;

    }
}
