package haw.Ausleihe;

import haw.Ausleihe.ActionPanels.ActionPanelLoeschenArtikelReservierung;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;

import dao.ReservierungDao;
import Database.Entities.Artikel;
import Database.Entities.Reservierung;
import Models.ArtikelModel;
import Models.ArtikelModel.ArtikelModelType;
import Tools.Access;

public class ReservierungBearbeiten extends WebPage{
	private static final long serialVersionUID = 1L;
	private static final Access access = new Access();
	boolean isBearbeiten = false;
	long reservierungID;
	
	public ReservierungBearbeiten(PageParameters parameters) {
		super(parameters);
		
		add(new HeaderPanel("headerPanel"));
		add(new FooterPanel("footerPanel"));
		add(new NavigationPanel("navigationPanel"));
		
		Reservierung res = loadReservierung(parameters);
		
		isBearbeiten = checkIfBearbeiten(parameters);
		
		showReservierungDetails(res);
		showArtikelTable(res);
		showWarning(parameters);
		createButton();
	}
	
	private void showWarning(PageParameters parameters) {
		StringValue warningSV = parameters.get("warning");
		Label warning = new Label("warning", Model.of("Der Artikel konnte nicht hinzugefügt werden, weil er schon zu diesem Zeitpunkt ausgeliehen ist."));
		
		if(warningSV.isNull()){
			warning.setVisible(false);
		}else if(!warningSV.toBoolean()){
			warning.setVisible(false);
		}
		
		add(warning);
	}

	private Reservierung loadReservierung(PageParameters parameters){
		reservierungID = parameters.get("reservierungID").toLong();
		ReservierungDao reservierungDao = new ReservierungDao();
		return reservierungDao.findReservierungWithAllById(reservierungID);		
	}
	
	private boolean checkIfBearbeiten(PageParameters parameters){
		return parameters.get("bearbeiten").toBoolean();		
	}
	
	private void showReservierungDetails(Reservierung res){
		Label labelIDRes  = new Label("labelIDReservierung", Model.of("Reservierungs-Nr: " + res.getId()));
		Label labelAusleiherRes = new Label("labelAusleiherRes", Model.of(res.getCustomer().getFirstName() + " " + res.getCustomer().getLastName()));
		
		String textAbwicklerRes = "wird bei Bestätigung eingetragen";
		if(res.getDealer() != null){
			if(res.getDealer().getFirstName() != null && res.getDealer().getLastName() != null){
				String dealerFirstname = res.getDealer().getFirstName();
				String dealerLastname =  res.getDealer().getLastName();
				textAbwicklerRes = dealerFirstname +" "+dealerLastname;
			}
		}	
		Label labelAbwicklerRes = new Label("labelAbwicklerRes", Model.of(textAbwicklerRes));
		
		Label inCartHeadline = new Label("loeschen", "loeschen");
		add(inCartHeadline);
		if(!isBearbeiten){
			inCartHeadline.setVisible(false);
        }
		
		String vonRes = "";
		String bisRes = "";
		if(res.getReservedFrom() != null && res.getReservedTo() != null){
			DateFormat formatter = new SimpleDateFormat("dd.MM.yy");
			vonRes = formatter.format(res.getReservedFrom());
			bisRes = formatter.format(res.getReservedTo());
		}	
		Label labelVonRes = new Label("labelVonRes", Model.of(vonRes));
		Label labelBisRes = new Label ("labelBisRes", Model.of(bisRes));
		
		add(labelIDRes);
		add(labelAusleiherRes);
		add(labelAbwicklerRes);
		add(labelVonRes);
		add(labelBisRes);
	}
	
	private void showArtikelTable(Reservierung res){
		List<Artikel> artikel = res.getArtikel();
		
		ListDataProvider<Artikel> listDtaProvider = new ListDataProvider<Artikel>(artikel);
		DataView<Artikel> dataView = new DataView<Artikel>("rows", listDtaProvider) {
			private static final long serialVersionUID = 1L;
			@Override
			protected void populateItem(Item<Artikel> item) {
				Artikel artikel = item.getModelObject();
	            item.add(new Label("Hersteller",artikel.getHersteller()));
	            item.add(new Label("Art",artikel.getArt()));
	            item.add(new Label("Kategorie",artikel.getKategorie()));
	            item.add(new Label("Kaufdatum",artikel.getKaufdatum()));
	            item.add(new Label("Typ",artikel.getTyp()));
	            item.add(new Label("Verzeichnisbaum",artikel.getVerzeichnisbaum()));
	            item.add(new Label("name",artikel.getName()));
	            
	            String created = "";
	            DateFormat formatter = new SimpleDateFormat("dd.MM.yy");
	            created = formatter.format(artikel.getCreated());
	            item.add(new Label("created", created));
	            

	            
	            ActionPanelLoeschenArtikelReservierung panelArtikelLoeschen = new ActionPanelLoeschenArtikelReservierung("loeschenArtikel",new ArtikelModel(new Model<Artikel>(item.getModelObject()), ArtikelModelType.ARTIKEL), reservierungID);
	            item.add(panelArtikelLoeschen);
	            
	            
	            if(!isBearbeiten){
	            	panelArtikelLoeschen.setVisible(false);
	            }
			}
		 };
		 dataView.setItemsPerPage(25);
	     add(dataView);
	     add(new PagingNavigator("navigator", dataView));
	}
	private void createButton(){
		Form form = new Form("formButton");
		Button reservierungButton = new Button("addArtikel") {
			private static final long serialVersionUID = 1L;

			public void onSubmit(){
				MySession.get().setReservierungsId(reservierungID);	            	
			    this.setResponsePage(HomePage.class);
			}
			public void onError(){
				System.out.println("Error ");
			}
		};
		form.add(reservierungButton);
		add(form);
		if(!isBearbeiten){
			form.setVisible(false);
		}
	}
}
