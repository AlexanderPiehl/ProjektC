package haw.Ausleihe.ActionPanels;

import java.util.List;
import java.util.Properties;

import haw.Ausleihe.MySession;
import haw.Ausleihe.ReservierungHP;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import dao.BasicDao;
import dao.ReservierungDao;
import enums.ReservierungsEnum;
import Database.Entities.Reservierung;
import Database.Entities.User;

public class ActionPanelReservierungBestaetigen extends Panel{

	private static final long serialVersionUID = 1L;

	public ActionPanelReservierungBestaetigen(String id, IModel<Reservierung> reservierungModel) {
		super(id, reservierungModel);
		
		 add(new Link<Object>(id){
			 private static final long serialVersionUID = 1L;
			 
			 @Override
			 public void onClick(){
			    Reservierung reservierung = (Reservierung) ActionPanelReservierungBestaetigen.this.getDefaultModelObject();
			    ReservierungDao reservierungDao = new ReservierungDao();
			    User dealer = null;
			    List <User> foundUsers = searchUser();
			    dealer = (User) foundUsers.get(0);
			    reservierung.setDealer(dealer);
			    reservierung.setStatus(ReservierungsEnum.Akzeptiert);
			    reservierungDao.edit(reservierung);
			            	
			    this.setResponsePage(ReservierungHP.class);
			 }
		 });
	}
	
	private List<User> searchUser(){
		String kennungDealer = MySession.get().getLoggedinID();
		BasicDao<User> userDao = new BasicDao<User>(User.class);
		Properties props = new Properties();
		if(kennungDealer != null){
			props.put("kennung", kennungDealer);
		}
		if(!props.isEmpty()){
			List<User> foundUsers = userDao.searchByProperties(props);
			return foundUsers;
		}	
		return null;
	}
}
