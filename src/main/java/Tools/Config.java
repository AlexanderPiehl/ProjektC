//Version : 1.1.1
//Author: Listener
//Description: Config Reader - specifies Function for known and unknown Elements

package Tools;
import java.io.FileInputStream;
import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.Properties;

public class Config {
	
	
	private Properties properties;
	private Tools tools;
	public Config() throws FileNotFoundException{
		this.tools = new Tools();
		this.properties = new Properties();
		BufferedInputStream stream = new BufferedInputStream(new FileInputStream("src/main/resources/config.properties"));
		try {
			this.properties.load(stream);
//			System.out.println("### Debug Properties ###");
//			System.out.println(this.properties.toString());
//			System.out.println(this.getProperty_db_port());
//			System.out.println("### Debug Properties End ###");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			stream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getProperty(String propertyName){
		return (String) this.properties.getProperty(propertyName);
	}
	

	public String getProperty_db_user(){
		return (String) this.properties.getProperty("db_user");
	}
	
	public String getProperty_db_password(){
		return (String) this.properties.getProperty("db_password");
	}
	
	public String getProperty_db_table(){
		return (String) this.properties.getProperty("db_table");
	}
	public String getProperty_db_host(){
		
		String db_hostTemp = (String) this.properties.getProperty("db_host");
		String db_portTemp = (String) this.properties.getProperty("db_port");
		Integer db_portInt = Integer.parseInt(db_portTemp);
//		if(this.tools.isReachable(db_hostTemp, db_portInt, 5000)){
//			return (String) this.properties.getProperty("db_host");
//		}
//		else{
//			return "127.0.0.1";
//		}
		return "127.0.0.1";

		
	}
	public String getProperty_db_charset(){
		return (String) this.properties.getProperty("db_charset"); //= UTF-8
	}					
	public String getProperty_db_port(){
		
		String db_hostTemp = (String) this.properties.getProperty("db_host");
		String db_portTemp = (String) this.properties.getProperty("db_port");
		Integer db_portInt = Integer.parseInt(db_portTemp);
		if(this.tools.isReachable(db_hostTemp, db_portInt, 5000)){
			return (String) this.properties.getProperty("db_port");
		}
		else{
			return "3306";
		}
		
		
		
	}						
	
	public boolean getProperty_seLDAP_ENABLED(){
		
	if (this.properties.getProperty("seLDAP_ENABLED").toString().equalsIgnoreCase("true")){
			return true;
		}else{
			return false;
		}
	} 			
	
	//seLDAP_DEBUGLOGIN
	public boolean getProperty_seLDAP_DEBUGLOGIN(){
		
		if (this.properties.getProperty("seLDAP_DEBUGLOGIN").toString().equalsIgnoreCase("true")){
				return true;
			}else{
				return false;
			}
		}
	public String getProperty_seLDAP_SERVER_ADDRESS(){
		return (String) this.properties.getProperty("seLDAP_SERVER_ADDRESS"); //= 192.168.145.20
	} 		
	
	public String getProperty_seLDAP_SERVER_PORT(){
		return (String) this.properties.getProperty("seLDAP_SERVER_PORT"); //= null
	} 			
	
	public String getProperty_seLDAP_BASE_DIRECTORY(){
		return (String) this.properties.getProperty("seLDAP_BASE_DIRECTORY"); //= DC=srg,DC=local
	} 		
	
	public String getProperty_seLDAP_DN(){
		return (String) this.properties.getProperty("seLDAP_DN"); //= SRG
	}
	
	public String getProperty_seLDAP_USER_DOMAIN(){
		return (String) this.properties.getProperty("seLDAP_USER_DOMAIN"); //= @srg.local
	} 			
	
	public String getProperty_seLDAP_StandardUserName(){
		return (String) this.properties.getProperty("seLDAP_StandardUserName");
	} 	 
	
	public String getProperty_seLDAP_StandardUserPassword(){
		return (String) this.properties.getProperty("seLDAP_StandardUserPassword");
	}  
	
	public String getProperty_seDefaultPageTitle(){
		return (String) this.properties.getProperty("seDefaultPAgeTitle"); //= Ausleihe
	}			
	public String getProperty_seUrlRootPath(){
		return (String) this.properties.getProperty("seUrlRootPath"); //= http://examlple.com/
	}				
	
	public String getProperty_seCopyrightText(){
		return (String) this.properties.getProperty("seCopyrightText"); //=(c)opyright by example, 2014
	}				
	
	public Integer getProperty_seSessionExpires(){
		return Integer.parseInt((String) this.properties.getProperty("seSessionExpires")); //=10
	}			
	public Integer getProperty_seSessionDeleteTime(){
		return Integer.parseInt((String) this.properties.getProperty("seSessionDeleteTime")); //=172800
	}			
	
	public Integer getProperty_seUploadFileMaxSize(){
		return Integer.parseInt((String) this.properties.getProperty("seUploadFileMaxSize")); //=20000000
	}			
	public String getProperty_seUploadFileDestination(){
		return (String) this.properties.getProperty("seUploadFileDestionation"); //=./public/uploads/
	}		
	
	public String getProperty_seEmailSenderMail(){
		return (String) this.properties.getProperty("seEmailSenderMail"); //=sender@example.com
	}			
	public String getProperty_seEmailSenderName(){
		return (String) this.properties.getProperty("seEmailSenderName"); //Absender Name =sendername
	}			


	}
	
