package haw.Ausleihe;

import org.apache.wicket.Session;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;

/**
 * Application object for your web application.
 * If you want to run this application without deploying, run the Start class.
 * 
 * @see haw.Start#main(String[])
 */
public class WicketApplication extends WebApplication
{
	/**
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	@Override
	public Class<? extends WebPage> getHomePage()
	{
		//return HomePage.class;
		return Login.class;
		//return produktNeu.class;
	}

	/**
	 * @see org.apache.wicket.Application#init()
	 */
	@Override
	public void init()
	{
		super.init();
		mountPage("home", getHomePage());
        mountPage("buchungen", Buchungen.class);
        mountPage("graueListe", GraueListe.class);
        mountPage("impressum", Impressum.class);
        mountPage("userVerwaltung", UserHp.class);
        mountPage("berechtigungenSetzen", BerechtigungSetzen.class);
        mountPage("buchungHistorie", BuchungHistorie.class);
        mountPage("homePage", HomePage.class);
        mountPage("impressum", Impressum.class);
        mountPage("login", Login.class);
        mountPage("produktEdit", produktEdit.class);
        mountPage("produktNeu", produktNeu.class);
        mountPage("registrierung", Registrierung.class);
        mountPage("reservierungBearbeiten", ReservierungBearbeiten.class);
        mountPage("reservierungHP", ReservierungHP.class);
        mountPage("sanktionslisteNeu", SanktionslisteNeu.class);
        mountPage("schwarzeListe", SchwarzeListe.class);
        mountPage("userHp", UserHp.class);
        mountPage("warenkorb", warenkorb.class);
        mountPage("wartungHP", WartungHP.class);

		// add your configuration here
	}
	
	 @Override
	 public final Session newSession(Request request, Response response) {
		 return new MySession(request);
	 }
}
