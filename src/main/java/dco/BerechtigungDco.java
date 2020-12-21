package dco;

import java.util.Date;
import java.util.List;

import Database.Entities.Berechtigung;
import Database.Entities.UserGroups;
import dao.BasicDao;

public class BerechtigungDco {
	
	public void createBerechtigung(String accessModule, String name){
		Berechtigung berechtigung = new Berechtigung();
		Date date = new Date();
		
		berechtigung.setAccessModule(accessModule);
		berechtigung.setName(name);
		berechtigung.setCreated(date);
		
		BasicDao<Berechtigung> berechtigungDao = new BasicDao<Berechtigung>(Berechtigung.class);
		berechtigungDao.create(berechtigung);
	}


	
	public boolean deleteBerechtigung(long id){
		BasicDao<Berechtigung> berechtigungDao = new BasicDao<Berechtigung>(Berechtigung.class);
		return berechtigungDao.delete(berechtigungDao.findById(id));
	}
	
	public void editBerechtigung(String accessModule, String name){
		Berechtigung berechtigung = new Berechtigung();
		Date date = new Date();
		
		berechtigung.setAccessModule(accessModule);
		berechtigung.setName(name);
		berechtigung.setModified(date);
		
		BasicDao<Berechtigung> berechtigungDao = new BasicDao<Berechtigung>(Berechtigung.class);
		berechtigungDao.edit(berechtigung);
	}
}
