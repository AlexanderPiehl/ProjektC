package haw.Ausleihe;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;



import Database.Entities.Artikel;

public final class MySession extends WebSession {
	private static final long serialVersionUID = 1L;
	
	private String userID;
	private boolean isLoggedIn;
	private ArrayList<Artikel> listCart = new ArrayList<Artikel>();
	private Object artieklObject;
	private Long reservierungsId;
	private ArrayList<Artikel> listSuche = new ArrayList<Artikel>();
	private boolean wirdGesucht;
	private ArrayList<Artikel> listVerliehen = new ArrayList<Artikel>();

	public MySession(Request request) {
		super(request);
		// TODO Auto-generated constructor stub
	}
	//artikelObject
	public final Object getMyObject() {
		return artieklObject;
	}
	public void setArtikelID(Object artieklObject) {
		this.artieklObject = artieklObject;
		putIntoList();
	}
	//ListID
	public final ArrayList<Artikel> getMylistCart() {
		return listCart;
	}
	public void setMyListID(ArrayList<Artikel> listCart) {
		this.listCart = listCart;
	}
	 		
	 		 
	 public void setLoggedin(String userID){
		 this.isLoggedIn = true;
		 this.userID = userID;
	 }
	 public void removeLogin(){
		 this.isLoggedIn = false;
		 this.userID = null;
		 clear();
	 }
	 public boolean checkIfLoggedIn(){
		 return this.isLoggedIn;
	 }
	 public final String getLoggedinID(){
		 return this.userID;
	 }
	 
	 public static MySession get() {
	        return (MySession) Session.get();	
	    }
	 
	 //füngt objekt zur warenkorb
	 public void putIntoList(){
		 listCart.add((Artikel) artieklObject);
	 }
	 
	 //loesche ein bestimmtes objekt aus dem Warenkorb
	 public void removeFromCart(Object cart){
		 listCart.remove(cart);
	 }
	 
	 public void emptyCart(){
		 listCart.removeAll(listCart);
	 }
	 
	public Long getReservierungsId() {
		return reservierungsId;
	}
	public void setReservierungsId(Long reservierungsId) {
		this.reservierungsId = reservierungsId;
	}
	
	//Liste des Suchergebniss
	public ArrayList<Artikel> getListSuche() {
		return listSuche;
	}
	public void setListSuche(List<Artikel> foundArtikel) {
		this.listSuche = (ArrayList<Artikel>) foundArtikel;
	}
	
	//true false für suche
	public boolean isWirdGesucht() {
		return wirdGesucht;
	}
	public void setWirdGesucht(boolean wirdGesucht) {
		this.wirdGesucht = wirdGesucht;
	}
	
	//List der Veerliehenden objekte aus den Warenkorb
	public ArrayList<Artikel> getListVerliehen() {
		return listVerliehen;
	}
	public void setListVerliehen(ArrayList<Artikel> listVerliehen) {
		this.listVerliehen = listVerliehen;
	}
}
