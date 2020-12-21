package Database.Entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

/**
 * Entitaet "User" 
 * 
 * POJO Plain Old JAva Object
 * 
 * wird in tabelle gemappt
 * 
 * 
 *
 */
@Entity 
public class User implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@Column(unique=true)
	private String kennung;
	@Column
	private String firstName;
	@Column
	private String lastName;
	@Column
	private String eMail;
	@Column
	private String telefon;
	@Column
	private String status;
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date modified;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
	List<Reservierung> reservierungen = new ArrayList<Reservierung>();
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "dealer")
	List<Reservierung> deals = new ArrayList<Reservierung>();
	@OneToOne(fetch = FetchType.LAZY)
	private SanktionsListe sanktionsListe; 
/*
 * TODO anpassen der DB
 */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "User_UserGroups", joinColumns = { 
			@JoinColumn(name = "User_ID", nullable = true) }, 
			inverseJoinColumns = { @JoinColumn(name = "UserGroup_ID", 
					nullable = true) }) 
	List<UserGroups> memberOfGroups = new ArrayList<UserGroups>();
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String geteMail() {
		return eMail;
}
	public void seteMail(String eMail) {
		this.eMail = eMail;
	}
	public String getKennung() {
		return kennung;
	}
	public void setKennung(String kennung) {
		this.kennung = kennung;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public String getTelefon() {
		return telefon;
	}
	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}
	public List<Reservierung> getReservierungen() {
		return reservierungen;
	}
	public void setReservierungen(List<Reservierung> reservierungen) {
		this.reservierungen = reservierungen;
	}
	public List<Reservierung> getDeals() {
		return deals;
	}
	public void setDeals(List<Reservierung> deals) {
		this.deals = deals;
	}	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public List<UserGroups> getMemberOfGroups() {
		return memberOfGroups;
	}
	public void setMemberOfGroups(List<UserGroups> memberOfGroups) {
		this.memberOfGroups = memberOfGroups;
	}
	public SanktionsListe getSanktionsListe() {
		return sanktionsListe;
	}
	public void setSanktionsListe(SanktionsListe sanktionsListe) {
		this.sanktionsListe = sanktionsListe;
	}
}
