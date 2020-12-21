package dao;

import org.hibernate.Hibernate;
import org.hibernate.IdentifierLoadAccess;
import org.hibernate.Session;
import org.hibernate.Transaction;

import Database.HibernateHelper;
import Database.Entities.Berechtigung;
import Database.Entities.Bilder;

public class BilderDao extends BasicDao<Bilder> {

    public BilderDao() {
        super(Bilder.class);
    }

    public Bilder findBilderWithArtikelById(long id) {
        Session session = HibernateHelper.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        IdentifierLoadAccess<Bilder> ila = session.byId(Bilder.class);
        Bilder bilder = ila != null ? (Bilder) ila.getReference(id) : null;
        if (bilder != null) {
            bilder = initializeAndUnproxy(bilder);
            Hibernate.initialize(bilder.getArtikel());
        }

        transaction.commit();
        return bilder;
    }
}
