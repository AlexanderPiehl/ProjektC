package dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.IdentifierLoadAccess;
import org.hibernate.Session;
import org.hibernate.Transaction;

import Database.HibernateHelper;
import Database.Entities.Artikel;
import org.hibernate.query.Query;

public class ArtikelDao extends BasicDao<Artikel> {


    public ArtikelDao() {
        super(Artikel.class);
    }

    public Artikel findArtikelWithReservierungById(long id) {
        Session session = HibernateHelper.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        IdentifierLoadAccess<Artikel> ila = session.byId(Artikel.class);
        Artikel artikel = ila != null ? ila.getReference(id) : null;
        if (artikel != null) {
            artikel = initializeAndUnproxy(artikel);
            Hibernate.initialize(artikel.getReservierungen());
        }

        transaction.commit();

        return artikel;
    }

    public Artikel findArtikelWithBilderById(long id) {
        Session session = HibernateHelper.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        IdentifierLoadAccess<Artikel> ila = session.byId(Artikel.class);
        Artikel artikel = ila != null ? ila.getReference(id) : null;
        if (artikel != null) {
            artikel = initializeAndUnproxy(artikel);
            Hibernate.initialize(artikel.getBilder());
        }

        transaction.commit();

        return artikel;
    }


    public List<Artikel> getByHersteller(String hersteller) {

        BasicDao<Artikel> artikelDao = new BasicDao<>(Artikel.class);
        return artikelDao.getByField("hersteller", hersteller);
    }

    public List<Artikel> getByTyp(String typ) {

        BasicDao<Artikel> artikelDao = new BasicDao<>(Artikel.class);
        return artikelDao.getByField("typ", typ);
    }

    public List<Artikel> getByArt(String art) {

        BasicDao<Artikel> artikelDao = new BasicDao<>(Artikel.class);
        return artikelDao.getByField("art", art);
    }

    public List<Artikel> getByKategorie(String kategorie) {

        BasicDao<Artikel> artikelDao = new BasicDao<>(Artikel.class);
        return artikelDao.getByField("kategorie", kategorie);
    }

    public List<Artikel> getByVerzeichnisbaum(String verzeichnisbaum) {

        BasicDao<Artikel> artikelDao = new BasicDao<>(Artikel.class);
        return artikelDao.getByField("verzeichnisbaum", verzeichnisbaum);
    }

    public List<Artikel> getByKaufdatum(String kaufdatum) {

        BasicDao<Artikel> artikelDao = new BasicDao<>(Artikel.class);
        return artikelDao.getByField("kaufdatum", kaufdatum);
    }

    public List<Artikel> getByPreis(String preis) {

        BasicDao<Artikel> artikelDao = new BasicDao<>(Artikel.class);
        return artikelDao.getByField("preis", preis);
    }

    public List<Artikel> getByLieferant(String lieferant) {

        BasicDao<Artikel> artikelDao = new BasicDao<>(Artikel.class);
        return artikelDao.getByField("lieferant", lieferant);
    }

    public List<Artikel> getByVolNr(String volNr) {

        BasicDao<Artikel> artikelDao = new BasicDao<>(Artikel.class);
        return artikelDao.getByField("volNr", volNr);
    }

    public List<Artikel> getByBemerkungen(String bemerkungen) {

        BasicDao<Artikel> artikelDao = new BasicDao<>(Artikel.class);
        return artikelDao.getByField("bemerkungen", bemerkungen);
    }

    public List<Artikel> getByZustand(String zustand) {

        BasicDao<Artikel> artikelDao = new BasicDao<>(Artikel.class);
        return artikelDao.getByField("zustand", zustand);
    }

    public List<Artikel> getByFoto(String foto) {

        BasicDao<Artikel> artikelDao = new BasicDao<>(Artikel.class);
        return artikelDao.getByField("foto", foto);
    }

    public List<Artikel> getByMenge(String menge) {

        BasicDao<Artikel> artikelDao = new BasicDao<>(Artikel.class);
        return artikelDao.getByField("menge", menge);
    }

    public List<Artikel> getByElektrischeLeistung(String elektrischeLeistung) {

        BasicDao<Artikel> artikelDao = new BasicDao<>(Artikel.class);
        return artikelDao.getByField("elektrischeLeistung", elektrischeLeistung);
    }

    public List<Artikel> getByPackmass(String packmass) {

        BasicDao<Artikel> artikelDao = new BasicDao<>(Artikel.class);
        return artikelDao.getByField("packmass", packmass);
    }

    public List<Artikel> getByGewicht(String gewicht) {

        BasicDao<Artikel> artikelDao = new BasicDao<>(Artikel.class);
        return artikelDao.getByField("gewicht", gewicht);
    }

    public List<Artikel> getByLeuchtmitteltyp(String leuchtmitteltyp) {

        BasicDao<Artikel> artikelDao = new BasicDao<>(Artikel.class);
        return artikelDao.getByField("leuchtmitteltyp", leuchtmitteltyp);
    }

    public List<Artikel> getByAkkutyp(String akkutyp) {

        BasicDao<Artikel> artikelDao = new BasicDao<>(Artikel.class);
        return artikelDao.getByField("akkutyp", akkutyp);
    }

    public List<Artikel> getByBatterientyp(String batterientyp) {

        BasicDao<Artikel> artikelDao = new BasicDao<>(Artikel.class);
        return artikelDao.getByField("batterientyp", batterientyp);
    }

    public List<Artikel> getByBatterienAnzahl(String batterienAnzahl) {

        BasicDao<Artikel> artikelDao = new BasicDao<>(Artikel.class);
        return artikelDao.getByField("batterienAnzahl", batterienAnzahl);
    }

    public List<Artikel> getBySpeichermedium(String speichermedium) {

        BasicDao<Artikel> artikelDao = new BasicDao<>(Artikel.class);
        return artikelDao.getByField("speichermedium", speichermedium);
    }

    public List<Artikel> getByStatus(String status) {

        BasicDao<Artikel> artikelDao = new BasicDao<>(Artikel.class);
        return artikelDao.getByField("status", status);
    }

    public List<Artikel> getBySeriennummer(String seriennummer) {

        BasicDao<Artikel> artikelDao = new BasicDao<>(Artikel.class);
        return artikelDao.getByField("seriennummer", seriennummer);
    }

    public List<Artikel> getByServicehotline(String servicehotline) {

        BasicDao<Artikel> artikelDao = new BasicDao<>(Artikel.class);
        return artikelDao.getByField("servicehotline", servicehotline);
    }

    public List<Artikel> getByGarantieZeit(String garantieZeit) {

        BasicDao<Artikel> artikelDao = new BasicDao<>(Artikel.class);
        return artikelDao.getByField("garantieZeit", garantieZeit);
    }

    public List<Artikel> getByAblaufGarantie(String ablaufGarantie) {

        BasicDao<Artikel> artikelDao = new BasicDao<>(Artikel.class);
        return artikelDao.getByField("ablaufGarantie", ablaufGarantie);
    }

    public List<Artikel> getByVerpackung(String verpackung) {

        BasicDao<Artikel> artikelDao = new BasicDao<>(Artikel.class);
        return artikelDao.getByField("verpackung", verpackung);
    }

    public List<Artikel> getByDeleted(String deleted) {

        BasicDao<Artikel> artikelDao = new BasicDao<>(Artikel.class);
        return artikelDao.getByField("deleted", deleted);
    }

    public List<Artikel> getByCreated(String created) {

        BasicDao<Artikel> artikelDao = new BasicDao<>(Artikel.class);
        return artikelDao.getByField("created", created);
    }

    public List<Artikel> getByModified(String modified) {

        BasicDao<Artikel> artikelDao = new BasicDao<>(Artikel.class);
        return artikelDao.getByField("modified", modified);
    }

    @SuppressWarnings("unchecked")
    public List<Artikel> getAvailableArtikel(Date from, Date to, String typ) {
        Session session = HibernateHelper.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Query<Artikel> q = session.getNamedQuery("getAvailableArtikel");
        q.setParameter("from", from);
        q.setParameter("to", to);
        q.setParameter("typ", typ);

        List<Artikel> list = q.getResultList();
        transaction.commit();

        return (list != null ? list : new ArrayList<>());

    }

    public boolean okToLoan(Date from, Date to, Long id) {
        int lendArtikel = countLendArtikel(from, to, id).size();
        String temp = findById(id).getMenge();
        int numberOfArtikel = Integer.parseInt(temp);
        return lendArtikel < numberOfArtikel;

    }

    @SuppressWarnings("unchecked")
    public List<Artikel> countLendArtikel(Date from, Date to, Long id) {
        Session session = HibernateHelper.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Query<Artikel> q = session.getNamedQuery("countLendArtikel");
        q.setParameter("from", from);
        q.setParameter("to", to);
        q.setParameter("id", id);

        List<Artikel> list = q.getResultList();
        transaction.commit();

        return (list != null ? list : new ArrayList<>());

    }

    public List<Artikel> getFreeArtikel(Date from, Date to) {
        int artikelCount;
        Artikel a;
        List<Artikel> aList;
        aList = getAll();
        artikelCount = aList.size();
        aList.clear();

        for (long i = 1; i < artikelCount + 1; i++) {
            if (okToLoan(from, to, i)) {
                a = findById(i);
                aList.add(a);
            }
        }
        return aList;
    }
}
