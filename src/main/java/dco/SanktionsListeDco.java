package dco;

import java.util.Date;

import Database.Entities.SanktionsListe;
import Database.Entities.User;
import dao.BasicDao;
import dao.SanktionsListeDao;
import enums.SanktionenEnum;

public class SanktionsListeDco {

	public void createSanktionsListe(User userID, SanktionenEnum sanktionEnum){
		SanktionsListe sanktionsListe = new SanktionsListe();
		Date date = new Date();
		
		sanktionsListe.setUserID(userID);
		sanktionsListe.setCreated(date);
		sanktionsListe.setStatus(sanktionEnum);
		
		SanktionsListeDao sanktionsListeDao = new SanktionsListeDao();
		sanktionsListeDao.create(sanktionsListe);
		
	}

	

	public boolean deleteSanktionsListe(User userId){
		long id = userId.getId();
		SanktionsListeDao sanktionsListeDao = new SanktionsListeDao();
		return sanktionsListeDao.delete(sanktionsListeDao.findById(id));
	}
	
	public void editSanktionsListe(User userID){
		SanktionsListe sanktionsListe = new SanktionsListe();
		Date date = new Date();
		
		sanktionsListe.setUserID(userID);
		sanktionsListe.setModified(date);
		SanktionsListeDao sanktionsListeDao = new SanktionsListeDao();
		sanktionsListeDao.edit(sanktionsListe);
		
	}
	public void userFromGreyToBlack(SanktionsListe sanktionsListe){
		SanktionsListeDao sanktionsListeDao = new SanktionsListeDao();
		Date date = new Date();
		
		sanktionsListe.setModified(date);
		sanktionsListe.setStatus(SanktionenEnum.Schwarze);
		sanktionsListeDao.edit(sanktionsListe);
	}
}
