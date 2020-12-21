package haw.Ausleihe;

import haw.Ausleihe.ActionPanels.ActionPanelBuchungBestaetigen;
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

import Models.ReservierungsModel;
import Models.ReservierungsModel.ReservierungModelType;
import Tools.Access;
import Database.Entities.Reservierung;
import Database.Entities.User;
import dao.ReservierungDao;
import dao.UserDao;
import enums.ReservierungsEnum;


public class Buchungen extends WebPage {
	private static final long serialVersionUID = 1L;
	private static final Access access = new Access();
	
	public Buchungen(final PageParameters parameters) {
		super(parameters);
		
		add(new HeaderPanel("headerPanel"));
		add(new FooterPanel("footerPanel"));
		add(new NavigationPanel("navigationPanel"));
		
		List<Reservierung> reservierungen =  loadReservierungen();
		setTableHeadBuchungen(reservierungen.isEmpty());
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
                ActionPanelBuchungBestaetigen panelBestaetigen = new ActionPanelBuchungBestaetigen("bestaetigen", new ReservierungsModel(new Model<Reservierung>(item.getModelObject()),ReservierungModelType.RESERVIERUNG));
                item.add(panelBestaetigen);
                
                panelBestaetigen.setVisible(false);

                
                if(access.isLoggedin()){
				     if(access.hasAccessTo("return_items") ||access.hasAccessTo("is_admin") || access.hasAccessTo("manage_own_lab")){
				    	 panelBestaetigen.setVisible(true);

				     }				  
			     }
                
			}};
			dataView.setItemsPerPage(25);
	        add(dataView);
		
	}
	
	private void showErrorMessage(boolean isEmpty) {
		Label error = new Label("error",  Model.of("Zurzeit gibt es keine bestätigte Reservierungen."));
		if(!isEmpty){
			error.setVisible(false);
		}
		add(error);		
	}

	private List<Reservierung> loadReservierungen() {
		 if(access.isLoggedin()){
	    	 ReservierungDao reservierungDao = new ReservierungDao();
	    	 List<Reservierung> reservierungen = reservierungDao.findReservierungWithUsersByEnum(ReservierungsEnum.Akzeptiert);
		     if(access.hasAccessTo("book_reserved") || access.hasAccessTo("is_admin") || access.hasAccessTo("manage_own_lab")){
		 		return reservierungen;
		     }else{
		    	 List<Reservierung> reservierungenFromUser = new ArrayList<Reservierung>();
		    	 String kennung = MySession.get().getLoggedinID();
		    	 UserDao userDao = new UserDao();
		    	 User user = userDao.getUniqueByField("kennung", kennung);
		    	 for(Reservierung res: reservierungen){
		    		 if(res.getCustomer().getId() == user.getId()){
		    			 reservierungenFromUser.add(res);
		    		 }
		    	 }
		    	 return reservierungenFromUser;
		     }
		 }		 
		return new ArrayList<Reservierung>();
	}

	private void setTableHeadBuchungen(boolean isEmpty) {
		Label idHeadBuchungen = new Label("idHeadBuchungen", Model.of("ID"));
		Label ausleiherHeadBuchungen = new Label("ausleiherHeadBuchungen", Model.of("Ausleiher"));
		Label abwicklerHeadBuchungen = new Label("abwicklerHeadBuchungen", Model.of("Abwickler"));
		Label vonHeadBuchungen = new Label("vonHeadBuchungen",Model.of("Gebucht von"));
		Label bisHeadBuchungenn = new Label("bisHeadBuchungenn",Model.of("Gebucht bis"));
		Label detailsHeadBuchungen = new Label("detailsHeadBuchungen", Model.of("Details"));
		Label bestaetigenHeadBuchungen = new Label("bestaetigenHeadBuchungen", Model.of("Rückgabe"));
		
		if(isEmpty){
			idHeadBuchungen.setVisible(false);
			ausleiherHeadBuchungen.setVisible(false);
			abwicklerHeadBuchungen.setVisible(false);
			vonHeadBuchungen.setVisible(false);
			bisHeadBuchungenn.setVisible(false);
			detailsHeadBuchungen.setVisible(false);
			bestaetigenHeadBuchungen.setVisible(false);
		}else if(!access.hasAccessTo("return_items")){
			bestaetigenHeadBuchungen.setVisible(false);
		}else if(!access.hasAccessTo("manage_own_lab")){
			bestaetigenHeadBuchungen.setVisible(false);
		}else if(!access.hasAccessTo("is_admin")){
			bestaetigenHeadBuchungen.setVisible(false);
		}
		
		add(idHeadBuchungen);
		add(ausleiherHeadBuchungen);
		add(abwicklerHeadBuchungen);
		add(vonHeadBuchungen);
		add(bisHeadBuchungenn);
		add(detailsHeadBuchungen);	
		add(bestaetigenHeadBuchungen);	
	}
}