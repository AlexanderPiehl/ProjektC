package haw.Ausleihe;

import haw.Ausleihe.ActionPanels.ActionPanelCartLoeschen;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import dao.ArtikelDao;
import dao.BasicDao;
import dao.UserDao;
import dco.ReservierungDco;
import Database.Entities.Artikel;
import Database.Entities.User;
import Models.ArtikelModel;
import Models.ArtikelModel.ArtikelModelType;
import Tools.Access;

public class warenkorb extends WebPage {
	private static final long serialVersionUID = 1L;
	private static final Access access = new Access();
	private boolean isAlradyLoan = false;
	private boolean ObjectsAreAlradyLoan;
	
	
	public warenkorb(final PageParameters parameters) {
		super(parameters);
		//List von Warnkorbobjekten aus des Session
		final List<Artikel> warenkorbList = MySession.get().getMylistCart();
		final List<Artikel> verliehenList = MySession.get().getListVerliehen();
		setTableHeadLineCart(warenkorbList.isEmpty());
		setTableHeadLineIsLoan(verliehenList.isEmpty());
		
		add(new HeaderPanel("headerPanel"));
		add(new FooterPanel("footerPanel"));
		add(new NavigationPanel("navigationPanel"));
		
		ListDataProvider<Artikel> listDtaProvider = new ListDataProvider<Artikel>(warenkorbList);

		DataView<Artikel> dataView = new DataView<Artikel>("rows", listDtaProvider) {
			private static final long serialVersionUID = 1L;

			@Override  
			protected void populateItem(Item<Artikel> item) {
				Artikel artikel = item.getModelObject();
	            item.add(new Label("Hersteller",artikel.getHersteller()));
	            item.add(new Label("Art",artikel.getArt()));
	            item.add(new Label("Kategorie",artikel.getKategorie()));
	            item.add(new Label("name",artikel.getName()));
	            item.add(new ActionPanelCartLoeschen ("CartLoeschen",new ArtikelModel(new Model<Artikel>(item.getModelObject()), ArtikelModelType.ARTIKEL)));
			}
		};
		dataView.setItemsPerPage(25);
		add(dataView);
		
		
		//schon verliehende objekte
		ListDataProvider<Artikel> listDtaProviderLoan = new ListDataProvider<Artikel>(verliehenList);
		DataView<Artikel> dataViewLoan = new DataView<Artikel>("rowsLoan", listDtaProviderLoan) {
			private static final long serialVersionUID = 1L;

			@Override  
			protected void populateItem(Item<Artikel> item) {
				Artikel artikel = item.getModelObject();
	            item.add(new Label("HerstellerLoan",artikel.getHersteller()));
	            item.add(new Label("ArtLoan",artikel.getArt()));
	            item.add(new Label("KategorieLoan",artikel.getKategorie()));
	            item.add(new Label("nameLoan",artikel.getName()));
			}
		};
		dataViewLoan.setItemsPerPage(25);
		add(dataViewLoan);
		
		
		//Datum und 
		final DateTextField fromDate = new DateTextField("fromDate", new Model(null));
		final DateTextField toDate =new DateTextField("toDate", new Model(null));
		Form<?> form = new Form<Void>("warenkrobForm") {
			private static final long serialVersionUID = 1L;
			@Override
			protected void onSubmit(){
				ReservierungDco reservierungDco = new ReservierungDco();
				
				List <User> foundUsers = searchUser();
				UserDao userDaoTest = new UserDao();
				User user = null;
				user = foundUsers.get(0);

				
				Date fromDateValue = fromDate.getModelObject();
				Date toDateValue = toDate.getModelObject();
				
				//isObjectLoan(fromDateValue, toDateValue, warenkorbList);
				ArtikelDao artikelDao = new ArtikelDao();
				for(int i = 0; i < warenkorbList.size(); i++){
					isAlradyLoan = artikelDao.okToLoan(fromDateValue, toDateValue, warenkorbList.get(i).getId());
					if(!isAlradyLoan){
						MySession.get().getListVerliehen().add(warenkorbList.get(i));
						MySession.get().getMylistCart().remove(i);
						ObjectsAreAlradyLoan = true;
					}
				}
				
				
				if(!ObjectsAreAlradyLoan){
					reservierungDco.createReservierung(user, warenkorbList, fromDateValue, toDateValue);
					MySession.get().getListVerliehen().removeAll(verliehenList);
					MySession.get().emptyCart();
				} 
				setResponsePage(warenkorb.class);
			}

		};
		if(warenkorbList.size() == 0){ form.setVisible(false); }
		add(form);
		form.add(fromDate);
		form.add(toDate);
		
		//anzeige wen warenkorb lehr ist
		Label emptyCart = new Label("emptyCart", "Der Warenkorb ist leer, bitte begeben sie sich zur Ausleihe");
		if(warenkorbList.size() > 0){ emptyCart.setVisible(false); }
		add(emptyCart);
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
	
	private void setTableHeadLineCart (Boolean isEmpty){
		//Tabele
		Model<String> bilderModel = Model.of("Bilder");
		Model<String> herstellerMdoel = Model.of("Herstellen");
		Model<String> artModel = Model.of("Art");
		Model<String> KategorieModel = Model.of("Kategorie");
		Model<String> loeschenModel = Model.of("loeschen");
		Model<String> nameModel = Model.of("Name");
		
		//Tabele
		Label BilderCart = new Label("BilderCart", bilderModel);
		Label herstellerCart = new Label("herstellerCart", herstellerMdoel);
		Label artCart = new Label("artCart", artModel);
		Label KategorieCart = new Label("KategorieCart",KategorieModel);
		Label loeschenCart = new Label("loeschenCart", loeschenModel);
		Label nameCart = new Label("nameCart", nameModel);
		
		if(isEmpty){
			BilderCart.setVisible(false);
			herstellerCart.setVisible(false);
			artCart.setVisible(false);
			KategorieCart.setVisible(false);
			loeschenCart.setVisible(false);
			nameCart.setVisible(false);
		}
		add(BilderCart);
		add(herstellerCart);
		add(artCart);
		add(KategorieCart);
		add(loeschenCart);
		add(nameCart);
	}
	
	
	private void setTableHeadLineIsLoan (Boolean isEmpty){
		//Tabele
		Model<String> bilderModel = Model.of("Bilder");
		Model<String> herstellerMdoel = Model.of("Herstellen");
		Model<String> artModel = Model.of("Art");
		Model<String> KategorieModel = Model.of("Kategorie");
		Model<String> InfoTextodel = Model.of("Diese Objekte sind leider Schon Verliehen, "
				+ "schicken sie die Bestellung nun ohne diese ab oder begeben Sie sich zur Leihe und wählen Sie Alternativen aus");
		Model<String> nameModel = Model.of("Name");

		
		//Tabele
		Label BilderCart = new Label("BilderIsLoan", bilderModel);
		Label herstellerCart = new Label("herstellerIsLoan", herstellerMdoel);
		Label artCart = new Label("artIsLoan", artModel);
		Label KategorieCart = new Label("KategorieIsLoan",KategorieModel);
		Label InfoTextLoan = new Label("InfoTextIsLoan",InfoTextodel);
		Label nameLoan = new Label("nameIsLoan", nameModel);
		
		if(isEmpty){
			BilderCart.setVisible(false);
			herstellerCart.setVisible(false);
			artCart.setVisible(false);
			KategorieCart.setVisible(false);
			InfoTextLoan.setVisible(false);
			nameLoan.setVisible(false);
		}
		add(BilderCart);
		add(herstellerCart);
		add(artCart);
		add(KategorieCart);
		add(InfoTextLoan);
		add(nameLoan);

	}
}