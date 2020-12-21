package haw.Ausleihe;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;


//import Database.HibernateHelper;
import dco.ArtikelDco;

public class produktNeu extends WebPage {
	private static final long serialVersionUID = 1L;

	public produktNeu(final PageParameters parameters) {
		super(parameters);
		
	//	MySession.get().setLoggedin("3");
		
		
		final TextField<String> hersteller = new TextField<String>("herstellerersteller", Model.of(""));		
		final TextField<String> typ = new TextField<String>("typ", Model.of(""));
		final TextField<String> art = new TextField<String>("art", Model.of(""));
		final TextField<String> kategorie = new TextField<String>("kategorie", Model.of(""));
		final TextField<String> verzeichnisbaum = new TextField<String>("verzeichnisbaum", Model.of(""));
		final TextField<String> kaufdatum = new TextField<String>("kaufdatum", Model.of(""));
		final TextField<String> preis = new TextField<String>("preis", Model.of(""));
		final TextField<String> lieferant = new TextField<String>("lieferant", Model.of(""));
		final TextField<String> volNr = new TextField<String>("volNr", Model.of(""));
		final TextField<String> bemerkung = new TextField<String>("bemerkung", Model.of(""));
		final TextField<String> zustand = new TextField<String>("zustand", Model.of(""));
		final TextField<String> name = new TextField<String>("name", Model.of(""));
		final TextField<String> menge = new TextField<String>("menge", Model.of(""));
		final TextField<String> elektrischeLeistung = new TextField<String>("elektrischeLeistung", Model.of(""));
		final TextField<String> packmass = new TextField<String>("packmass", Model.of(""));
		final TextField<String> gewicht = new TextField<String>("gewicht", Model.of(""));
		final TextField<String> leuchtmitteltyp = new TextField<String>("leuchtmitteltyp", Model.of(""));
		final TextField<String> akkutyp = new TextField<String>("akkutyp", Model.of(""));
		final TextField<String> batterietyp = new TextField<String>("batterietyp", Model.of(""));
		final TextField<String> batterieanzahl = new TextField<String>("batterieanzahl", Model.of(""));
		final TextField<String> speichermedium = new TextField<String>("speichermedium", Model.of(""));
		final TextField<String> status = new TextField<String>("status", Model.of(""));
		final TextField<String> seriennummer = new TextField<String>("seriennummer", Model.of(""));
		final TextField<String> servicehotline = new TextField<String>("servicehotline", Model.of(""));
		final TextField<String> garantieZeitraum = new TextField<String>("garantieZeitraum", Model.of(""));
		final TextField<String> ablaufGarantie = new TextField<String>("ablaufGarantie", Model.of(""));
		final TextField<String> verpackung = new TextField<String>("verpackung", Model.of(""));
		
		add(new HeaderPanel("headerPanel"));
		add(new FooterPanel("footerPanel"));
		add(new NavigationPanel("navigationPanel"));
		
		Form<?> ProduktNeuForm = new Form<Void> ("ProduktNeuForm"){
			private static final long serialVersionUID = 1L;

			@Override
			protected void onError() {
				System.out.println("Produkt Neu Test");
			}
			
			@Override
			protected void onSubmit() {
				//HibernateHelper hibernate = new HibernateHelper();
				ArtikelDco artikelDco = new ArtikelDco();
					
				final String herstellerValue = hersteller.getModelObject();
				final String typValue = typ.getModelObject();
				final String artValue = art.getModelObject();
				final String kategorieValue = kategorie.getModelObject();
				final String verzeichnisbaumValue = verzeichnisbaum.getModelObject();
				final String kaufdatumValue = kaufdatum.getModelObject();
				final String preisValue = preis.getModelObject();
				final String lieferantValue = lieferant.getModelObject();
				final String volNrValue = volNr.getModelObject();
				final String bemerkungValue = bemerkung.getModelObject();
				final String zustandValue = zustand.getModelObject();
				final String nameValue = name.getModelObject();
				final String mengeValue = menge.getModelObject();
				final String elektrischeLeistungValue = elektrischeLeistung.getModelObject();
				final String packmassValue = packmass.getModelObject();
				final String gewichtValue = gewicht.getModelObject();
				final String leuchtmitteltypValue = leuchtmitteltyp.getModelObject();
				final String akkutypValue = akkutyp.getModelObject();
				final String batterietypValue = batterietyp.getModelObject();
				final String batterieanzahlValue = batterieanzahl.getModelObject();
				final String speichermediumValue = speichermedium.getModelObject();
				final String statusValue = status.getModelObject();
				final String seriennummerValue = seriennummer.getModelObject();
				final String servicehotlineValue = servicehotline.getModelObject();
				final String garantieZeitraumValue = garantieZeitraum.getModelObject();
				final String ablaufGarantieValue = ablaufGarantie.getModelObject();
				final String verpackungValue = verpackung.getModelObject();
				
//				if(nameValue.equalsIgnoreCase("")){
//					System.out.println("keinen Namen");
//				}
				
				artikelDco.createArtikel(herstellerValue, typValue, artValue, kategorieValue, verzeichnisbaumValue, kaufdatumValue, preisValue, lieferantValue, volNrValue, bemerkungValue, zustandValue, nameValue, mengeValue, elektrischeLeistungValue, packmassValue, gewichtValue, leuchtmitteltypValue, akkutypValue, batterietypValue, batterieanzahlValue, speichermediumValue, statusValue, seriennummerValue, servicehotlineValue, garantieZeitraumValue, ablaufGarantieValue, verpackungValue);
				//hibernate.addArtikel(herstellerValue, typValue, artValue, kategorieValue, verzeichnisbaumValue, kaufdatumValue, 15, lieferantValue, volNrValue, bemerkungValue, zustandValue, fotoValue, 15, elektrischeLeistungValue, packmassValue, 15, leuchtmitteltypValue, akkutypValue, batterietypValue, batterieanzahlValue, speichermediumValue, statusValue, seriennummerValue, servicehotlineValue, garantieZeitraumValue, ablaufGarantieValue, verpackungValue);
				System.out.println("Neues produckt in DB");
			}
			
		};
		add(ProduktNeuForm);
		ProduktNeuForm.add(hersteller);
		ProduktNeuForm.add(typ);
		ProduktNeuForm.add(art);
		ProduktNeuForm.add(kategorie);
		ProduktNeuForm.add(verzeichnisbaum);
		ProduktNeuForm.add(kaufdatum);
		ProduktNeuForm.add(preis);
		ProduktNeuForm.add(lieferant);
		ProduktNeuForm.add(volNr);
		ProduktNeuForm.add(name);
		ProduktNeuForm.add(bemerkung);
		ProduktNeuForm.add(zustand);
		ProduktNeuForm.add(menge);
		ProduktNeuForm.add(elektrischeLeistung);
		ProduktNeuForm.add(packmass);
		ProduktNeuForm.add(gewicht);
		ProduktNeuForm.add(leuchtmitteltyp);
		ProduktNeuForm.add(akkutyp);
		ProduktNeuForm.add(batterietyp);
		ProduktNeuForm.add(batterieanzahl);
		ProduktNeuForm.add(speichermedium);
		ProduktNeuForm.add(status);
		ProduktNeuForm.add(seriennummer);
		ProduktNeuForm.add(servicehotline);
		ProduktNeuForm.add(garantieZeitraum);
		ProduktNeuForm.add(ablaufGarantie);
		ProduktNeuForm.add(verpackung);
		
		
		
    }
}
