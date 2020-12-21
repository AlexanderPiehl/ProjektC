package haw.Ausleihe;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.hibernate.Session;

import Tools.LDAP;
import Database.HibernateHelper;
import Database.Entities.User;


public class Login extends WebPage {
	private static final long serialVersionUID = 1L;
	
	
	public Login(final PageParameters parameters) {
		super(parameters);
		
		
		final TextField<String> hawKennung = new TextField<String>("hawKennung",
				Model.of(""));
//		
		
		hawKennung.setRequired(true);
		//hawKennung.add(new UserValidator());
		final PasswordTextField passwort = new PasswordTextField("passwort", Model.of(""));
		passwort.setRequired(true);
		
		
		
		Form<?> login = new Form<Void> ("login")		
		{
			@Override
			protected void onError(){
				//ToDo: Hier sollte eine Meldung erscheinen, dass das Login nicht möglich war
				//System.out.println("testest_ErrorTest");
			}
			@Override
			protected void onSubmit() {
				PageParameters pageParameters = new PageParameters();
				
				HibernateHelper hibernate = new HibernateHelper();
//				hibernate.addUser("abb123", "test", "Henrik", "Oelze", "test«test.de");
				LDAP ldap = new LDAP();
				final String usernameValue = hawKennung.getModelObject();
				final String passwordValue = passwort.getModelObject();
				if(ldap.authentify(usernameValue, passwordValue)){
					MySession.get().setLoggedin(usernameValue);
					
					if(hibernate.checkIfUserExists(usernameValue)){
						
						
						setResponsePage(HomePage.class);
					}else{
						//pageParameters.add("HawKennung", usernameValue); //sende kennung zur kommenden seite
						//ToDO. Seite anzeigen, wo der USer seine Daten eingeben muss
						setResponsePage(Registrierung.class);
					}				
				}else{
					//ToDo. Auch hier sollte eine Fehlermeldung kommen, MSG wird von der LDAP-Klasse bereitgestellt
					//System.out.println("pech");
				}				
	
				 
				
				
			}
		};
		add(login);
		login.add(hawKennung);
		login.add(passwort);
		
	}
}