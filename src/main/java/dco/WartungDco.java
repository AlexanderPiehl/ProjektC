package dco;

import java.util.Date;

import dao.WartungsDao;
import enums.WartungsEnum;
import Database.Entities.Artikel;
import Database.Entities.Wartung;

public class WartungDco {

	public void createWartung(Artikel artikel, WartungsEnum wartungsArt, String desc, String maintenanceDate){
		Wartung wartung = new Wartung();
		Date currentDate = new Date();
		
		wartung.setArtikel(artikel);
		wartung.setStatus(wartungsArt);
		wartung.setDecs(desc);
		wartung.setMaintenanceDate(maintenanceDate);
		wartung.setCreated(currentDate);
		
		WartungsDao wartungsDao = new WartungsDao();
		wartungsDao.edit(wartung);
	}
	public void createWartung(Wartung wartung){
		Date currentDate = new Date();
		wartung.setCreated(currentDate);
		
		WartungsDao wartungsDao = new WartungsDao();
		wartungsDao.edit(wartung);
	}
	
	
	public boolean deleteWartung(long id){
		WartungsDao wartungsDao = new WartungsDao();
		return wartungsDao.delete(wartungsDao.findById(id));
	}
	
	public void editWartung(Artikel artikel, WartungsEnum wartungsArt, String desc, String maintenanceDate){
		Wartung wartung = new Wartung();
		Date currentDate = new Date();
		
		wartung.setArtikel(artikel);
		wartung.setStatus(wartungsArt);
		wartung.setDecs(desc);
		wartung.setMaintenanceDate(maintenanceDate);
		wartung.setModified(currentDate);		
	}
}
