package dao;

import Database.Entities.Artikel;
import Database.Entities.Reservierung;
import Database.HibernateHelper;
import enums.ReservierungsEnum;
import org.hibernate.Hibernate;
import org.hibernate.IdentifierLoadAccess;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class ReservierungDao extends BasicDao<Reservierung> {

    public ReservierungDao() {
        super(Reservierung.class);
    }

    public Reservierung findReservierungWithAllById(long id) {
        Session session = HibernateHelper.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        IdentifierLoadAccess<Reservierung> ila = session.byId(Reservierung.class);
        Reservierung reservierung = ila != null ? ila.getReference(id) : null;
        if (reservierung != null) {
            reservierung = initializeAndUnproxy(reservierung);
            Hibernate.initialize(reservierung.getArtikel());
            Hibernate.initialize(reservierung.getCustomer());
            Hibernate.initialize(reservierung.getDealer());
        }
        transaction.commit();
        return reservierung;
    }

    @SuppressWarnings("unchecked")
    public List<Reservierung> findReservierungWithUsersByEnum(ReservierungsEnum status) {
        Session session = HibernateHelper.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Query<Reservierung> q = session.createQuery("FROM " + Reservierung.class.getSimpleName() + " o where o. status  = :status");
        q.setParameter("status", status);
        List<Reservierung> list = q.list();

        if (list != null) {
            for (Reservierung r : list) {
                Hibernate.initialize(r.getCustomer());
                Hibernate.initialize(r.getDealer());
            }
        }
        transaction.commit();
        return list;
    }

    public Reservierung findReservierungWithArtikelById(long id) {
        Session session = HibernateHelper.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        IdentifierLoadAccess<Reservierung> ila = session.byId(Reservierung.class);
        Reservierung reservierung = ila != null ? ila.getReference(id) : null;
        if (reservierung != null) {
            reservierung = initializeAndUnproxy(reservierung);
            Hibernate.initialize(reservierung.getArtikel());
        }

        transaction.commit();

        return reservierung;
    }

    public Reservierung findReservierungWithDealerById(long id) {
        Session session = HibernateHelper.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        IdentifierLoadAccess<Reservierung> ila = session.byId(Reservierung.class);
        Reservierung reservierung = ila != null ? ila.getReference(id) : null;
        if (reservierung != null) {
            reservierung = initializeAndUnproxy(reservierung);
            Hibernate.initialize(reservierung.getDealer());
        }

        transaction.commit();
        return reservierung;
    }

    public Reservierung findReservierungWithCustomerById(long id) {
        Session session = HibernateHelper.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        IdentifierLoadAccess<Reservierung> ila = session.byId(Reservierung.class);
        Reservierung reservierung = ila != null ? ila.getReference(id) : null;
        if (reservierung != null) {
            reservierung = initializeAndUnproxy(reservierung);
            Hibernate.initialize(reservierung.getCustomer());
        }

        transaction.commit();
        return reservierung;
    }

    @SuppressWarnings("unchecked")
    public List<Reservierung> getReservierungOfUser(String kennung) {
        Session session = HibernateHelper.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Query<Reservierung> q = session.getNamedQuery("getReservierungOfUser");
        q.setParameter("kennung", kennung);

        List<Reservierung> list = q.getResultList();
        transaction.commit();

        return (list != null ? list : new ArrayList<>());

    }

    public Reservierung create(Reservierung reservierung, List<Artikel> artikel) {

        Session session = HibernateHelper.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.save(reservierung);
        reservierung.setArtikel(artikel);

        for (Artikel a : artikel) {
            a.getReservierungen().add(reservierung);
            session.merge(a);
        }

        transaction.commit();
        return reservierung;
    }

}
