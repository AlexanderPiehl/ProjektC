package Database.Entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

/**
 * Entitaet "ArtikelVerleihhistorie" 
 * 
 * POJO Plain Old JAva Object
 * 
 * Entity zum Speichern der Verleihistorie für Nutzer und Artikel
 * 
 */
@Entity
public class Verleihhistorie implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@Column
	private int barcodeData;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID", nullable = true)
	private User customer;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "DEALER_ID",nullable = true)
	private User dealer;
	@Column
	private String comments;
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date deleted;
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date modified;
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date reservedFrom;
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date reservedTo;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "artikel_Verleihhistorie", joinColumns = { 
			@JoinColumn(name = "VERLEIHHISTORIE_ID", nullable = true) }, 
			inverseJoinColumns = { @JoinColumn(name = "HISTORIEARTIKEL_ID", 
					nullable = true) })
	private List<Artikel> artikel;
	
//	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "verleihhistorie",  cascade=CascadeType.REFRESH)
//	private List<Artikel> artikel;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getBarcodeData() {
		return barcodeData;
	}
	public void setBarcodeData(int barcodeData) {
		this.barcodeData = barcodeData;
	}
	public User getCustomer() {
		return customer;
	}
	public void setCustomer(User customer) {
		this.customer = customer;
	}
	public User getDealer() {
		return dealer;
	}
	public void setDealer(User dealer) {
		this.dealer = dealer;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
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
	public List<Artikel> getArtikel() {
		return artikel;
	}
	public void setArtikel(List<Artikel> artikel) {
		this.artikel = artikel;
	}
	public Date getReservedFrom() {
		return reservedFrom;
	}
	public void setReservedFrom(Date reservedFrom) {
		this.reservedFrom = reservedFrom;
	}
	public Date getReservedTo() {
		return reservedTo;
	}
	public void setReservedTo(Date reservedTo) {
		this.reservedTo = reservedTo;
	}
}
