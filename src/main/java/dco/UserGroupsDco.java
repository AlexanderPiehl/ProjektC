package dco;

import java.util.Date;
import java.util.List;

import dao.BasicDao;
import Database.Entities.Artikel;
import Database.Entities.Berechtigung;
import Database.Entities.UserGroups;

public class UserGroupsDco {
	
	public void createUserGroups(String name, List<Berechtigung> berechtigung){
		UserGroups userGroup = new UserGroups();
		Date date = new Date();
		
		userGroup.setName(name);
		userGroup.setBerechtigung(berechtigung);
		userGroup.setCreated(date);
		
		BasicDao<UserGroups> userGroupDao = new BasicDao<UserGroups>(UserGroups.class);
		userGroupDao.create(userGroup);
	}
	
	public boolean deleteUserGroups(long id){
		BasicDao<UserGroups> userGroupsDao = new BasicDao<UserGroups>(UserGroups.class);
		return userGroupsDao.delete(userGroupsDao.findById(id));
	}
	
	public void editUserGroups(String name, List<Berechtigung> berechtigung){
		UserGroups userGroup = new UserGroups();
		Date date = new Date();
		
		userGroup.setName(name);
		userGroup.setBerechtigung(berechtigung);
		userGroup.setModified(date);
		
		BasicDao<UserGroups> userGroupDao = new BasicDao<UserGroups>(UserGroups.class);
		userGroupDao.edit(userGroup);
	}

}
