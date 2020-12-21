package haw.Ausleihe;

import haw.Ausleihe.ActionPanels.ActionPanelBerechtigungUserLoeschen;
import haw.Ausleihe.ActionPanels.ActionPanelNeueBerechtigungUser;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import Database.Entities.User;
import Database.Entities.UserGroups;
import dao.UserDao;
import dao.UserGroupsDao;

public class BerechtigungSetzen extends WebPage{
	private static final long serialVersionUID = 1L;
	long userID;

	public BerechtigungSetzen(PageParameters parameters){
		super(parameters);
		add(new HeaderPanel("headerPanel"));
		add(new FooterPanel("footerPanel"));
		add(new NavigationPanel("navigationPanel"));
		
		User user = loadUser(parameters);
		createTableBisherigeBerechtigungen(user);
		createTableNeueBerechtigung(user);
		
		showUserDetails(user);
	}
	
	private void createTableNeueBerechtigung(User user) {
		List<UserGroups> userGroupsOfUser = loadNeueUSerGroups(user.getMemberOfGroups());		
		createTableHeadNeueBerechtigungen(userGroupsOfUser.isEmpty());
		showErrorMessageNeueBerechtigung(userGroupsOfUser.isEmpty());
		
		ListDataProvider<UserGroups> listDtaProvider = new ListDataProvider<UserGroups>(userGroupsOfUser);
		 DataView<UserGroups> dataView = new DataView<UserGroups>("rowNeueBerechtigungen", listDtaProvider) {
			private static final long serialVersionUID = 1L;

			@Override  
			 protected void populateItem(Item<UserGroups> item) {
					UserGroups uG = item.getModelObject();
					item.add(new Label("idNeue",uG.getId()));
	                item.add(new Label("kennungNeue",uG.getName()));
	                ActionPanelNeueBerechtigungUser panelBerechtigungAdd= new ActionPanelNeueBerechtigungUser("neueBerechtigung", userID, uG.getId());
	                item.add(panelBerechtigungAdd);	
	            }
	        };
	        dataView.setItemsPerPage(25);
	        add(dataView);
	}
	private void showErrorMessageNeueBerechtigung(boolean isEmpty) {
		Label errorNeue = new Label("errorNeue",  Model.of("Es sind noch keine Berechtigungsgruppen angelegt worden."));
		if(!isEmpty){
			errorNeue.setVisible(false);
		}
		add(errorNeue);		
	}

	private void createTableHeadNeueBerechtigungen(boolean isEmpty) {
		Label idHeadNeu = new Label("idHeadNeu", Model.of("ID"));
		Label nameHeadNeu = new Label("nameHeadNeu", Model.of("Name"));
		Label addHeadNeu = new Label("addHeadNeu", Model.of("Hinzufügen"));
		
		if(isEmpty){
			idHeadNeu.setVisible(false);
			nameHeadNeu.setVisible(false);
			addHeadNeu.setVisible(false);
		}
		
		add(idHeadNeu);
		add(nameHeadNeu);
		add(addHeadNeu);	
	}

	private List<UserGroups> loadNeueUSerGroups(List<UserGroups> bisherigeUserGroups) {
		UserGroupsDao userGroupsDao = new UserGroupsDao();
		List<UserGroups> alleUserGroups = userGroupsDao.getAll();
		List<UserGroups> neueUserGroups = new ArrayList<UserGroups>();
		
		
		
		if(bisherigeUserGroups == null){
			return alleUserGroups;
		}else{
			if(bisherigeUserGroups.size() == 0){
				return alleUserGroups;
			}else{
				for(UserGroups uGNeu: alleUserGroups){
					boolean isNeu = true;
					for(UserGroups uG: bisherigeUserGroups){
						if(uG.getId() == uGNeu.getId()){
							isNeu = false;
							break;
						}
					}
					if(isNeu){
						neueUserGroups.add(uGNeu);
					}
				}
				return neueUserGroups;
			}
		}
	}

	private User loadUser(PageParameters parameters){
		userID = parameters.get("UserID").toLong();
		UserDao userDao = new UserDao();
		return userDao.findUserWithUserGroupsById(userID);	
	}
	
	private void showUserDetails(User user){
		Label labelUserId  = new Label("labelUserId", Model.of("User: " + user.getId()));
		Label labelUserName = new Label("labelUserName", Model.of(user.getFirstName() + " "+ user.getLastName()));
		Label labelUserEMail = new Label("labelUserEMail", Model.of(user.geteMail()));
		Label labelUserTelefon = new Label("labelUserTelefon", Model.of(user.getTelefon()));
			
		add(labelUserId);
		add(labelUserName);
		add(labelUserEMail);
		add(labelUserTelefon);
	}
	private void createTableBisherigeBerechtigungen(User user){
		List<UserGroups> userGroupsOfUser = user.getMemberOfGroups();
		createTableHeadBisherigeBerechtigungen(userGroupsOfUser.isEmpty());
		showErrorMessageBisherigeBerechtigung(userGroupsOfUser.isEmpty());
			
		ListDataProvider<UserGroups> listDtaProvider = new ListDataProvider<UserGroups>(userGroupsOfUser);
		 DataView<UserGroups> dataView = new DataView<UserGroups>("rowAktuelleBerechtigungen", listDtaProvider) {
			private static final long serialVersionUID = 1L;

			@Override  
			 protected void populateItem(Item<UserGroups> item) {
					UserGroups uG = item.getModelObject();
					item.add(new Label("id",uG.getId()));
	                item.add(new Label("kennung",uG.getName()));
	                
	                ActionPanelBerechtigungUserLoeschen panelBerechtigungLoeschen = new ActionPanelBerechtigungUserLoeschen("loeschenBerechtigung", userID, uG.getId());
	                item.add(panelBerechtigungLoeschen);	
	            }
	        };
	        dataView.setItemsPerPage(25);
	        add(dataView);
	}
	
	private void showErrorMessageBisherigeBerechtigung(boolean isEmpty) {
		Label errorBisherige = new Label("errorBisherige",  Model.of("Dem Nutzer sind noch keine Berechtigungsgruppen zugeteilt worden."));
		if(!isEmpty){
			errorBisherige.setVisible(false);
		}
		add(errorBisherige);		
	}
	private void createTableHeadBisherigeBerechtigungen(boolean isEmpty){
		Label idHeadAktuell = new Label("idHeadAktuell", Model.of("ID"));
		Label nameHeadAktuell = new Label("nameHeadAktuell", Model.of("Name"));
		Label loeschenHeadAktuell = new Label("loeschenHeadAktuell", Model.of("Löschen"));
		
		if(isEmpty){
			idHeadAktuell.setVisible(false);
			nameHeadAktuell.setVisible(false);
			loeschenHeadAktuell.setVisible(false);
		}
		
		add(idHeadAktuell);
		add(nameHeadAktuell);
		add(loeschenHeadAktuell);
	}
}
