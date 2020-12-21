package haw.Ausleihe;

import haw.Ausleihe.ActionPanels.ActionPanelGreyToBlack;
import haw.Ausleihe.ActionPanels.ActionPanelLoeschen;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RadioChoice;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import Database.Entities.Artikel;
import Database.Entities.SanktionsListe;
import Database.Entities.Wartung;
import Models.SanktionsListeModel;
import Models.SanktionsListeModel.SanktionsListeModelType;
import dao.ArtikelDao;
import dao.WartungsDao;
import dco.WartungDco;
import enums.WartungsEnum;

public class WartungHP extends WebPage{
	private static final long serialVersionUID = 1L;
	private static final List<String> WARTUNGSART = Arrays.asList(new String[] {WartungsEnum.Pruefung.toString(),WartungsEnum.Wartung.toString()}); 
	private String selected = "Wartung";
	
	public WartungHP(PageParameters parameters){
		super(parameters);	
		add(new HeaderPanel("headerPanel"));
		add(new FooterPanel("footerPanel"));
		add(new NavigationPanel("navigationPanel"));
		
		long artikelID = parameters.get("artikelID").toLong();
		Artikel artikel = loadArtikel(artikelID);
		createAddForm(artikel);
		createTableWartung();
	}
	private Artikel loadArtikel(long artikelID){
		ArtikelDao artikelDao = new ArtikelDao();
		return artikelDao.findById(artikelID);
	}
	
	private void createAddForm(final Artikel artikel){
		Label labelArtikelID = new Label("labelArtikelID",Model.of("Artikel ID"));
		Label labelArtikelIDValue = new Label("labelArtikelIDValue",Model.of(artikel.getId()));
		Label labelMaintenanceDate = new Label("labelMaintenanceDate", Model.of("Datum"));
		final TextField<String> textFieldMaintenanceDate = new TextField<String>("textFieldMaintenanceDate",Model.of(""));
		Label labelWartungsArt = new Label("labelWartungsArt",Model.of("Wartungsart: "));
		final RadioChoice<String> wartungsArt = new RadioChoice<String>("wartungsArt", new PropertyModel<String>(this, "selected"), WARTUNGSART);
		Label labelBeschreibung = new Label("labelBeschreibung",Model.of("Beschreibung"));
		final TextArea<String> textAreaBeschreibung = new TextArea<String>("textAreaBeschreibung",Model.of(""));
		
		Form<?> addFormWartung = new Form<Void> ("addFormWartung"){
			private static final long serialVersionUID = 1L;
			@Override
			protected void onError() {
				System.out.println("onError bei SuchenNutzerForm");
			}
			
			@Override
			protected void onSubmit() {
				PageParameters parametersNeu = new PageParameters();
				WartungDco wartungsDco = new WartungDco();
				Wartung wartung = new Wartung();
				
				String maintenanceDateInput = textFieldMaintenanceDate.getModelObject();
				String beschreibungInput = textAreaBeschreibung.getModelObject();
				String wartungsArtInput = wartungsArt.getModelObject();
				
				wartung.setArtikel(artikel);
				wartung.setMaintenanceDate(maintenanceDateInput);
				wartung.setDecs(beschreibungInput);
				if(wartungsArtInput.equalsIgnoreCase("Wartung"))
					wartung.setStatus(WartungsEnum.Wartung);
				else{
					wartung.setStatus(WartungsEnum.Pruefung);
				}
				wartungsDco.createWartung(wartung);
				
				parametersNeu.add("artikelID", artikel.getId());
				setResponsePage(WartungHP.class, parametersNeu);
			}
		};
		add(addFormWartung);
		addFormWartung.add(labelArtikelID);
		addFormWartung.add(labelArtikelIDValue);
		addFormWartung.add(labelMaintenanceDate);
		addFormWartung.add(textFieldMaintenanceDate);
		addFormWartung.add(labelWartungsArt);
		addFormWartung.add(wartungsArt);
		addFormWartung.add(labelBeschreibung);
		addFormWartung.add(textAreaBeschreibung);
	}
	private void createTableWartung(){
		WartungsDao wartungsDao = new WartungsDao();
		List<Wartung>wartungen = wartungsDao.getAll();
		createTableWartungHead(wartungen.isEmpty());
		showErrorMessage(wartungen.isEmpty());	
		
		ListDataProvider<Wartung> listDtaProvider = new ListDataProvider<Wartung>(wartungen);
		DataView<Wartung> dataView = new DataView<Wartung>("rows", listDtaProvider) {
			private static final long serialVersionUID = 1L;

			@Override  
			 protected void populateItem(Item<Wartung> item) {
	                Wartung wartung = item.getModelObject();
	                item.add(new Label("artikelID",wartung.getArtikel().getId()));
	                item.add(new Label("datum",wartung.getMaintenanceDate().substring(0, 10)));
	                item.add(new MultiLineLabel("wartungsTyp",wartung.getStatus().toString()));
	                item.add(new Label("beschreibung",wartung.getDecs()));
	            }
	        };
	        dataView.setItemsPerPage(25);
	        add(dataView);
	}
	private void showErrorMessage(boolean isEmpty) {
		Label error = new Label("error",  Model.of("Es wurde bisher keine Wartung durchgeführt."));
		if(!isEmpty){
			error.setVisible(false);
		}
		add(error);		
	}
	
	private void createTableWartungHead(boolean isEmpty){
		Label artikelIDHead = new Label("artikelIDHead", Model.of("Artikel"));
		Label datumHead = new Label("datumHead", Model.of("Datum"));
		Label wartungsTypHead = new Label("wartungsTypHead", Model.of("WartungsTyp"));
		Label beschreibungHead = new Label("beschreibungHead",Model.of("Beschreibung"));
		
		if(isEmpty){
			artikelIDHead.setVisible(false);
			datumHead.setVisible(false);
			wartungsTypHead.setVisible(false);
			beschreibungHead.setVisible(false);
		}
		add(artikelIDHead);
		add(datumHead);
		add(wartungsTypHead);
		add(beschreibungHead);
	}
}
