package dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.IdentifierLoadAccess;
import org.hibernate.Session;
import org.hibernate.Transaction;

import enums.SanktionenEnum;
import Database.HibernateHelper;
import Database.Entities.SanktionsListe;
import org.hibernate.query.Query;

public class SanktionsListeDao extends BasicDao<SanktionsListe> {

    public SanktionsListeDao() {
        super(SanktionsListe.class);
    }

    public SanktionsListe findSanktionsListeWithUserIdById(long id) {
        Session session = HibernateHelper.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        IdentifierLoadAccess<SanktionsListe> ila = session.byId(SanktionsListe.class);
        SanktionsListe sanktionsListe = ila != null ? ila.getReference(id) : null;
        if (sanktionsListe != null) {
            sanktionsListe = initializeAndUnproxy(sanktionsListe);
            Hibernate.initialize(sanktionsListe.getUserID());
        }

        transaction.commit();
        return sanktionsListe;
    }

    @SuppressWarnings("unchecked")
    public List<SanktionsListe> getAllGraueListe() {
        Session session = HibernateHelper.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Query<SanktionsListe> q = session.createQuery("SELECT o FROM " + SanktionsListe.class.getSimpleName() + " o where o. status  = :status");
        q.setParameter("status", SanktionenEnum.Graue);

        List<SanktionsListe> list = q.list();
        if (list != null) {
            for (SanktionsListe s : list) {
                Hibernate.initialize(s.getUserID());
            }
        }
        transaction.commit();

        return (list != null ? list : new ArrayList<>());
    }

    @SuppressWarnings("unchecked")
    public List<SanktionsListe> getAllSchwarzeListe() {
        Session session = HibernateHelper.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Query<SanktionsListe> q = session.createQuery("SELECT o FROM " + SanktionsListe.class.getSimpleName() + " o where o. status  = :status");
        q.setParameter("status", SanktionenEnum.Schwarze);

        List<SanktionsListe> list = q.list();
        if (list != null) {
            for (SanktionsListe s : list) {
                Hibernate.initialize(s.getUserID());
            }
        }
        transaction.commit();
        return (list != null ? list : new ArrayList<>());
    }

    @SuppressWarnings("unchecked")
    public List<SanktionsListe> getAllWithUsers() {
        Session session = HibernateHelper.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Query<SanktionsListe> q = session.createQuery("FROM " + SanktionsListe.class.getSimpleName());
        List<SanktionsListe> list = q.list();
        if (list != null) {
            for (SanktionsListe s : list) {
                Hibernate.initialize(s.getUserID());
            }
        }
        transaction.commit();
        return (list != null ? list : new ArrayList<>());
    }
}
