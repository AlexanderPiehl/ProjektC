package haw.Ausleihe;
import haw.Ausleihe.ActionPanels.ActionPanelReservierungBearbeiten;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.string.StringValue;

import Models.ReservierungsModel;
import Models.ReservierungsModel.ReservierungModelType;
import Tools.Access;
import Database.Entities.Artikel;
import Database.Entities.Reservierung;
import Database.Entities.User;
import dao.ArtikelDao;
import dao.ReservierungDao;
import dao.UserDao;
import enums.ReservierungsEnum;

public class BuchungHistorie extends WebPage {
	private static final long serialVersionUID = 1L;
	private static final Access acess = new Access();

	public BuchungHistorie(final PageParameters parameters) {
		super(parameters);

		add(new HeaderPanel("headerPanel"));
		add(new FooterPanel("footerPanel"));
		add(new NavigationPanel("navigationPanel"));
	
		List<Reservierung> reservierungen = loadReservierungen(parameters);
		setTableHeadReservierungen(reservierungen.isEmpty());
		showErrorMessage(reservierungen.isEmpty());
		
		ListDataProvider<Reservierung> listDtaProvider = new ListDataProvider<Reservierung>(reservierungen);
		DataView<Reservierung> dataView = new DataView<Reservierung>("rows", listDtaProvider) {

			@Override
			protected void populateItem(Item<Reservierung> item) {
				Reservierung reservierung = item.getModelObject();
				String nameAusleiher = reservierung.getCustomer().getFirstName() +" " + reservierung.getCustomer().getLastName();
				
				User dealer = reservierung.getDealer();
				String nameDealer = "";
				if(dealer != null){
					nameDealer = reservierung.getDealer().getFirstName() +" " + reservierung.getDealer().getLastName();
				}
				
				item.add(new Label("id",reservierung.getId()));
                item.add(new Label("ausleiher",Model.of(nameAusleiher)));
                item.add(new Label("abwickler",Model.of(nameDealer)));
                item.add(new Label("von",reservierung.getReservedFrom()));
                item.add(new Label("bis",reservierung.getReservedTo()));
                ActionPanelReservierungBearbeiten panelDetails = new ActionPanelReservierungBearbeiten("bearbeiten",new ReservierungsModel(new Model<Reservierung>(item.getModelObject()),ReservierungModelType.RESERVIERUNG), false);
                item.add(panelDetails);
			}};
			dataView.setItemsPerPage(25);
	        add(dataView);
    }
	private void showErrorMessage(boolean isEmpty) {
		Label error = new Label("error",  Model.of("Es gab bisher keine Buchungen."));
		if(!isEmpty){
			error.setVisible(false);
		}
		add(error);		
	}
	private void setTableHeadReservierungen(boolean isEmpty){	
		Label idHeadReservierungen = new Label("idHeadReservierungen", Model.of("ID"));
		Label ausleiherHeadReservierungen = new Label("ausleiherHeadReservierungen", Model.of("Ausleiher"));
		Label abwicklerHeadReservierungen = new Label("abwicklerHeadReservierungen", Model.of("Abwickler"));
		Label vonHeadReservierungen = new Label("vonHeadReservierungen",Model.of("Angefragt von"));
		Label bisHeadReservierungen = new Label("bisHeadReservierungen",Model.of("Angefragt bis"));
		Label detailsReservierung = new Label("detailsReservierung", Model.of("Details"));
		
		if(isEmpty){
			idHeadReservierungen.setVisible(false);
			ausleiherHeadReservierungen.setVisible(false);
			abwicklerHeadReservierungen.setVisible(false);
			vonHeadReservierungen.setVisible(false);
			bisHeadReservierungen.setVisible(false);
		}
		
		add(idHeadReservierungen);
		add(ausleiherHeadReservierungen);
		add(abwicklerHeadReservierungen);
		add(vonHeadReservierungen);
		add(bisHeadReservierungen);	
		add(detailsReservierung);
	}
	private List<Reservierung> loadReservierungen(PageParameters parameters){
		List<Reservierung> res = new ArrayList<Reservierung>();
		long userId = 0;
		long artikelId = 0;
		
		StringValue userIdSV = parameters.get("userID");
		StringValue artikelIdSV = parameters.get("artikelID");
		
		if(!userIdSV.isNull()){
			userId = parameters.get("userID").toLong();
		}
		if(!artikelIdSV.isNull()){
			artikelId = parameters.get("artikelID").toLong();
		}
		
		

		
		if(userId > 0){
			UserDao userDao = new UserDao();
			User user = userDao.findUserWithReservierungById(userId);
			res = user.getReservierungen();
		}else if(artikelId > 0){
			ArtikelDao artikelDao = new ArtikelDao();
			Artikel artikel = artikelDao.findArtikelWithReservierungById(artikelId);
			res = artikel.getReservierungen();
		}else{
			return res;
		}
		
		List<Reservierung> resHistorie = new ArrayList<Reservierung>();
		if(res.isEmpty()){
			return res;
		}else{
			for(Reservierung r: res){
				if(r.getStatus() != null){
					if(r.getStatus() == ReservierungsEnum.Rueckgabe){
						resHistorie.add(r);
					}
				}
			}
			ReservierungDao reservierungDao = new ReservierungDao();
			List<Reservierung> resWithAll = new ArrayList<Reservierung>();
			for(Reservierung r:resHistorie){
				resWithAll.add(reservierungDao.findReservierungWithAllById(r.getId()));
			}
			return resWithAll;
		}
	}
}
