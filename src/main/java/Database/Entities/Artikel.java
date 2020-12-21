package Database.Entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

/**
 * Entitaet "Artikel" 
 * 
 * POJO Plain Old JAva Object
 * 
 */
@Entity
@NamedQueries({@NamedQuery(name="getAvailableArtikel", query = "SELECT A FROM Artikel A LEFT JOIN A.reservierungen R WHERE ((R.reservedFrom > :to AND R.reservedTo < :from) OR A.reservierungen IS EMPTY) AND A.typ LIKE :typ"),
	@NamedQuery(name="countLendArtikel", query = "SELECT A FROM Artikel A LEFT JOIN A.reservierungen R WHERE A.id = :id AND ((R.reservedFrom <= :from AND R.reservedTo >= :from) OR (R.reservedFrom <= :to AND R.reservedTo >= :to))"),
	@NamedQuery(name="getFreeArtikel", query = "SELECT A FROM Artikel A LEFT JOIN A.reservierungen R WHERE A.id = :id AND ((R.reservedFrom >= :from AND R.reservedTo <= :from) OR (R.reservedFrom >= :to AND R.reservedTo <= :to))")})
public class Artikel implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@Column
	private String hersteller;
	@Column
	private String typ;
	@Column
	private String art;
	@Column
	private String name;
	@Column
	private String kategorie;
	@Column
	private String verzeichnisbaum;
	@Column
	private String kaufdatum;
	@Column
	private String preis;
	@Column
	private String lieferant;
	@Column
	private String volNr;
	@Column
	private String bemerkungen;
	@Column
	private String zustand;
	@Lob
	@Column(columnDefinition = "longblob")
	private byte[] foto;
//	@Column
//	private String foto;
	@Column
	private String menge;
	@Column
	private String elektrischeLeistung;
	@Column
	private String packmass;
	@Column
	private String gewicht;
	@Column
	private String leuchtmitteltyp;
	@Column
	private String akkutyp;
	@Column
	private String batterientyp;
	@Column
	private String batterienAnzahl;
	@Column
	private String speichermedium;
	@Column
	private String status;
	@Column
	private String seriennummer;
	@Column
	private String servicehotline;
	@Column
	private String garantieZeit;
	@Column
	private String ablaufGarantie;
	@Column
	private String verpackung;

	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date deleted;
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date modified;
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "artikel", cascade=CascadeType.PERSIST)
	List<Reservierung> reservierungen = new ArrayList<Reservierung>();
	
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "artikel",  cascade=CascadeType.PERSIST)
	List<Verleihhistorie> verleihhistorie = new ArrayList<Verleihhistorie>();
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "artikel")
	List<Bilder> bilder = new ArrayList<Bilder>();
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getHersteller() {
		return hersteller;
	}
	public void setHersteller(String hersteller) {
		this.hersteller = hersteller;
	}
	public String getTyp() {
		return typ;
	}
	public void setTyp(String typ) {
		this.typ = typ;
	}
	public String getArt() {
		return art;
	}
	public void setArt(String art) {
		this.art = art;
	}
	public String getKategorie() {
		return kategorie;
	}
	public void setKategorie(String kategorie) {
		this.kategorie = kategorie;
	}
	public String getVerzeichnisbaum() {
		return verzeichnisbaum;
	}
	public void setVerzeichnisbaum(String verzeichnisbaum) {
		this.verzeichnisbaum = verzeichnisbaum;
	}
	public String getKaufdatum() {
		return kaufdatum;
	}
	public void setKaufdatum(String kaufdatum) {
		this.kaufdatum = kaufdatum;
	}
	public String getPreis() {
		return preis;
	}
	public void setPreis(String preis) {
		this.preis = preis;
	}
	public String getLieferant() {
		return lieferant;
	}
	public void setLieferant(String lieferant) {
		this.lieferant = lieferant;
	}
	public String getVolNr() {
		return volNr;
	}
	public void setVolNr(String volNr) {
		this.volNr = volNr;
	}
	public String getBemerkungen() {
		return bemerkungen;
	}
	public void setBemerkungen(String bemerkungen) {
		this.bemerkungen = bemerkungen;
	}
	public String getZustand() {
		return zustand;
	}
	public void setZustand(String zustand) {
		this.zustand = zustand;
	}	
//	public String getFoto() {
//		return foto;
//	}
//	public void setFoto(String foto) {
//		this.foto = foto;
//	}
		public byte[] getFoto() {
		return foto;
	}
	public void setFoto(byte[] foto) {
		this.foto = foto;
	}
	public String getMenge() {
		return menge;
	}
	public void setMenge(String menge) {
		this.menge = menge;
	}
	public String getElektrischeLeistung() {
		return elektrischeLeistung;
	}
	public void setElektrischeLeistung(String elektrischeLeistung) {
		this.elektrischeLeistung = elektrischeLeistung;
	}
	public String getPackmass() {
		return packmass;
	}
	public void setPackmass(String packmass) {
		this.packmass = packmass;
	}
	public String getGewicht() {
		return gewicht;
	}
	public void setGewicht(String gewicht) {
		this.gewicht = gewicht;
	}
	public String getLeuchtmitteltyp() {
		return leuchtmitteltyp;
	}
	public void setLeuchtmitteltyp(String leuchtmitteltyp) {
		this.leuchtmitteltyp = leuchtmitteltyp;
	}
	public String getAkkutyp() {
		return akkutyp;
	}
	public void setAkkutyp(String akkutyp) {
		this.akkutyp = akkutyp;
	}
	public String getBatterientyp() {
		return batterientyp;
	}
	public void setBatterientyp(String batterientyp) {
		this.batterientyp = batterientyp;
	}
	public String getBatterienAnzahl() {
		return batterienAnzahl;
	}
	public void setBatterienAnzahl(String batterienAnzahl) {
		this.batterienAnzahl = batterienAnzahl;
	}
	public String getSpeichermedium() {
		return speichermedium;
	}
	public void setSpeichermedium(String speichermedium) {
		this.speichermedium = speichermedium;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSeriennummer() {
		return seriennummer;
	}
	public void setSeriennummer(String seriennummer) {
		this.seriennummer = seriennummer;
	}
	public String getServicehotline() {
		return servicehotline;
	}
	public void setServicehotline(String servicehotline) {
		this.servicehotline = servicehotline;
	}
	public String getGarantieZeit() {
		return garantieZeit;
	}
	public void setGarantieZeit(String garantieZeit) {
		this.garantieZeit = garantieZeit;
	}
	public String getAblaufGarantie() {
		return ablaufGarantie;
	}
	public void setAblaufGarantie(String ablaufGarantie) {
		this.ablaufGarantie = ablaufGarantie;
	}
	public String getVerpackung() {
		return verpackung;
	}
	public void setVerpackung(String verpackung) {
		this.verpackung = verpackung;
	}
	public Date getDeleted() {
		return deleted;
	}
	public void setDeleted(Date deleted) {
		this.deleted = deleted;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public Date getModified() {
		return modified;
	}
	public void setModified(Date modified) {
		this.modified = modified;
	}
	public List<Reservierung> getReservierungen() {
		return reservierungen;
	}
	public void setReservierungen(List<Reservierung> reservierungen) {
		this.reservierungen = reservierungen;
	}
	public List<Verleihhistorie> getVerleihhistorie() {
		return verleihhistorie;
	}
	public void setVerleihhistorie(List<Verleihhistorie> verleihhistorie) {
		this.verleihhistorie = verleihhistorie;
	}
	public List<Bilder> getBilder() {
		return bilder;
	}
	public void setBilder(List<Bilder> bilder) {
		this.bilder = bilder;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
