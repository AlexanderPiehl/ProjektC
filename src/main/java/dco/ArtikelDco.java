package dco;


import Database.Entities.Artikel;
import dao.BasicDao;

import java.util.Date;

public class ArtikelDco {


    public void createArtikel(String hersteller, String typ, String art, String kategorie, String verzeichnisbaum,
                              String kaufdatum, String preis, String lieferant, String volNr, String bemerkungen, String zustand,
                              String name, String menge, String elektrischeLeistung, String packmass, String gewicht, String leuchtmitteltyp,
                              String akkutyp, String batterientyp, String batterienAnzahl, String speichermedium, String status,
                              String seriennummer, String servicehotline, String garantieZeit, String ablaufGarantie, String verpackung) {
        Date date = new Date();
        Artikel artikel = new Artikel();
        if (menge == " " || menge == null)
            menge = "1";

        artikel.setHersteller(hersteller);
        artikel.setTyp(typ);
        artikel.setArt(art);
        artikel.setKategorie(kategorie);
        artikel.setVerzeichnisbaum(verzeichnisbaum);
        artikel.setKaufdatum(kaufdatum);
        artikel.setPreis(preis);
        artikel.setLieferant(lieferant);
        artikel.setVolNr(volNr);
        artikel.setBemerkungen(bemerkungen);
        artikel.setZustand(zustand);
        artikel.setName(name);
        artikel.setMenge(menge);
        artikel.setElektrischeLeistung(elektrischeLeistung);
        artikel.setPackmass(packmass);
        artikel.setGewicht(gewicht);
        artikel.setLeuchtmitteltyp(leuchtmitteltyp);
        artikel.setAkkutyp(akkutyp);
        artikel.setBatterientyp(batterientyp);
        artikel.setBatterienAnzahl(batterienAnzahl);
        artikel.setSpeichermedium(speichermedium);
        artikel.setStatus(status);
        artikel.setSeriennummer(seriennummer);
        artikel.setServicehotline(servicehotline);
        artikel.setGarantieZeit(garantieZeit);
        artikel.setAblaufGarantie(ablaufGarantie);
        artikel.setVerpackung(verpackung);
        artikel.setCreated(date);

        BasicDao<Artikel> artikelDao = new BasicDao<Artikel>(Artikel.class);
        artikelDao.create(artikel);

    }

    public boolean deleteArtikel(long id) {
        BasicDao<Artikel> artikelDao = new BasicDao<Artikel>(Artikel.class);
        return artikelDao.delete(artikelDao.findById(id));
    }

    public void editArtikel(long id, String hersteller, String typ, String art, String kategorie, String verzeichnisbaum,
                            String kaufdatum, String preis, String lieferant, String volNr, String bemerkungen, String zustand,
                            String name, String menge, String elektrischeLeistung, String packmass, String gewicht, String leuchtmitteltyp,
                            String akkutyp, String batterientyp, String batterienAnzahl, String speichermedium, String status,
                            String seriennummer, String servicehotline, String garantieZeit, String ablaufGarantie, String verpackung) {

        Date date = new Date();
        BasicDao<Artikel> artikelDao = new BasicDao<Artikel>(Artikel.class);

        Artikel artikel = artikelDao.findById(id);
        artikel.setHersteller(hersteller);
        artikel.setTyp(typ);
        artikel.setArt(art);
        artikel.setKategorie(kategorie);
        artikel.setVerzeichnisbaum(verzeichnisbaum);
        artikel.setKaufdatum(kaufdatum);
        artikel.setPreis(preis);
        artikel.setLieferant(lieferant);
        artikel.setVolNr(volNr);
        artikel.setBemerkungen(bemerkungen);
        artikel.setZustand(zustand);
        artikel.setName(name);
        artikel.setMenge(menge);
        artikel.setElektrischeLeistung(elektrischeLeistung);
        artikel.setPackmass(packmass);
        artikel.setGewicht(gewicht);
        artikel.setLeuchtmitteltyp(leuchtmitteltyp);
        artikel.setAkkutyp(akkutyp);
        artikel.setBatterientyp(batterientyp);
        artikel.setBatterienAnzahl(batterienAnzahl);
        artikel.setSpeichermedium(speichermedium);
        artikel.setStatus(status);
        artikel.setSeriennummer(seriennummer);
        artikel.setServicehotline(servicehotline);
        artikel.setGarantieZeit(garantieZeit);
        artikel.setAblaufGarantie(ablaufGarantie);
        artikel.setVerpackung(verpackung);
        artikel.setModified(date);

        artikelDao.edit(artikel);

    }


}
