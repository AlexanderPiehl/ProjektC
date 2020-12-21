package Database.Entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

/**
 * Entitaet "Berechtigung" 
 * 
 * POJO Plain Old JAva Object
 * 
 * einzelne Berechtigungen werden Gruppen zugeordnet
 * BSP:
 * id:5
 * Name: Graue Liste bearbeiten
 * accessModule: greylist_Edit
 *
 */
@Entity 
public class Berechtigung implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@Column
	private String Name;
	@Column(unique=true)
	private String accessModule; // Das "Modul" auf das geprueft wird.
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date modified;
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "berechtigung",  cascade=CascadeType.PERSIST)
	List<UserGroups> memberOfGroups = new ArrayList<UserGroups>();
	
	
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
	public String getAccessModule() {
		return accessModule;
	}
	public void setAccessModule(String accessModule) {
		this.accessModule = accessModule;
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
	public List<UserGroups> getMemberOfGroups() {
		return memberOfGroups;
	}
	public void setMemberOfGroups(List<UserGroups> memberOfGroups) {
		this.memberOfGroups = memberOfGroups;
	}
	
	
	}
