package Database;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;

import org.hibernate.Hibernate;

import Database.Entities.Artikel;
import Database.Entities.Berechtigung;
import Database.Entities.Reservierung;
import Database.Entities.SanktionsListe;
import Database.Entities.User;
import Database.Entities.UserGroups;
import Tools.Hash;
import dao.ArtikelDao;
import dao.BasicDao;
import dao.ReservierungDao;
import dao.SanktionsListeDao;
import dco.ArtikelDco;
import dco.BerechtigungDco;
import dco.BilderDco;
import dco.ReservierungDco;
import dco.SanktionsListeDco;
import dco.UserGroupsDco;
import enums.SanktionenEnum;
public class HibernateApp {
	public static void main(String[] args){
		HibernateHelper hibernate = new HibernateHelper();
		ArtikelDco artikelDco =new ArtikelDco();
		hibernate.initHibernate();
		
//		hibernate.addUser("abb123", "test", "Henrik", "Oelze", "test«test.de");
//		hibernate.addUser("abb122", "test", "Alex", "Piehl", "test«test.de");
//		hibernate.addUser("abb121", "test", "Boerje", "Santen", "test«test.de");
//		hibernate.addUser("abb212", "test", "Stephan", "Koehler", "test«test.de");
		
		Calendar c = new GregorianCalendar();
	    c.set(Calendar.HOUR_OF_DAY, 0); //anything 0 - 23
	    c.set(Calendar.MINUTE, 0);
	    c.set(Calendar.SECOND, 0);
		
		Date testDate = c.getTime();
		
		
	Date dateFrom = new Date();

//	Calendar cFrom = Calendar.getInstance();
////	cFrom.set(Calendar.YEAR, 2014);
////	cFrom.set(Calendar.MONTH, Calendar.JULY);
////	cFrom.set(Calendar.DAY_OF_YEAR, 25);
//	cFrom.set( 2014, Calendar.JULY, 8 );  
//		
//	Calendar cTo = Calendar.getInstance();
////	cTo.set(Calendar.YEAR, 2014);
////	cTo.set(Calendar.MONTH, Calendar.AUGUST);
////	cTo.set(Calendar.DAY_OF_YEAR, 1);
//	cTo.set( 2014, Calendar.JULY, 20 );  
//	System.out.println("Daten in der Abfrage: von: "+cFrom.getTime()+" bis: "+cTo.getTime());
//	long l = 9;
//	
//	Artikel a = new Artikel();
//	ArtikelDao artDao = new ArtikelDao();
//	a = artDao.findArtikelWithReservierungById(18);
//	List<Reservierung> rList = new ArrayList<Reservierung>();
//	rList = a.getReservierungen();
	
//	System.out.println("Reservierungsliste im Artikel 1: "+rList.size());
//	List<Artikel> artikel = new ArrayList<Artikel>();
//	Reservierung r = new Reservierung();
//	r = rList.get(0);
//	artikel = artDao.countLendArtikel(cFrom.getTime(), cTo.getTime(), l);
//	System.out.println("Abfrage nach Artikeln mit 'ID9 und ausgeliehen: " + artikel.size());
//	System.out.println("Daten im Artikel: von: "+r.getReservedFrom()+" bis: "+r.getReservedTo());
//	String test;
//	if(artDao.okToLoan(cFrom.getTime(), cTo.getTime(), l)){
//		test = "Kann man noch ausleihen";
//	}else
//		test = "Kann man nicht ausleihen";
//	System.out.println("ausleihe ok? "+ test);

		
		CreateSomeData createData = new CreateSomeData();
		createData.createData();
		
//		for(int i = 0;i<100;i++)
//		{
//			artikelDco.createArtikel("Panasonic", "HD", "Polaroid", "Kamera", "blau", "15.03.1932", "1900.0", "Kamera"+i, "12",
//					"Versuch", "gut", "ja", "5", "richtig", "gross", "12.0", "Neon", "blau", "Zimt", "5", "Schallplatte", "gut",
//					"12345", "04567890", "abgelaufen", "nein", "weg");
////		hibernate.addArtikel("Panasonic", "HD", "Polaroid", "Kamera", "blau", "15.03.1932", 1900.0f, "Kamera"+i, "12",
////				"Versuch", "gut", "ja", 5, "richtig", "gross", 12.0f, "Neon", "blau", "Zimt", "5", "Schallplatte", "gut",
////				"12345", "04567890", "abgelaufen", "nein", "weg");
//		}
//		artikelDco.createArtikel("Samsung", "SD", "Polaroid", "Kamera", "blau", "15.03.1932", "1900.0", "KameraSamsung", "12",
//				"Versuch", "gut", "ja", "5", "richtig", "gross", "12.0", "Neon", "blau", "Zimt", "5", "Schallplatte", "gut",
//				"12345", "04567890", "abgelaufen", "nein", "weg");
////		
////		hibernate.getAllArtikel();
//		
//		BasicDao<Artikel> artikelDao = new BasicDao<Artikel>(Artikel.class);
//		List<Artikel> artikelList = artikelDao.getAll();
//		System.out.println("HibernateApp.main(): found " + artikelList.size() + " artikel by all Artikel search" );
////		Properties props = new Properties();
////		List<String> herstellerListe = new ArrayList<String>();
////		herstellerListe.add("Panasonic");
////		herstellerListe.add("Samsung");
////		props.put("hersteller", herstellerListe);
////		
////		List<Artikel> artikelsByHersteller = artikelDao.searchByProperties(props);
////		System.out.println("HibernateApp.main(): found " + artikelsByHersteller.size() + " artikel by artikel search" );
////		for(Artikel a : artikelsByHersteller){
////			System.out.println("Found: " + a.getHersteller() + " " + a.getLieferant());
////		}
////		
////		ArtikelDao artDao = new ArtikelDao();
//////		List<Artikel> artikelByTyp = artikelDao.getByField("typ", "HD");
////		List<Artikel> artikelByTyp = artDao.getByTyp("HD");
////				//.getByField("typ", "HD");
////
////		
////		System.out.println("HibernateApp.main(): found " + artikelByTyp.size() + " artikel with type HD" );
////		if(artikelByTyp.size() > 0){
////			System.out.println("First Artikel: " + artikelByTyp.get(0).getLieferant());
////		}
////		
////		
//		User user = new User();
//		user.setFirstName("Carsten");
//		user.setLastName("Ehlers");
//		user.setKennung("abb121");
//		user.setTelefon("04060921252");
//		user.seteMail("carsten.ehlers@haw.de");
//		
//		BasicDao<User> userDao = new BasicDao<User>(User.class);
//		userDao.create(user);
//		
//		SanktionsListe liste = new SanktionsListe();
//		liste.setStatus(SanktionenEnum.Graue);
//		liste.setUserID(user);
//		
//		SanktionsListeDao sanktionsListeDao = new SanktionsListeDao();
//		sanktionsListeDao.create(liste);
////		
////		Properties userSearch = new Properties();
////		userSearch.put("firstName", "cars*");
////		userSearch.put("lastName", "E%");
////		
////		List<User> usersFromDb = userDao.searchByProperties(userSearch);
////		System.out.println("HibernateApp.main(): found " + artikelByTyp.size() + " users with search" );
////		for(User u : usersFromDb){
////			System.out.println("Found: " + u.getFirstName() + " " + u.getLastName());
////		}
////		
////		Calendar cTo = Calendar.getInstance();
////		cTo.set(Calendar.YEAR, 2014);
////		cTo.set(Calendar.MONTH, Calendar.JUNE);
////		cTo.set(Calendar.DAY_OF_YEAR, 30);
////		
////		List<Artikel> availableArtikel = artDao.getAvailableArtikel(new Date(), cTo.getTime(), "SD");
////		System.out.println("Found " + availableArtikel.size() + " artikel which can be lended ");
////		
////		
////		Calendar cFrom = Calendar.getInstance();
////		cFrom.set(Calendar.YEAR, 2014);
////		cFrom.set(Calendar.MONTH, Calendar.JUNE);
////		cFrom.set(Calendar.DAY_OF_YEAR, 4);
////		
//////		Artikel a1 = ! availableArtikel.isEmpty() ? availableArtikel.get(0) : artDao.getByTyp("SD").get(0);
//////		
//////		
//////		Reservierung r1 = new Reservierung();
//////		List<Artikel> artikelR1 = new ArrayList<Artikel>();
//////		artikelR1.add(a1);
//////		r1.setDealer(user);
//////		r1.setCustomer(user);
//////		r1.setCreated(new Date());
//////		r1.setReservedFrom(cFrom.getTime());
//////		r1.setReservedTo(cTo.getTime());
//////		r1.setArtikel(null);
//////		
//////		ReservierungDao reservierungsDao = new ReservierungDao();
//////		r1 = reservierungsDao.create(r1);
////////		r1 = reservierungsDao.create(r1, artikelR1);
//////		
//////		
//////		System.out.println("HibernateApp.main(): reservierung.id: " + r1.getId());
//////		
//////		a1.getReservierungen().add(r1);
//////		a1 = artDao.edit(a1);
//////		
//////		cTo.set(Calendar.MONTH, Calendar.AUGUST);
//////		availableArtikel = artDao.getAvailableArtikel(new Date(), cTo.getTime(), "SD");
//////		System.out.println("Found " + availableArtikel.size() + " artikel which can be lended after reservation was created");
//////		
//////		BookingItems test = new BookingItems();
//////		test.setId(0);
//////		test.setOrderId(1234567890);
//////		test.setObjectId(66666);
//////		test.setDeleted(testDate);
//////		test.setCreated(testDate);
//////		test.setModified(testDate);
////	
////		
////
////		SanktionsListeDco sanktionsListe = new SanktionsListeDco();
////		sanktionsListe.createSanktionsListe(user, SanktionenEnum.Graue);
////
//////		artikelDco.createArtikel("Panasonic", "HD", "Polaroid", "Kamera", "blau", "15.03.1932", 1900.0f, "Kamera", "12",
//////				"Versuch", "gut", "C:\\AMD\\starwars.jpg", 5, "richtig", "gross", 12.0f, "Neon", "blau", "Zimt", "5", "Schallplatte", "gut",
//////				"12345", "04567890", "abgelaufen", "nein", "weg");
//////		
////		//User database = (User) session.load(User.class, 0);
////		//System.out.println(database.getFirstName());
////		
//		
////		 Wirft folgende Exception failed to lazily initialize a collection of role: Database.Entities.Reservierung.artikel, could not initialize proxy - no Session
////		 * und zwar in der Zeile for(int i = 0; i < artikelR2.size(); i++){
//		ArtikelDao artDao = new ArtikelDao();
//		ReservierungDao reservierungsDao = new ReservierungDao();
////		BasicDao<User> userDao = new BasicDao<User>(User.class);
//		
//		Calendar cTo = Calendar.getInstance();
//		cTo.set(Calendar.YEAR, 2014);
//		cTo.set(Calendar.MONTH, Calendar.JUNE);
//		cTo.set(Calendar.DAY_OF_YEAR, 30);
//		
//		Calendar cFrom = Calendar.getInstance();
//		cFrom.set(Calendar.YEAR, 2014);
//		cFrom.set(Calendar.MONTH, Calendar.JUNE);
//		cFrom.set(Calendar.DAY_OF_YEAR, 4);
//		
//		List<Artikel> artikelByTyp = artDao.getByTyp("HD");
//		System.out.println("HibernateApp.main() artikelbyTyp"+artikelByTyp.size());
//		List<User> userList = userDao.getAll();
//		
//		Reservierung r1 = new Reservierung();
//		List<Artikel> artikelR1 = new ArrayList<Artikel>();
//		List<Reservierung> reservierungList = new ArrayList<Reservierung>();
//		reservierungList.add(r1);
//		Artikel artikelTest = new Artikel();
//		artikelTest = artikelByTyp.get(0);
//		artikelTest.setReservierungen(reservierungList);
//		artikelR1.add(artikelByTyp.get(0));
//		artikelR1.add(artikelByTyp.get(1));
//		artikelR1.add(artikelByTyp.get(35));
////		System.out.println("HibernateApp.main() artikelr1 "+ artikelR1.get(0).getLieferant());
////		System.out.println("HibernateApp.main() artikelr1 "+ artikelR1.get(1).getLieferant());
////		System.out.println("HibernateApp.main() artikelr1 "+ artikelR1.get(2).getLieferant());
//		//r1.setDealer(userList.get(0));
//		r1.setCustomer(userList.get(0));
//		r1.setCreated(new Date());
//		r1.setReservedFrom(cFrom.getTime());
//		r1.setReservedTo(cTo.getTime());
//		r1.setArtikel(artikelR1);
//		
//		reservierungsDao.create(r1);
//		
////		List<Reservierung> reservierungList = reservierungsDao.getAll();
////		Reservierung r2 = reservierungList.get(reservierungList.size() - 1);
////		List<Artikel> artikelR2 = r2.getArtikel();
////		for(int i = 0; i < artikelR2.size(); i++){
////			System.out.println(artikelR2.get(i));
////		}
//		
////		BilderDco bild = new BilderDco();
////		bild.createBilder(artikelByTyp.get(0), "C:\\Temp\\Flammen.jpg");
////		bild.createBilder(artikelByTyp.get(0), "C:\\Temp\\hs.jpg");
////		bild.createBilder(artikelByTyp.get(1), "C:\\Temp\\M51.jpg");
////		bild.createBilder(artikelByTyp.get(2), "C:\\Temp\\IS.jpg");
////		bild.createBilder(artikelByTyp.get(2), "C:\\Temp\\ISS.jpg");
////		bild.deleteBild(3);
//		
//		BerechtigungDco berechtigung = new BerechtigungDco();
//		berechtigung.createBerechtigung("was auch immer das ist", "admin");
//		berechtigung.createBerechtigung("bla", "pöbel");
//		List<Berechtigung> berechtigungListe = new ArrayList<Berechtigung>();
//		BasicDao<Berechtigung> berechtigungDao = new BasicDao<Berechtigung>(Berechtigung.class);
//		berechtigungListe.add(berechtigungDao.findById(1));
//		berechtigungListe.add(berechtigungDao.findById(2));
//		UserGroupsDco userGroup = new UserGroupsDco();
//		userGroup.createUserGroups("Administrator", berechtigungListe);
//		List<UserGroups> userGroupList = new ArrayList<UserGroups>();
//		BasicDao<UserGroups> userGroupsDao = new BasicDao<UserGroups>(UserGroups.class);
//		userGroupList.add(userGroupsDao.findById(1));
//		User userBerechtigungTest = new User();
//		userBerechtigungTest.setFirstName("Thomas");
//		userBerechtigungTest.setLastName("Ehlers");
//		userBerechtigungTest.setKennung("abc123");
//		userBerechtigungTest.setTelefon("0406035322");
//		userBerechtigungTest.seteMail("thomas.ehlers@haw.de");
//		userBerechtigungTest.setMemberOfGroups(userGroupList);
//		
//		BasicDao<User> useruserBerechtigungTestDao = new BasicDao<User>(User.class);
//		useruserBerechtigungTestDao.create(userBerechtigungTest);
//		
//		
//		List<Reservierung> reservierung = new ArrayList<Reservierung>();
//		Artikel test = artDao.findArtikelWithReservierungById(1);
////		Hibernate.initialize(test.getReservierungen());
//		reservierung = test.getReservierungen();
//		System.out.println("HibernateApp.main()"+reservierung.size());
//		
		Hash hash = new Hash();
		String hashValue = hash.generateMD5Hash("alex");
		System.out.println(hashValue);
	}

}
