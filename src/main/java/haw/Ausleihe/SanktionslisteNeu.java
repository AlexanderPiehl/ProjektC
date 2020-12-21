package haw.Ausleihe;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import Database.Entities.SanktionsListe;
import Database.Entities.User;
import dao.BasicDao;
import dao.SanktionsListeDao;
import dco.SanktionsListeDco;
import enums.SanktionenEnum;

public class SanktionslisteNeu extends WebPage{
	private static final long serialVersionUID = 1L;
	
	public SanktionslisteNeu (final PageParameters parameters){
		super(parameters);
		
		add(new HeaderPanel("headerPanel"));
		add(new FooterPanel("footerPanel"));
		add(new NavigationPanel("navigationPanel"));
		
		Boolean ifGrey = true;
		Long sanktionsListeID = 0l; 
		String typ = parameters.get("typ").toString();
		if(typ.equalsIgnoreCase("black")){
			ifGrey = false;
			sanktionsListeID = parameters.get("sanktionsListeID").toLong();
		}
		
		long userID = parameters.get("userID").toLong();
		if(userID > 0){
			User user = getUser(userID);
			createForm(user,ifGrey, sanktionsListeID);
		}
	}
	
	private User getUser(long userId){
		BasicDao<User> userDao = new BasicDao<User>(User.class);
		return userDao.findById(userId);
	}
	
	private void createForm(final User user, final boolean ifGrey, final long sanktionsListeID){
		Label labelKennung = new Label("Kennung", "Kennung: ");
		Label labelKennungInput = new Label("KennungInput", user.getKennung());
		Label labelVorname = new Label("Vorname", "Vorname: ");
		Label labelVornameInput = new Label("labelVornameInput", user.getFirstName());
		Label labelNachname = new Label("Nachname", "Nachname");
		Label labelNachnameInput = new Label("labelNachnameInput", user.getLastName());
		Label labelBegruendung = new Label("begruendungLabel", "Begründung: ");
		final TextArea<String> textAreaBegruendung = new TextArea<String>("textAreaBegruendung", Model.of(""));
		
		Form<?> bestaetigungForm = new Form<Void> ("bestaetigungForm"){
			private static final long serialVersionUID = 1L;
			@Override
			protected void onError() {
				System.out.println("onError bei bestaetigungForm");
			}
			
			@Override
			protected void onSubmit() {
				String inputBegruendung = textAreaBegruendung.getModelObject();
				SanktionsListeDao sanktionsListeDao = new SanktionsListeDao();
				
        		if(ifGrey){
    				SanktionsListe liste = new SanktionsListe();
            		liste.setUserID(user);
            		liste.setComments(inputBegruendung);
        			liste.setStatus(SanktionenEnum.Graue);
        			sanktionsListeDao.create(liste);
            		this.setResponsePage(GraueListe.class);
        		}else{
        			SanktionsListe liste = sanktionsListeDao.findById(sanktionsListeID);
        			liste.setComments(inputBegruendung);
        			SanktionsListeDco sanktionsListeDco = new SanktionsListeDco();
        			sanktionsListeDco.userFromGreyToBlack(liste);
            		this.setResponsePage(SchwarzeListe.class);
        		}        		        		
			}	
		};
		add(bestaetigungForm);
		bestaetigungForm.add(labelKennung);
		bestaetigungForm.add(labelKennungInput);
		bestaetigungForm.add(labelVorname);
		bestaetigungForm.add(labelVornameInput);
		bestaetigungForm.add(labelNachname);
		bestaetigungForm.add(labelNachnameInput);
		bestaetigungForm.add(labelBegruendung);	
		bestaetigungForm.add(textAreaBegruendung);
	}
}
