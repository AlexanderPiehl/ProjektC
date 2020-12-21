package Tools;

import java.io.FileNotFoundException;

public class LDAPTest {
	public LDAPTest() throws FileNotFoundException{
		
	
	LDAP LDAPConnection = new LDAP();
	if(
	LDAPConnection.authentify("LDdfgfdghAP", "Passwort11234")){
		System.out.println("LDAP Authentifiziert");
	}else{
		System.out.println("LDAP FEHLGESCHLAGEN");
	}
	System.out.println("##### END LDAP####");
	}
 
}
