package Database.Entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import enums.WartungsEnum;

/**
 * Entitaet "ArtikelVerleihhistorie" 
 * 
 * POJO Plain Old JAva Object
 * 
 * Entity zum Speichern der Wartungs- Pruefhistorie von Artikeln
 * 
 */
@Entity
public class Wartung implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ARTIKEL_ID",nullable = true)
	private Artikel artikel;	
	@Column
	@Enumerated(EnumType.STRING)
	private WartungsEnum status;
	@Column
	private String decs;
	@Column
	private String maintenanceDate;
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date modified;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Artikel getArtikel() {
		return artikel;
	}
	public void setArtikel(Artikel artikel) {
		this.artikel = artikel;
	}
	public WartungsEnum getStatus() {
		return status;
	}
	public void setStatus(WartungsEnum status) {
		this.status = status;
	}
	public String getMaintenanceDate() {
		return maintenanceDate;
	}
	public void setMaintenanceDate(String maintenanceDate) {
		this.maintenanceDate = maintenanceDate;
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
	public String getDecs() {
		return decs;
	}
	public void setDecs(String decs) {
		this.decs = decs;
	}
	
}
