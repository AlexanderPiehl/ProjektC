package Database;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import Database.Entities.Artikel;
import Database.Entities.Berechtigung;
import Database.Entities.User;
import Database.Entities.UserGroups;
import dao.ArtikelDao;
import dao.BerechtigungDao;
import dao.UserDao;
import dao.UserGroupsDao;
import dco.ArtikelDco;
import dco.BerechtigungDco;
import dco.ReservierungDco;
import dco.SanktionsListeDco;
import dco.UserDco;
import dco.UserGroupsDco;
import enums.SanktionenEnum;

public class CreateSomeData {
	
	public void createData(){
		createSomeBerechtigungen();
		createSomeUserGroups();
		createSomeUser();
		createSomeArtikel();
		createSomeSanktionen();
	}
	
	private void createSomeBerechtigungen(){
		BerechtigungDco berechtigung = new BerechtigungDco();
		berechtigung.createBerechtigung("Man darf alles", "admin");
		berechtigung.createBerechtigung("Man darf ausleihen", "leiher");
		berechtigung.createBerechtigung("Man darf verleihen", "verleiher");
		berechtigung.createBerechtigung("Man darf neue neue objekte erstellen", "bearbeiter");
		
	}
	
	private void createSomeUserGroups(){
		BerechtigungDao bDao = new BerechtigungDao();
		UserGroupsDco userGroups = new UserGroupsDco();
		List<Berechtigung> bList = new ArrayList<Berechtigung>();
		//bList.addAll(bDao.getByField("Name", "admin"));
		bList.add(bDao.getUniqueByField("Name", "admin"));
		userGroups.createUserGroups("admin", bList);
		bList.clear();
		bList.add(bDao.getUniqueByField("Name", "leiher"));
		bList.add(bDao.getUniqueByField("Name", "verleiher"));
		userGroups.createUserGroups("dealer", bList);
		bList.clear();
		bList.add(bDao.getUniqueByField("Name", "leiher"));
		userGroups.createUserGroups("customer", bList);
		bList.add(bDao.getUniqueByField("Name", "bearbeiter"));
		bList.add(bDao.getUniqueByField("Name", "verleiher"));
		userGroups.createUserGroups("moderator", bList);

		
	}
	
	private void createSomeUser(){
		UserDco userDco = new UserDco();
		UserGroupsDao uGDao = new UserGroupsDao();
		List<UserGroups> uGList= new ArrayList<UserGroups>();
		uGList.add(uGDao.getUniqueByField("Name", "moderator"));		
		userDco.createUser("abe456", "Henrik", "Oelze", "henrik.oelze@haw-hamburg.de", "040698745", uGList);
		uGList.clear();
		uGList.add(uGDao.getUniqueByField("Name", "customer"));
		userDco.createUser("abl143", "Börje", "Santen", "boerje.santen@haw-hamburg.de", "04098356254", uGList);
		uGList.add(uGDao.getUniqueByField("Name", "moderator"));
		userDco.createUser("abu943", "Stephan", "Köhler", "stephan.koehler@haw-hamburg.de", "040985354", uGList);
		uGList.clear();
		uGList.add(uGDao.getUniqueByField("Name", "admin"));
		userDco.createUser("ade376", "Thomas", "Ehlers", "thomas.ehlers@haw-hamburg.de", "040832746", uGList);
		uGList.clear();
		uGList.add(uGDao.getUniqueByField("Name", "dealer"));
		userDco.createUser("apo453", "Alexander", "Piehl", "alexander.piehl@haw-hamburg.de", "040832426", uGList);
	}
	
	private void createSomeArtikel(){
		ArtikelDco artikelDco = new ArtikelDco();
		artikelDco.createArtikel("Samsung", "Video", "Kamera", "HD", "Baum", "1.06.1999", "1000Euro", "Amazon", "5", "Nicht zu gebrauchen", "gut", "Samsung HD PLR12", "2", "20", "500x30x50", "2Kg", "Neon", "Lithium", "", "1", "Kasette", "leihbar", "15645654", "0406546874", "2Jahre", "1.5.2001", "Karton");
		artikelDco.createArtikel("Panasonic", "Video", "Kamera", "SD", "Baum", "5.09.2006", "800Euro", "Media Markt", "4", "", "gut", "Panasonic SD Low", "3", "25", "50x50x90", "3Kg", "Neon", "Nickel", "AA", "4", "Kasette", "Digital", "65695654", "04056456", "4Jahre", "5.9.2010", "Karton");
		artikelDco.createArtikel("Panasonic", "Ton", "Mikrophon", "Digital", "Baum", "9.09.2009", "500Euro", "Saturn", "3", "", "gut", "Mic 080", "6", "5", "10x10x200", "1Kg", "", "Nickel", "AA", "1", "Digital", "Digital", "4564564", "0434575", "2Jahre", "9.9.2010", "Karton");
		artikelDco.createArtikel("Sennheiser", "Ton", "Mikrophon", "Analog", "Baum", "10.10.20010", "400Euro", "Media Markt", "8", "", "ok", "Senn PL 500", "4", "10", "10x5x20", "1Kg", "", "", "", "", "Digital", "Digital", "54546456", "0401427444", "4Jahre", "10.10.2014", "Karton");
		artikelDco.createArtikel("Bose", "Ton", "Lautsprecher", "Digital", "Baum", "5.09.2006", "200Euro", "Amazon", "6", "", "gut", "Bose HR 587", "4", "10", "100x50x90", "10Kg", "", "", "", "", "", "Digital", "43468654", "04004047", "2Jahre", "5.9.2008", "Karton");
		artikelDco.createArtikel("Teufel", "Ton", "Lautspecher", "Digital", "Baum", "5.10.2007", "800Euro", "Saturn", "4", "", "ok", "Teufel B 100", "2", "20", "100x100x100", "20Kg", "", "", "", "3", "", "Digital", "687413", "0405122947", "2Jahre", "5.10.2009", "Karton");
		artikelDco.createArtikel("Panasonic", "Ton", "Kabel", "Cinch", "Baum", "2.03.2010", "50Euro", "Amazon", "20", "", "gut", "Kabel", "20", "25", "10x10x10", "5Kg", "", "", "", "", "", "", "42544144", "", "", "", "Karton");
		artikelDco.createArtikel("Teufel", "Ton", "Lautsprecher", "Digital", "Baum", "5.10.2010", "1500Euro", "Saturn", "10", "", "gut", "Teufel W 1300", "5", "10", "150x150x150", "15Kg", "", "", "", "", "", "", "1462168", "030545845", "2 jahre", "1013", "Karton");
		artikelDco.createArtikel("Auna", "Ton", "Mikrophon", "Digital", "Baum", "12.06.2012", "70Euro", "Amazon", "8", "", "gut", "Auna MIC-900B", "9", "10", "50x50x90", "2Kg", "", "", "", "", "Digital", "Digital", "0415245", "040454564", "4Jahre", "5.6.2016", "Karton");
		
	}
	
	private void createSomeSanktionen(){
		SanktionsListeDco sankt = new SanktionsListeDco();
		UserDao userDao = new UserDao();
		User user = new User();
		user = userDao.getUniqueByField("lastName", "Köhler");
		sankt.createSanktionsListe(user, SanktionenEnum.Graue);
		user = userDao.getUniqueByField("lastName", "Oelze");
		sankt.createSanktionsListe(user, SanktionenEnum.Schwarze);
	}
	
//	private void createSomeReservierung(){
//		User user = new User();
//		Artikel artikel = new Artikel();
//		ArtikelDao artikelDao = new ArtikelDao();
//		List<Artikel> aList = new ArrayList<Artikel>();
//		aList.add((Artikel) artikelDao.getByField("name", "Bose HR 587"));
//		UserDao userDao = new UserDao();
//		user = (User) userDao.getByField("lastName", "Ehlers");
//		ReservierungDco reservierung = new ReservierungDco();
//		Calendar cFrom = Calendar.getInstance();	
//		cFrom = createDate(2014, 16);
//		cFrom.set(Calendar.MONTH, Calendar.JUNE);
//		Calendar cTo = Calendar.getInstance();
//		cTo = createDate(2014, 30);
//		cTo.set(Calendar.MONTH, Calendar.JUNE);
//		reservierung.createReservierung(user, aList, cFrom, cTo);
//
//	}
//	
//	private Calendar createDate(int jahr, int tag){
//		Calendar c = Calendar.getInstance();
//		c.set(Calendar.YEAR, jahr);
//		c.set(Calendar.DAY_OF_YEAR, tag);
//		
//		return c;
//	}

}
