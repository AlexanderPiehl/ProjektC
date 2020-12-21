package haw.Ausleihe;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;

import Database.Entities.User;
import Database.Entities.UserGroups;
import dao.BasicDao;
import dao.UserDao;
import dao.UserGroupsDao;
import dco.UserDco;

public class Registrierung  extends WebPage {
	private static final long serialVersionUID = 1L;

	public Registrierung(final PageParameters parameters) {
		super(parameters);
		
		final TextField<String> firstname = new TextField<String>("firstname", Model.of(""));	
		final TextField<String> lastname = new TextField<String>("lastname", Model.of(""));	
		final TextField<String> email = new TextField<String>("email", Model.of(""));	
		final TextField<String> phone = new TextField<String>("phone", Model.of(""));	
				
		
		Form<?> UserNeuForm = new Form<Void> ("UserNeuForm"){
			private static final long serialVersionUID = 1L;
			
			final String kennung = MySession.get().getLoggedinID();
			@Override
			protected void onError() {
				//System.out.println("Produkt Neu Test");
			}
			
			@Override
			protected void onSubmit() {
				final String firstnameValue = firstname.getModelObject();
				final String lastnameValue = lastname.getModelObject();
				final String emailValue = email.getModelObject();
				final String phoneValue = phone.getModelObject();
				
				
				// Nur drin damit kein Fehler geworfen wird!
				List<UserGroups> userGroups = new ArrayList<UserGroups>();
				UserGroupsDao userGroupsDao = new UserGroupsDao();
				UserGroups userGroup = userGroupsDao.findById(4);
				userGroups.add(userGroup);
				
				UserDco userDco = new UserDco();
				userDco.createUser(kennung, firstnameValue, lastnameValue, emailValue, phoneValue, userGroups );
				//userDco.createUser(kennung, firstName, lastName, eMail, telefon, memberOfGroups);
				
				setResponsePage(HomePage.class);
			}
			
		};
		add(UserNeuForm);
		UserNeuForm.add(firstname);
		UserNeuForm.add(lastname);
		UserNeuForm.add(email);
		UserNeuForm.add(phone);
    }
	
	private List<User> searchUser(){
		String kennung = MySession.get().getLoggedinID();
		BasicDao<User> userDao = new BasicDao<User>(User.class);
		Properties props = new Properties();
		if(kennung != null){
			props.put("kennung", kennung);
		}
		if(!props.isEmpty()){
			List<User> foundUsers = userDao.searchByProperties(props);
			return foundUsers;
		}	
		return null;
	}
}
