package dao;

import Database.Entities.Berechtigung;
import Database.HibernateHelper;
import org.hibernate.Hibernate;
import org.hibernate.IdentifierLoadAccess;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class BerechtigungDao extends BasicDao<Berechtigung> {

    public BerechtigungDao() {
        super(Berechtigung.class);
    }

    public Berechtigung findBerechtigungWithUserGroupsById(long id) {
        Session session = HibernateHelper.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        IdentifierLoadAccess<Berechtigung> ila = session.byId(Berechtigung.class);
        Berechtigung berechtigung = ila != null ? (Berechtigung) ila.getReference(id) : null;
        if (berechtigung != null) {
            berechtigung = initializeAndUnproxy(berechtigung);
            Hibernate.initialize(berechtigung.getMemberOfGroups());
        }

        transaction.commit();
        return berechtigung;
    }
}
