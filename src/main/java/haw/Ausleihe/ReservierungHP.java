package haw.Ausleihe;

import haw.Ausleihe.ActionPanels.ActionPanelReservierungBearbeiten;
import haw.Ausleihe.ActionPanels.ActionPanelReservierungBestaetigen;
import haw.Ausleihe.ActionPanels.ActionPanelReservierungLoeschen;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.Model;

import Models.ReservierungsModel;
import Models.ReservierungsModel.ReservierungModelType;
import Tools.Access;
import Database.Entities.Reservierung;
import Database.Entities.User;
import dao.BasicDao;
import dao.ReservierungDao;
import dao.UserDao;
import enums.ReservierungsEnum;

public class ReservierungHP extends WebPage {
	private static final long serialVersionUID = 1L;
	private static final Access access = new Access();

	public ReservierungHP(final PageParameters parameters) {
		super(parameters);

		add(new HeaderPanel("headerPanel"));
		add(new FooterPanel("footerPanel"));
		add(new NavigationPanel("navigationPanel"));

		List<Reservierung> reservierungen = loadReservierungen();		
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
                ActionPanelReservierungLoeschen panelReservirungloeschen = new ActionPanelReservierungLoeschen("loeschen", new ReservierungsModel(new Model<Reservierung>(item.getModelObject()),ReservierungModelType.RESERVIERUNG));
                item.add(panelReservirungloeschen);
                ActionPanelReservierungBearbeiten panelReservierungBearbeiten = new ActionPanelReservierungBearbeiten("bearbeiten",new ReservierungsModel(new Model<Reservierung>(item.getModelObject()),ReservierungModelType.RESERVIERUNG), true);
                item.add(panelReservierungBearbeiten);
                ActionPanelReservierungBestaetigen panelReservierungBestaetigen = new ActionPanelReservierungBestaetigen("bestaetigen", new ReservierungsModel(new Model<Reservierung>(item.getModelObject()),ReservierungModelType.RESERVIERUNG));
                item.add(panelReservierungBestaetigen);
                
		    	 panelReservierungBestaetigen.setVisible(false);
                
                if(access.isLoggedin()){
				     if(access.hasAccessTo("book_reserved") || access.hasAccessTo("is_admin") || access.hasAccessTo("manage_own_lab")){
				    	 panelReservierungBestaetigen.setVisible(true);
				     }				     
			     }
			}};
			dataView.setItemsPerPage(25);
	        add(dataView);
    }
	private void showErrorMessage(boolean isEmpty) {
		Label error = new Label("error",  Model.of("Zurzeit gibt es keine offenen Reservierungen."));
		if(!isEmpty){
			error.setVisible(false);
		}
		add(error);		
	}
	
	private List<Reservierung> loadReservierungen() {
		 if(access.isLoggedin()){
	    	 ReservierungDao reservierungDao = new ReservierungDao();
	    	 List<Reservierung> reservierungen = reservierungDao.findReservierungWithUsersByEnum(ReservierungsEnum.Angefragt);
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
	private void setTableHeadReservierungen(boolean isEmpty){	
		Label idHeadReservierungen = new Label("idHeadReservierungen", Model.of("ID"));
		Label ausleiherHeadReservierungen = new Label("ausleiherHeadReservierungen", Model.of("Ausleiher"));
		Label abwicklerHeadReservierungen = new Label("abwicklerHeadReservierungen", Model.of("Abwickler"));
		Label vonHeadReservierungen = new Label("vonHeadReservierungen",Model.of("Angefragt von"));
		Label bisHeadReservierungen = new Label("bisHeadReservierungen",Model.of("Angefragt bis"));
		Label loeschenHeadReservierungen = new Label("loeschenHeadReservierungen", Model.of("Löschen"));
		Label bearbeitenHeadReservierungen = new Label("bearbeitenHeadReservierungen", Model.of("Bearbeiten"));
		Label bestaetigenHeadReservierungen = new Label("bestaetigenHeadReservierungen", Model.of("Bestätigen"));
		
		if(isEmpty){
			idHeadReservierungen.setVisible(false);
			ausleiherHeadReservierungen.setVisible(false);
			abwicklerHeadReservierungen.setVisible(false);
			vonHeadReservierungen.setVisible(false);
			bisHeadReservierungen.setVisible(false);
			loeschenHeadReservierungen.setVisible(false);
			bearbeitenHeadReservierungen.setVisible(false);
			bestaetigenHeadReservierungen.setVisible(false);
		}else if(!access.hasAccessTo("book_reserved")){
			bestaetigenHeadReservierungen.setVisible(false);
		}else if(!access.hasAccessTo("manage_own_lab")){
			bestaetigenHeadReservierungen.setVisible(false);
		}else if(!access.hasAccessTo("is_admin")){
			bestaetigenHeadReservierungen.setVisible(false);
		}
		
		add(idHeadReservierungen);
		add(ausleiherHeadReservierungen);
		add(abwicklerHeadReservierungen);
		add(vonHeadReservierungen);
		add(bisHeadReservierungen);
		add(loeschenHeadReservierungen);	
		add(bearbeitenHeadReservierungen);
		add(bestaetigenHeadReservierungen);	
	}
}