package Database.Entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import org.apache.wicket.model.IModel;

import enums.SanktionenEnum;

@Entity
public class SanktionsListe implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@OneToOne(fetch = FetchType.LAZY)
	private User userID;
	@Temporal(TemporalType.TIMESTAMP)
	private Date deleted; 
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date modified;
	@Column
	@Enumerated(EnumType.STRING)
	private SanktionenEnum status;
	@Column
	private String comments;
	
	public Date getModified() {
		return modified;
	}
	public void setModified(Date modified) {
		this.modified = modified;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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
	public User getUserID() {
		return userID;
	}
	public void setUserID(User userID) {
		this.userID = userID;
	}
	public SanktionenEnum getStatus() {
		return status;
	}
	public void setStatus(SanktionenEnum status) {
		this.status = status;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
}

