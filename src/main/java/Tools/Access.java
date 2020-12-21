package Tools;

import haw.Ausleihe.MySession;

import java.util.ArrayList;
import java.util.List;

import Database.Entities.Berechtigung;
import Database.Entities.SanktionsListe;
import Database.Entities.User;
import Database.Entities.UserGroups;
import dao.BasicDao;
import dao.SanktionsListeDao;
import dao.UserDao;
import dao.UserGroupsDao;
import dco.BerechtigungDco;
import dco.UserGroupsDco;

public class Access {
	
	private List<Berechtigung> accessRights = new ArrayList<Berechtigung>();
	private List<UserGroups> userGroups = new ArrayList<UserGroups>();
	private String currentUser;
	private boolean isLoggedin;
	
	public Access(){}
	
	public static void init(){
		BerechtigungDco berechtigungDco = new BerechtigungDco();

		berechtigungDco.createBerechtigung("view_Products", "Produktkatalog einsehen");
		
		berechtigungDco.createBerechtigung("reserve_products", "Produkte reservieren");
		
		berechtigungDco.createBerechtigung("book_reserved", "Buchung best&auml;tigen");
		
		berechtigungDco.createBerechtigung("lending", "Ausleihe vornehmen");
		
		berechtigungDco.createBerechtigung("return_items", "R&uumlcknahme");
		
		berechtigungDco.createBerechtigung("article_edit", "Artikel editieren");

		berechtigungDco.createBerechtigung("article_remove", "Artikel l&ouml;schen");

		berechtigungDco.createBerechtigung("article_create", "Artikel erstellen");
		
		berechtigungDco.createBerechtigung("view_Greylist", "Graueliste einsehen");

		berechtigungDco.createBerechtigung("view_Blacklist", "Schwarzeliste einsehen");
		
		berechtigungDco.createBerechtigung("edit_Greylist", "Graueliste bearbeiten");

		berechtigungDco.createBerechtigung("edit_Blacklist", "Schwarzeliste bearbeiten");

		berechtigungDco.createBerechtigung("manage_own_lab", "Eigenes Labor editieren");
		
		berechtigungDco.createBerechtigung("is_admin", "Administrator Berechtigung");
	}
	
	private void createDefaultUserGroupsWithRights() {
		/*
		 * Basic Usergroups
		 * 
		 */
		
		BasicDao<Berechtigung> berechtigungDao = new BasicDao<Berechtigung>(Berechtigung.class);
		List<Berechtigung> studentList = new ArrayList<Berechtigung>();
		List<Berechtigung> basicAssistantList = new ArrayList<Berechtigung>();
		List<Berechtigung> basicManagerList = new ArrayList<Berechtigung>();
		List<Berechtigung> adminList = new ArrayList<Berechtigung>();
		
		
		
		Berechtigung view_Products = new Berechtigung();
		view_Products.setAccessModule("view_Products");
		view_Products.setName("Produktkatalog einsehen");
		berechtigungDao.create(view_Products);
		studentList.add(view_Products);
		
		Berechtigung reserve_Products = new Berechtigung();
		reserve_Products.setAccessModule("reserve_products");
		reserve_Products.setName("Produkte reservieren");
		berechtigungDao.create(reserve_Products);
		studentList.add(reserve_Products);
		
		Berechtigung book_reserved = new Berechtigung();
		book_reserved.setAccessModule("book_reserved");
		book_reserved.setName("Buchung best&auml;tigen");
		berechtigungDao.create(book_reserved);
		basicAssistantList.add(book_reserved);
		
		Berechtigung lend_items = new Berechtigung();
		lend_items.setAccessModule("lending");
		lend_items.setName("Ausleihe vornehmen");
		berechtigungDao.create(lend_items);
		basicAssistantList.add(lend_items);
		
		Berechtigung return_items = new Berechtigung();
		return_items.setAccessModule("return_items");
		return_items.setName("R&uumlcknahme");
		berechtigungDao.create(return_items);
		basicAssistantList.add(return_items);
		
		Berechtigung article_edit = new Berechtigung();
		article_edit.setAccessModule("article_edit");
		article_edit.setName("Artikel editieren");
		berechtigungDao.create(article_edit);
		basicAssistantList.add(article_edit);
		
		Berechtigung article_remove = new Berechtigung();
		article_remove.setAccessModule("article_remove");
		article_remove.setName("Artikel l&ouml;schen");
		berechtigungDao.create(article_remove);
		basicAssistantList.add(article_remove);
		
		Berechtigung article_create = new Berechtigung();
		article_create.setAccessModule("article_create");
		article_create.setName("Artikel erstellen");
		berechtigungDao.create(article_create);
		basicAssistantList.add(article_create);
		
		Berechtigung view_Greylist = new Berechtigung();
		view_Greylist.setAccessModule("view_Greylist");
		view_Greylist.setName("Graueliste einsehen");
		berechtigungDao.create(view_Greylist);
		basicAssistantList.add(view_Greylist);

		Berechtigung view_Blacklist = new Berechtigung();
		view_Blacklist.setAccessModule("view_Blacklist");
		view_Blacklist.setName("Schwarzeliste einsehen");
		berechtigungDao.create(view_Blacklist);
		basicAssistantList.add(view_Blacklist);
		
		Berechtigung edit_Greylist = new Berechtigung();
		edit_Greylist.setAccessModule("edit_Greylist");
		edit_Greylist.setName("Graueliste bearbeiten");
		berechtigungDao.create(edit_Greylist);
		basicAssistantList.add(edit_Greylist);


		Berechtigung edit_Blacklist = new Berechtigung();
		edit_Blacklist.setAccessModule("edit_Blacklist");
		edit_Blacklist.setName("Schwarzeliste bearbeiten");
		berechtigungDao.create(edit_Blacklist);
		basicManagerList.add(edit_Blacklist);
		
		Berechtigung manage_own_lab = new Berechtigung();
		manage_own_lab.setAccessModule("manage_own_lab");
		manage_own_lab.setName("Eigenes Labor editieren");
		berechtigungDao.create(manage_own_lab);
		basicManagerList.add(manage_own_lab);
		
		Berechtigung is_admin = new Berechtigung();
		is_admin.setAccessModule("is_admin");
		is_admin.setName("Administrator Berechtigung");
		berechtigungDao.create(is_admin);
		adminList.add(is_admin);
		
		
		
		
		UserGroupsDco userGroups = new UserGroupsDco();
		
		basicAssistantList.addAll(studentList);
		basicManagerList.addAll(basicAssistantList);
		adminList.addAll(basicManagerList);
		
		userGroups.createUserGroups("student", studentList);
		userGroups.createUserGroups("basicAssistant", basicAssistantList);
		userGroups.createUserGroups("basicManager", basicManagerList);
		userGroups.createUserGroups("admin", adminList);		
	}


	private boolean getUserGroupsFromCurrentUser(){
		if(MySession.get().getLoggedinID() != null){
			if(currentUser == null){				
				currentUser = MySession.get().getLoggedinID();
				isLoggedin = true;
				User user = this.getUserObjectByKennung(currentUser);
				userGroups = user.getMemberOfGroups();
				return true;
			}else{
				if(MySession.get().getLoggedinID().equals(currentUser)){
					return true;
				}else{
					currentUser = MySession.get().getLoggedinID();
					isLoggedin = true;
					accessRights.clear();
					User user = this.getUserObjectByKennung(this.currentUser);
					userGroups = user.getMemberOfGroups();
					return true;
				}
			}
		}
		else{
			currentUser = null;
			isLoggedin = false;
			return false;
		}
	}
	
	private void getAccessRightsFromCurrentUser(){
		UserGroupsDao uGDao = new UserGroupsDao();
		this.getUserGroupsFromCurrentUser();
		for(UserGroups u: userGroups){
			u = uGDao.findUserGroupsWithBerechtigungById(u.getId());
			List<Berechtigung> berechtigungenTemp = u.getBerechtigung();
			for(Berechtigung bAlle:berechtigungenTemp){
				boolean isNeu = true;
				if(accessRights.isEmpty()){
					accessRights.add(bAlle);
				}else{
					for(Berechtigung bBisher:accessRights){
						if(bAlle.getId() == bBisher.getId()){
							isNeu = false;
							break;
						}
					}
					if(isNeu){
						accessRights.add(bAlle);
					}
				}
			}
		}
	}
	
	private User getUserObjectByKennung(String userKennung){
		UserDao userDao = new UserDao();
		User user = userDao.findUserByUniqueFieldWithUserGroups("kennung", userKennung);
		return user;
	}
	
	private boolean rightIsInList(String Module){
		return this.accessRights.contains(Module);
	}
	
	private String getCurrentUser() {
		return currentUser;
	}
	
	private void setCurrentUser(String currentUser) {
		this.currentUser = currentUser;
	}
	
	/*
	 * Public Functions:
	 */
	public boolean isLoggedin(){
		this.getUserGroupsFromCurrentUser();
		
		return this.isLoggedin;
	}
	
	public boolean hasAccessTo(String Module){
		if(Module == "true_for_debug"){
			return true;
		}
		if(Module == "false_for_debug"){
			return false;
		}
		getAccessRightsFromCurrentUser();
		for(Berechtigung berechtigung:this.accessRights){
			
			if(berechtigung.getAccessModule().equals(Module)){

				return true;
			}
		}
		return false;
	}
	
	public boolean isOnGreylist(String userkennung){
		User user = this.getUserObjectByKennung(userkennung);
		SanktionsListeDao sanktionsListeDao = new SanktionsListeDao();
		List<SanktionsListe> sanktionsListe = sanktionsListeDao.getAllGraueListe();
		for(SanktionsListe entry:sanktionsListe){
			if(user.getId() == entry.getUserID().getId()){
				return true;
			}
		}
		return false;
	}
	
	public boolean isOnBlacklist(String userkennung){
		User user = this.getUserObjectByKennung(userkennung);
		SanktionsListeDao sanktionsListeDao = new SanktionsListeDao();
		List<SanktionsListe> sanktionsListe = sanktionsListeDao.getAllSchwarzeListe();
		for(SanktionsListe entry:sanktionsListe){
			if(user.getId() == entry.getUserID().getId()){
				return true;
			}
		}
		return false;
	}
	
	public void createAccessModule(String module, String name){
		BerechtigungDco berechtigung = new BerechtigungDco();
		berechtigung.createBerechtigung(module, name);
	}
	
	public void createUserGroup(String userGroupName,  List<Berechtigung> berechtigung){
		UserGroupsDco userGroup = new UserGroupsDco();
		userGroup.createUserGroups(userGroupName, berechtigung);
	}
	
	public void addBerechtigungToUserGroup(Berechtigung berechtigung, String userGroupName){
		UserGroupsDao userGroupDao = new UserGroupsDao();
		UserGroups userGroupTemp = new UserGroups();
		userGroupTemp = userGroupDao.getUniqueByField("Name", userGroupName);
		List<Berechtigung> berechtigungsListe = new ArrayList<Berechtigung>();
		berechtigungsListe = userGroupTemp.getBerechtigung();
		if(!berechtigungsListe.contains(berechtigung)){
			berechtigungsListe.add(berechtigung);
			userGroupTemp.setBerechtigung(berechtigungsListe);
			userGroupDao.edit(userGroupTemp);
		}
	}
	
	public void addUserGroupToUser(String userkennung, String userGroupName){		
		UserGroupsDao userGroupDao = new UserGroupsDao();
		UserGroups userGroup = new UserGroups();
		userGroup = userGroupDao.getUniqueByField("Name", userGroupName);
		
		BasicDao<User> userDao = new BasicDao<User>(User.class);
		User user = userDao.getUniqueByField("kennung", userkennung);
		List<UserGroups> isMemberOf = new ArrayList<UserGroups>();
		isMemberOf = user.getMemberOfGroups();
		if(!isMemberOf.contains(userGroup)){
			isMemberOf.add(userGroup);
			user.setMemberOfGroups(isMemberOf);
			userDao.edit(user);
		}
	}

}
