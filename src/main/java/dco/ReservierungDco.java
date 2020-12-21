package dco;

import java.util.Date;
import java.util.List;

import Database.Entities.Artikel;
import Database.Entities.Reservierung;
import Database.Entities.User;
import dao.BasicDao;
import enums.ReservierungsEnum;

public class ReservierungDco {

	public void createReservierung(User customer, List<Artikel> artikel, Date reservedFrom, Date reservedTo){
		Reservierung reservierung = new Reservierung();
		Date date = new Date();
		
		reservierung.setCustomer(customer);
		reservierung.setCreated(date);
		reservierung.setStatus(ReservierungsEnum.Angefragt);
		reservierung.setArtikel(artikel);
		reservierung.setReservedFrom(reservedFrom);
		reservierung.setReservedTo(reservedTo);
		
		BasicDao<Reservierung> artikelDao = new BasicDao<Reservierung>(Reservierung.class);
		artikelDao.create(reservierung);
		
	}
	
	public boolean  deleteReservierung(long id){
		BasicDao<Reservierung> reservierungDao = new BasicDao<Reservierung>(Reservierung.class);
		return reservierungDao.delete(reservierungDao.findById(id));
	}
	
	public void editReservierung(User customer, User dealer, ReservierungsEnum rEnum, List<Artikel> artikel, Date reservedFrom, Date reservedTo){
		Reservierung reservierung = new Reservierung();
		Date date = new Date();
		
		reservierung.setCustomer(customer);
		reservierung.setDealer(dealer);
		reservierung.setModified(date);
		reservierung.setStatus(rEnum);
		reservierung.setArtikel(artikel);
		reservierung.setReservedFrom(reservedFrom);
		reservierung.setReservedTo(reservedTo);
		
		BasicDao<Reservierung> reservierungDao = new BasicDao<Reservierung>(Reservierung.class);
		reservierungDao.edit(reservierung);
		
	}
}
