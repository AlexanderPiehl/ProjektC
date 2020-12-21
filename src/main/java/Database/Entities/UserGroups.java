package Database.Entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

/**
 * Entitaet "UserGroups" 
 * 
 * POJO Plain Old JAva Object
 * 
 * Berechtigungen werden UserGroups zugeordnet
 * BSP:
 * id: 5
 * Name: AdminGroup
 * created:timestamp
 * modified: timestamp
 * berechtigungen: greylist_edit, blacklist_edit, labor1_admin, labor2_admin..... 
 *
 */
@Entity 
public class UserGroups implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@Column
	private String Name; 
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date modified;
	/*
	 * TODO #39 - Wieso "customer" ?
	 * 
	 */
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "memberOfGroups",  cascade=CascadeType.PERSIST)
	List<User> user = new ArrayList<User>();
	
//	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "memberOfGroups",  cascade=CascadeType.ALL)
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "UserGroups_Berechtigungen", joinColumns = { 
			@JoinColumn(name = "UserGroup_ID_ID", nullable = true) }, 
			inverseJoinColumns = { @JoinColumn(name = "Berechtigung_ID", 
					nullable = true) }) 
	List<Berechtigung> berechtigung = new ArrayList<Berechtigung>();
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
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
	public List<User> getUser() {
		return user;
	}
	public void setUser(List<User> user) {
		this.user = user;
	}
	public List<Berechtigung> getBerechtigung() {
		return berechtigung;
	}
	public void setBerechtigung(List<Berechtigung> berechtigung) {
		this.berechtigung = berechtigung;
	}


	}
