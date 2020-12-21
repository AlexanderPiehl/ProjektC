package haw.Ausleihe.ActionPanels;

import haw.Ausleihe.HomePage;
import haw.Ausleihe.MySession;
import haw.Ausleihe.ReservierungHP;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import dao.ReservierungDao;
import enums.ReservierungsEnum;
import Database.Entities.Reservierung;

public class ActionPanelReservierungHinzufuegen extends Panel{
	private static final long serialVersionUID = 1L;

	public ActionPanelReservierungHinzufuegen(String id, IModel<Reservierung> reservierungModel) {
		super(id, reservierungModel);
		add(new Link<Object>(id){
			 private static final long serialVersionUID = 1L;
			 
			 @Override
			 public void onClick(){
				 Reservierung reservierung = (Reservierung) ActionPanelReservierungHinzufuegen.this.getDefaultModelObject();
				 MySession.get().setReservierungsId(reservierung.getId());
			            	
			    this.setResponsePage(HomePage.class);
			 }
		 });
	}
	

}
