package haw.Ausleihe;

import haw.Ausleihe.ActionPanels.ActionPanelBerechtigungSetzen;
import haw.Ausleihe.ActionPanels.ActionPanelHistorie;
import haw.Ausleihe.ActionPanels.ActionPanelToGrey;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import dao.BasicDao;
import dao.SanktionsListeDao;
import Database.Entities.SanktionsListe;
import Database.Entities.User;
import Models.UserModel;
import Models.UserModel.UserModelType;
import Tools.Access;


public class UserHp extends WebPage {
	private static final long serialVersionUID = 1L;
	private static final Access access = new Access();
	Label graueListeFoundUSers = new Label("graueListeFoundUSers",Model.of("Graue Liste"));
	private static final String KENNUNG_PARAMETER = "kennung";
	private static final String VORNAME_PARAMETER = "vorname";
	private static final String NACHNAME_PARAMETER = "nachname";

	public UserHp(final PageParameters parameters) {
		super(parameters);
		add(new HeaderPanel("headerPanel"));
		add(new FooterPanel("footerPanel"));
		add(new NavigationPanel("navigationPanel"));
		
		createSuchenUserForm();
		createUserTable(parameters);
		createButtons();
	}

	private void createSuchenUserForm() {
		final TextField<String> kennungTextField = new TextField<String>(
				"suchenKennung", Model.of(""));
		final TextField<String> vornameTextField = new TextField<String>(
				"suchenVorname", Model.of(""));
		final TextField<String> nachnameTextField = new TextField<String>(
				"suchenNachname", Model.of(""));

		Form<?> suchenNutzerForm = new Form<Void>("suchenNutzerForm") {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onError() {
				System.out.println("onError bei SuchenNutzerForm in UserHP");
			}

			@Override
			protected void onSubmit() {
				PageParameters pageParameters = new PageParameters();

				String inputKennung = kennungTextField.getModelObject();
				String inputVorname = vornameTextField.getModelObject();
				String inputNachname = nachnameTextField.getModelObject();

				if (inputKennung != null) {
					pageParameters.add(KENNUNG_PARAMETER, inputKennung);
				}
				if (inputVorname != null) {
					pageParameters.add(VORNAME_PARAMETER, inputVorname);
				}
				if (inputNachname != null) {
					pageParameters.add(NACHNAME_PARAMETER, inputNachname);
				}
				setResponsePage(UserHp.class, pageParameters);

			}
		};
		add(suchenNutzerForm);
		suchenNutzerForm.add(kennungTextField);
		suchenNutzerForm.add(vornameTextField);
		suchenNutzerForm.add(nachnameTextField);
	}
	
	private void createUserTable(PageParameters parameters){
        List <User> foundUsers = searchUser(parameters);
        
        ListDataProvider<User> listDataProviderUser = new ListDataProvider<User>(foundUsers);
        setTableHeadFoundUsers(foundUsers.isEmpty());
        showErrorMessage(foundUsers.isEmpty());
		DataView<User> dataViewUser = new DataView<User>("rowsUser", listDataProviderUser) {
			private static final long serialVersionUID = 1L;

			@SuppressWarnings("unchecked")
			@Override
			protected void populateItem(Item<User> item) {
				User user = item.getModelObject();
				item.add(new Label("kennungGefunden",user.getKennung()));
                item.add(new Label("vornameGefunden",user.getFirstName()));
                item.add(new Label("nachnameGefunden",user.getLastName()));
                item.add(new Label("emailGefunden",user.geteMail()));
                item.add(new Label("telefonGefunden", user.getTelefon()));
                ActionPanelHistorie panelHistorie = new ActionPanelHistorie("historie", true, user.getId());
	            item.add(panelHistorie);
	            ActionPanelBerechtigungSetzen panelBerechtigung = new ActionPanelBerechtigungSetzen("berechtigungSetzen",new UserModel(new Model<User>(item.getModelObject()),UserModelType.USER));
	            item.add(panelBerechtigung);
	            
	            boolean isUSerOnList = checkIfUserAlreadyOnList(user);
	            ActionPanelToGrey panelToGrey = new ActionPanelToGrey("toGrey", new UserModel(new Model<User>(item.getModelObject()),UserModelType.USER));
	            panelToGrey.setVisible(isUSerOnList);
	            graueListeFoundUSers.setVisible(isUSerOnList);
	            item.add(panelToGrey);
	            if(access.isLoggedin()){
				     if(!access.hasAccessTo("edit_Greylist")){
				    	 panelToGrey.setVisible(false);
				     }
				     if(!access.hasAccessTo("is_admin") ){
				    	 panelBerechtigung.setVisible(false);
				     } else if(!access.hasAccessTo("manage_own_lab")){
				    	 panelBerechtigung.setVisible(false);
				     }
			     }
			}
		 }; 
		 dataViewUser.setItemsPerPage(10);
	     add(dataViewUser);
	    
	}
	private void showErrorMessage(boolean isEmpty) {
		Label error = new Label("error",  Model.of("Es wurde leider kein Nutzer gefunden."));
		if(!isEmpty){
			error.setVisible(false);
		}
		add(error);		
	}

	private void setTableHeadFoundUsers (Boolean isEmpty){	
		Label kennungFoundUsers = new Label("kennungFoundUsers",  Model.of("HAW-Kennung"));
		Label vornameFoundUsersy = new Label("vornameFoundUsers", Model.of("Vorname"));
		Label nachnameFoundUsers = new Label("nachnameFoundUsers",  Model.of("Nachname"));
		Label emailFoundUsers = new Label("emailFoundUsers",  Model.of("E-Mail"));
		Label telefonFoundUsers = new Label("telefonFoundUsers",  Model.of("Telefon"));
		Label historieFoundUsers = new Label("historieFoundUsers",Model.of("Verleihhistorie"));
		Label berechtigungFoundUsers =  new Label("berechtigungFoundUsers",Model.of("Berechtigung"));
		graueListeFoundUSers = new Label("graueListeFoundUsers",Model.of("Graue Liste"));
		
		if(isEmpty){
			kennungFoundUsers.setVisible(false);
			vornameFoundUsersy.setVisible(false);
			nachnameFoundUsers.setVisible(false);
			emailFoundUsers.setVisible(false);
			telefonFoundUsers.setVisible(false);
			historieFoundUsers.setVisible(false);
			berechtigungFoundUsers.setVisible(false);
			graueListeFoundUSers.setVisible(false);
		}
		 if(!access.hasAccessTo("edit_Greylist")){
			 graueListeFoundUSers.setVisible(false);
	     }
	     if(!access.hasAccessTo("manage_own_lab")){
	    	 berechtigungFoundUsers.setVisible(false);
	     }
	     if(!access.hasAccessTo("is_admin")){
	    	 berechtigungFoundUsers.setVisible(false);
	     }

		add(kennungFoundUsers);
		add(vornameFoundUsersy);
		add(nachnameFoundUsers);
		add(emailFoundUsers);
		add(telefonFoundUsers);
		add(historieFoundUsers);
		add(berechtigungFoundUsers);
		add(graueListeFoundUSers);
	}
	private List<User> searchUser(PageParameters parameters){
		String kennung = parameters.get(KENNUNG_PARAMETER).toString();
		String vorname = parameters.get(VORNAME_PARAMETER).toString();
		String nachname = parameters.get(NACHNAME_PARAMETER).toString();		
		System.out.println("kennung: " + kennung + " ; vorname: " + vorname + "; nachname: " + nachname);
		
		BasicDao<User> userDao = new BasicDao<User>(User.class);
		Properties props = new Properties();
		if(kennung != null){
			props.put("kennung", kennung);
		}
		if(vorname != null){
			props.put("firstName", vorname);
		}
		if(nachname != null){
			props.put("lastName", nachname);
		}
		if(!props.isEmpty()){
			List<User> foundUsers = userDao.searchByProperties(props);
			return foundUsers;
		}		
		return new ArrayList<User>();
	}
	private void createButtons(){
		Form formButtons = new Form("formButtons");
		Button berechtigungsButton = new Button("berechtigung") {
			private static final long serialVersionUID = 1L;

			public void onSubmit(){          	
			    this.setResponsePage(BerechtigungsVerwaltung.class);
			}
			public void onError(){
				System.out.println("Error bei berechtigungsButton ");
			}
		};
		
		Button graueListeButton = new Button("graueListe") {
			private static final long serialVersionUID = 1L;

			public void onSubmit(){          	
			    this.setResponsePage(GraueListe.class);
			}
			public void onError(){
				System.out.println("Error bei graueListeButton ");
			}
		};
		
		Button schwarzeListeButton = new Button("schwarzeListe") {
			private static final long serialVersionUID = 1L;

			public void onSubmit(){          	
			    this.setResponsePage(SchwarzeListe.class);
			}
			public void onError(){
				System.out.println("Error bei schwarzeListeButton ");
			}
		};
		add(formButtons);
		graueListeButton.setVisible(false);
		schwarzeListeButton.setVisible(false);
		berechtigungsButton.setVisible(false);
		
		formButtons.add(berechtigungsButton);
		formButtons.add(graueListeButton);
		formButtons.add(schwarzeListeButton);
		if(access.isLoggedin()){
			if(access.hasAccessTo("view_Greylist")){
				graueListeButton.setVisible(true);
		    }
			if(access.hasAccessTo("is_admin") ){
		    	graueListeButton.setVisible(true);
		    } 
			if(access.hasAccessTo("manage_own_lab")){
		    	graueListeButton.setVisible(true);
		    }
			
		     if(access.hasAccessTo("view_Blacklist")){
		    	 schwarzeListeButton.setVisible(true);
		     }
		     if(access.hasAccessTo("is_admin") ){
		    	 schwarzeListeButton.setVisible(true);
			 }
		     if(access.hasAccessTo("manage_own_lab")){
			    	schwarzeListeButton.setVisible(true);
			 }
		     
		     if(access.hasAccessTo("is_admin")){
		    	 berechtigungsButton.setVisible(true);
		     }
		     if(access.hasAccessTo("manage_own_lab")){
		    	 berechtigungsButton.setVisible(true);
		     }
	    }
		
	}
	private boolean checkIfUserAlreadyOnList(User user){
		SanktionsListeDao sanktionsListeDao = new SanktionsListeDao();
		List<SanktionsListe> sanktionsListe = sanktionsListeDao.getAllWithUsers();
		
		if(!sanktionsListe.isEmpty()){
			for(SanktionsListe liste: sanktionsListe){
				User userSanktionsListe = liste.getUserID();
				if(user.getId() == userSanktionsListe.getId()){
					return false;
				}
			}
		}					
		return true;
	}
}
