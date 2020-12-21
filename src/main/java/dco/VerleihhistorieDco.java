package dco;

import java.util.Date;
import java.util.List;

import dao.VerleihhistorieDao;
import Database.Entities.Artikel;
import Database.Entities.Reservierung;
import Database.Entities.User;
import Database.Entities.Verleihhistorie;

public class VerleihhistorieDco {
	
	public void createVerleihhistorie(int barcodeData, User customer, User dealer,String comments, List<Artikel> artikel, Date reservedFrom, Date reservedTo){
		Verleihhistorie historie = new Verleihhistorie();
		Date currentDate = new Date();
		
		historie.setBarcodeData(barcodeData);
		historie.setCustomer(customer);
		historie.setDealer(dealer);
		historie.setComments(comments);
		historie.setCreated(currentDate);
		historie.setReservedFrom(reservedFrom);
		historie.setReservedTo(reservedTo);
		historie.setArtikel(artikel);
	}
	
	public void createVerleihhistorie(Reservierung reservierung, String comments){
		Verleihhistorie historie = new Verleihhistorie();
		Date currentDate = new Date();
		
		historie.setBarcodeData(reservierung.getBarcodeData());
		historie.setCustomer(reservierung.getCustomer());
		historie.setDealer(reservierung.getCustomer());
		historie.setComments(comments);
		historie.setCreated(currentDate);
		historie.setReservedFrom(reservierung.getReservedFrom());
		historie.setReservedTo(reservierung.getReservedTo());
		historie.setArtikel(reservierung.getArtikel());
	}
	
	public boolean deleteVerleihhistorie(long id){
		VerleihhistorieDao historieDao = new VerleihhistorieDao();
		return historieDao.delete(historieDao.findById(id));
	}
	
	public void editVerleihhistorie(int barcodeData, User customer, User dealer,String comments, List<Artikel> artikel, Date reservedFrom, Date reservedTo){
		Verleihhistorie historie = new Verleihhistorie();
		Date currentDate = new Date();
		
		historie.setBarcodeData(barcodeData);
		historie.setCustomer(customer);
		historie.setDealer(dealer);
		historie.setComments(comments);
		historie.setModified(currentDate);
		historie.setReservedFrom(reservedFrom);
		historie.setReservedTo(reservedTo);
		historie.setArtikel(artikel);
		
		VerleihhistorieDao historieDao = new VerleihhistorieDao();
		historieDao.edit(historie);
	}
	
	public void editVerleihhistorie(Reservierung reservierung, String comments){
		Verleihhistorie historie = new Verleihhistorie();
		Date currentDate = new Date();
		
		historie.setBarcodeData(reservierung.getBarcodeData());
		historie.setCustomer(reservierung.getCustomer());
		historie.setDealer(reservierung.getCustomer());
		historie.setComments(comments);
		historie.setModified(currentDate);
		historie.setReservedFrom(reservierung.getReservedFrom());
		historie.setReservedTo(reservierung.getReservedTo());
		historie.setArtikel(reservierung.getArtikel());
		
		VerleihhistorieDao historieDao = new VerleihhistorieDao();
		historieDao.edit(historie);
	}
}
