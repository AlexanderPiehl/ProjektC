package haw.Ausleihe;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import Tools.Access;
import Database.Entities.Artikel;
import Database.Entities.User;
import dao.BasicDao;

public class HeaderPanel  extends Panel {
	private static final long serialVersionUID = 1L;
	private static final Access access = new Access();
	private String inputSuche = null;

	public HeaderPanel(String id) { 
		super(id);
		
		Form<?> form = new Form<Void>("form");
		
		
		final TextField<String> sucheTextField = new TextField<String>("sucheTextField", Model.of(""));
		form.add(sucheTextField);
		
		Button suche = new Button("suche") {
			public void onSubmit(){
				inputSuche = sucheTextField.getModelObject();
				searchArtikel();
				MySession.get().setWirdGesucht(true);
				System.out.print(MySession.get().getListSuche());
				setResponsePage(HomePage.class);
			}
		};
		form.setDefaultButton(suche);
		//suche.setDefaultFormProcessing(true);
		form.add(suche);
		
		Button reservierung = new Button("reservierung") {
			public void onSubmit(){
				setResponsePage(ReservierungHP.class);
			}
		};
		reservierung.setDefaultFormProcessing(false);
		form.add(reservierung);
	
		Button buchung = new Button("buchung") {
			public void onSubmit(){
				setResponsePage(Buchungen.class);
			}
		};
		buchung.setDefaultFormProcessing(false);
		form.add(buchung);
		
		Button produktNeu = new Button("produktNeu") {
			public void onSubmit(){
				setResponsePage(produktNeu.class);
			}
		};
		produktNeu.setDefaultFormProcessing(false);
		produktNeu.setVisible(false);
		form.add(produktNeu);
		
		Button userVerwaltung = new Button("userVerwaltung") {
			public void onSubmit(){
				setResponsePage(UserHp.class);
			}
		};
		userVerwaltung.setDefaultFormProcessing(false);
		userVerwaltung.setVisible(false);
		form.add(userVerwaltung);
		
		Button Login = new Button("Login") {
			private static final long serialVersionUID = 1L;
			public void onSubmit(){
				MySession.get().removeLogin();
				MySession.get().invalidate();
				setResponsePage(Login.class);
			}
		};
		Login.setDefaultFormProcessing(false);
		form.add(Login);
		
		
		//link für Logo zur Startseite
				form.add(new Link("StartSeite") {
					@Override public void onClick() {
					//PageParameters pageParameters = new PageParameters(); pageParameters.add("foo", "foo"); pageParameters.add("bar", "bar");
					setResponsePage(HomePage.class); }
				});
				
				//link für Logo zum Warenkorb
				form.add(new Link("warenkorb") {
					@Override public void onClick() {
					//PageParameters pageParameters = new PageParameters(); pageParameters.add("foo", "foo"); pageParameters.add("bar", "bar");
					setResponsePage(warenkorb.class); }
				});
			        
		add(form);
		
		 if(access.isLoggedin()){            	
         	if(access.hasAccessTo("article_create") || access.hasAccessTo("is_admin")	){
         		produktNeu.setVisible(true);
         	}
         	if(access.hasAccessTo("view_Blacklist") || access.hasAccessTo("edit_Blacklist") || access.hasAccessTo("view_Greylist") || access.hasAccessTo("edit_Greylist")|| access.hasAccessTo("is_admin")) {
         		userVerwaltung.setVisible(true);
         	}
 		}
		
	
	}
	
	private List<Artikel> searchArtikel(){
		List<Artikel> foundHersteller= null; List<Artikel> foundTyp = null;	List<Artikel> foundName = null;	List<Artikel> foundKategorie = null;
		List<Artikel> foundKaufdatum = null; List<Artikel> foundPreis = null; List<Artikel> foundLieferant = null; List<Artikel> foundVolNr = null;
		List<Artikel> foundZustand = null; List<Artikel> foundMenge = null; List<Artikel> foundElLeistung = null; List<Artikel> foundpackmass = null;
		List<Artikel> foundGewicht = null; List<Artikel> foundLeuchtmitteltyp= null; List<Artikel> foundAkkutyp = null; List<Artikel> foundBatterityp = null;
		List<Artikel> foundBatteriAnzahl = null; List<Artikel> foundSpeichermedium = null; List<Artikel> foundSeriennummer = null; List<Artikel> foundGgarantieZeit = null;
		List<Artikel> foundAblaufGarantie = null; List<Artikel> foundVerpackung = null; List<Artikel> foundArt = null;
		
		BasicDao<Artikel> artikelDao = new BasicDao<Artikel>(Artikel.class);
		Properties props = new Properties(); Properties props2 = new Properties(); 	Properties props3 = new Properties(); 	Properties props4 = new Properties();
		Properties props5 = new Properties(); 	Properties props6 = new Properties(); 	Properties props7 = new Properties(); 	Properties props8 = new Properties();
		Properties props9 = new Properties(); Properties props10 = new Properties(); 	Properties props11 = new Properties(); 	Properties props12 = new Properties();
		Properties props13 = new Properties(); 	Properties props14 = new Properties(); 	Properties props15 = new Properties(); 	Properties props16 = new Properties();
		Properties props17 = new Properties(); Properties props18 = new Properties(); 	Properties props19 = new Properties(); 	Properties props20 = new Properties();
		Properties props21 = new Properties(); 	Properties props22 = new Properties(); Properties props23 = new Properties(); 	 	
		
		//List<Artikel> foundArtikel = null;
		if(inputSuche != null){
			props.put("hersteller", inputSuche);
			props2.put("typ", inputSuche);
			props3.put("name", inputSuche);
			props4.put("kategorie", inputSuche);
			props5.put("kaufdatum", inputSuche);
			props6.put("preis", inputSuche);
			props7.put("lieferant", inputSuche);
			props8.put("volNr", inputSuche);
			props9.put("zustand", inputSuche);
			props10.put("menge", inputSuche);
			props11.put("elektrischeLeistung", inputSuche);
			props12.put("packmass", inputSuche);
			props13.put("gewicht", inputSuche);
			props14.put("leuchtmitteltyp", inputSuche);
			props15.put("akkutyp", inputSuche);
			props16.put("batterientyp", inputSuche);
			props17.put("batterienAnzahl", inputSuche);
			props18.put("speichermedium", inputSuche);
			props19.put("seriennummer", inputSuche);
			props20.put("garantieZeit", inputSuche);
			props21.put("ablaufGarantie", inputSuche);
			props22.put("verpackung", inputSuche);
			props23.put("art", inputSuche);
		}		
		if(!props.isEmpty()){
			foundHersteller= artikelDao.searchByProperties(props);
			foundTyp = artikelDao.searchByProperties(props2);
			foundName= artikelDao.searchByProperties(props3);
			foundKategorie = artikelDao.searchByProperties(props4);
			foundKaufdatum= artikelDao.searchByProperties(props5);
			foundPreis = artikelDao.searchByProperties(props6);
			foundLieferant= artikelDao.searchByProperties(props7);
			foundVolNr = artikelDao.searchByProperties(props8);
			foundZustand= artikelDao.searchByProperties(props9);
			foundMenge = artikelDao.searchByProperties(props10);
			foundElLeistung= artikelDao.searchByProperties(props11);
			foundpackmass = artikelDao.searchByProperties(props12);
			foundGewicht= artikelDao.searchByProperties(props13);
			foundLeuchtmitteltyp = artikelDao.searchByProperties(props14);
			foundAkkutyp= artikelDao.searchByProperties(props15);
			foundBatterityp = artikelDao.searchByProperties(props16);
			foundBatteriAnzahl= artikelDao.searchByProperties(props17);
			foundSpeichermedium = artikelDao.searchByProperties(props18);
			foundSeriennummer= artikelDao.searchByProperties(props19);
			foundGgarantieZeit = artikelDao.searchByProperties(props20);
			foundAblaufGarantie= artikelDao.searchByProperties(props21);
			foundVerpackung = artikelDao.searchByProperties(props22);
			foundArt = artikelDao.searchByProperties(props23);
			List<Artikel> foundArtikel = new ArrayList<Artikel>();//artikelDao.searchByProperties(props);
			foundArtikel.addAll(foundHersteller);
			foundArtikel.addAll(foundTyp);
			foundArtikel.addAll(foundName);
			foundArtikel.addAll(foundKategorie);
			foundArtikel.addAll(foundKaufdatum);
			foundArtikel.addAll(foundPreis);
			foundArtikel.addAll(foundLieferant);
			foundArtikel.addAll(foundVolNr);
			foundArtikel.addAll(foundZustand);
			foundArtikel.addAll(foundMenge);
			foundArtikel.addAll(foundElLeistung);
			foundArtikel.addAll(foundpackmass);
			foundArtikel.addAll(foundGewicht);
			foundArtikel.addAll(foundLeuchtmitteltyp);
			foundArtikel.addAll(foundAkkutyp);
			foundArtikel.addAll(foundBatterityp);
			foundArtikel.addAll(foundBatteriAnzahl);
			foundArtikel.addAll(foundSpeichermedium);
			foundArtikel.addAll(foundSeriennummer);
			foundArtikel.addAll(foundGgarantieZeit);
			foundArtikel.addAll(foundAblaufGarantie);
			foundArtikel.addAll(foundVerpackung);
			foundArtikel.addAll(foundArt);
			
			MySession.get().setListSuche(foundArtikel);
			System.out.println(foundArtikel);
			return foundArtikel;
		}		
		return new ArrayList<Artikel>();
	}

}
