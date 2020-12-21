package dco;

import java.util.Date;
import java.util.List;

import Database.Entities.SanktionsListe;
import Database.Entities.User;
import Database.Entities.UserGroups;
import dao.BasicDao;

public class UserDco {

	public void createUser(String kennung, String firstName, String lastName, String eMail, String telefon, List<UserGroups> memberOfGroups){
		User user = new User();
		Date date = new Date();
		
		user.setKennung(kennung);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.seteMail(eMail);
		user.setTelefon(telefon);
		user.setCreated(date);
		user.setMemberOfGroups(memberOfGroups);
		
		BasicDao<User> artikelDao = new BasicDao<User>(User.class);
		artikelDao.create(user);
		
	}
	
	public void editUser(String kennung, String firstName, String lastName, String eMail, String telefon){
		User user = new User();
		Date date = new Date();
		
		user.setKennung(kennung);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.seteMail(eMail);
		user.setModified(date);
		user.setTelefon(telefon);
		
		BasicDao<User> userDao = new BasicDao<User>(User.class);
		userDao.edit(user);
		
	}
}
