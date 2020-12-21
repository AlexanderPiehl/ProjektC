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
public class Bilder implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@Lob
	@Column(columnDefinition = "longblob")
	private byte[] bild;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Artikel_ID",nullable = true)
	private Artikel artikel;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public byte[] getBild() {
		return bild;
	}
	public void setBild(byte[] bild) {
		this.bild = bild;
	}
	public Artikel getArtikel() {
		return artikel;
	}
	public void setArtikel(Artikel artikel) {
		this.artikel = artikel;
	}
}
