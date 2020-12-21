package Tools;

import javax.naming.*;
import javax.naming.directory.*;

import java.io.FileNotFoundException;
import java.util.Hashtable;


public class LDAP {
	boolean LDAP_ENABLED;
	boolean LDAP_DEBUGLOGIN;
	String LDAPBaseDirectory;
	String LDAP_SERVER_ADDRESS;
	String LDAP_SERVER_PORT;
	String LDAP_USER_DOMAIN;
	String LDAP_DN;
	String LDAP_StandardUserName;
	String LDAP_StandardUserPassword;
	public LDAP(){ //depends on Config Class
		//Import settings from Config
		try{
	
			Config config = new Config();

			this.LDAP_ENABLED = config.getProperty_seLDAP_ENABLED();
			if(this.LDAP_ENABLED){
				this.LDAP_DEBUGLOGIN = config.getProperty_seLDAP_DEBUGLOGIN();
				this.LDAPBaseDirectory = config.getProperty_seLDAP_BASE_DIRECTORY();
				this.LDAP_SERVER_ADDRESS = config.getProperty_seLDAP_SERVER_ADDRESS();
				this.LDAP_SERVER_PORT = config.getProperty_seLDAP_SERVER_PORT();
				this.LDAP_USER_DOMAIN = config.getProperty_seLDAP_USER_DOMAIN();
				this.LDAP_DN = config.getProperty_seLDAP_DN();
				this.LDAP_StandardUserName = config.getProperty_seLDAP_StandardUserName();
				this.LDAP_StandardUserPassword = config.getProperty_seLDAP_StandardUserPassword();
				
				
			}
		} catch (FileNotFoundException e){
			//todo 
		}
		
		
	}
	
	public boolean authentify(String userName, String userPassword){
		if(this.LDAP_DEBUGLOGIN){
			return true;
		}
		System.out.println(userPassword);
		//LDAP responses with "true" if password == null
		if(userPassword.equals("")){
			return false;
		}
		
		/**
		 * TODO Add availability check for LDAP Server
		 * 
		 */
		try
	    {
			System.out.println("Trying LDAP");
	        // Set up the environment for creating the initial context
	        Hashtable<String, String> env = new Hashtable<String, String>();
	        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
	        String ldapURL = "ldap://" + this.LDAP_SERVER_ADDRESS + ":" +this.LDAP_SERVER_PORT;
	        System.out.println("URL: "+ ldapURL);
	        env.put(Context.PROVIDER_URL, ldapURL);
	        // 
	        env.put(Context.SECURITY_AUTHENTICATION, "simple");
	        env.put(Context.SECURITY_PRINCIPAL, this.LDAP_DN +"\\"+ userName ); //"domain\\user"); 
	        System.out.println("Principal: "+ this.LDAP_DN +"\\"+ userName ); //DEBUG
	        env.put(Context.SECURITY_CREDENTIALS, userPassword);
	        System.out.println("Password: "+ userPassword ); //DEBUG
	        
	        // Create the initial context

	        DirContext ctx = new InitialDirContext(env);
	        boolean result = (ctx != null);

//	        if(ctx != null)
	            ctx.close();
	        System.out.println("Result: " + result);
	        
//	        return result;
	        if(result){
	        	
	        	return true;
	        }else{
	        	return false;
	        }
	    }
	    catch (Exception e)
	    {          
	    	System.out.println(e.getStackTrace());
	    	e.printStackTrace();
	        return false;
	    }
	}
			
			

//			OWN seLDAPController Class (PHP) using as example...			
//			    protected $bind = NULL;
//			    protected $ldap_connection = NULL;
//			    protected $lastFind = NULL;
//
//
//			    public function bindLdapStandardUser() {
//			        if (seLDAP_StandardUserName != '') $this->login(seLDAP_StandardUserName, seLDAP_StandardUserPassword);
//			    }
//			    /**
//			     *
//			     * @param string $userName
//			     * @param string $userPassword
//			     * @return boolean
//			     */
//			    public function login($userName, $userPassword) {
//			        // if no password exit here
//			        if (trim($userPassword) == '') return false;
//			        if ($this->connectLdap()){
//
//
//			            $bindResult =  $this->userBind($userName, $userPassword);
//			            return $bindResult;
//			        }else {
//			            return false;
//			        }
//			    }
//
//			    /**
//			     * Tries to bind user
//			     *
//			     * @param string $userName
//			     * @param string $userPassword
//			     * @return boolean
//			     */
//			    private function userBind($userName, $userPassword){
//			        set_error_handler('seLdapController_handleError');
//			        $this->bind = ldap_bind($this->ldap_connection, $userName . seLDAP_USER_DOMAIN, $userPassword);
//			        restore_error_handler();
//			        return $this->bind;
//			    }
//
//			    /**
//			     * Tries to connect to ldap
//			     */
//			    private function connectLdap(){
//			        $this->ldap_connection = ldap_connect(seLDAP_SERVER_ADDRESS, seLDAP_SERVER_PORT);
//			        return $this->ldap_connection;
//			    }
//
//
//			    public function find($filterArray, $filterParameters = NULL, $limitToArray = NULL){
//			        if ($this->ldap_connection) {
//			            //AND/OR Kombinationen koennen benutzt werden wenn $filterArray ein String ist -> dabei dann aeusserste klammern weglassen
//			            // $filterArrayParameters -> AND , OR  -> standard -> AND  >>> noetig?
//			            //$limitToArray bsp: array( "ou", "sn", "vorname", "mail");
//			            //$seLdap->find("&(objectClass=person)(objectClass=user)(badPwdCount>=1)");
//			            //$seLdap->find("sAMAccountName=dwarfex", NULL, array("sn"))
//
//			            if(is_string($filterArray)) $filterArray = array($filterArray);
//
//			            if(count($filterArray) > 1) {
//
//			                $filters = '(& (';
//
//			                if ($filterParameters == 'OR')
//			                    $filters ='(| (';
//
//			                foreach($filterArray as $filter)
//			                    $filters .= $filter . ') (';
//
//			                $filters .='))';
//
//			                if(isset($limitToArray))
//			                    $this->lastFind = ldap_search($this->ldap_connection, seLDAP_BASE_DIRECTORY, $filters, $limitToArray);
//			                else
//			                    $this->lastFind = ldap_search($this->ldap_connection, seLDAP_BASE_DIRECTORY, $filters);
//
//			            }
//			            else
//			            {
//			                // only one filter
//			                if(isset($limitToArray))
//			                    $this->lastFind = ldap_search($this->ldap_connection, seLDAP_BASE_DIRECTORY, '('.$filterArray['0'].')', $limitToArray);
//			                else
//			                    $this->lastFind = ldap_search($this->ldap_connection, seLDAP_BASE_DIRECTORY, "(".$filterArray['0'].")");
//
//			            }
//			            return true;
//			        } else return false;
//			    }
//
//			    /**
//			     * Look up users telephone and or email etc
//			     *
//			     * @param unknown $sAMAccountName
//			     * @param unknown $fieldname
//			     * @return unknown
//			     */
//			    private function getUserSpecificField($userName, $fieldname){
//			        if ($this->find("sAMAccountName=".$userName, NULL, array($fieldname))) {
//			            $entry = ldap_first_entry($this->ldap_connection, $this->lastFind);
//			            $value = ldap_get_values($this->ldap_connection, $entry, $fieldname);
//			            $value = $value['0'];
//			            echo $value;
//			            return $value;
//			        } else return null;
//			    }
//
//			    public function getUserLastName($sAMAccountName){
//			        return $this->getUserSpecificField($sAMAccountName, "sn");
//
//			    }
//			    public function getUserGivenName($sAMAccountName){
//			        return $this->getUserSpecificField($sAMAccountName, "givenName");
//			    }
//
//			    public function getUserMail($sAMAccountName){
//			        return $this->getUserSpecificField($sAMAccountName, "mail");
//			    }
//
//			    public function close() {
//			        if (isset($this->ldap_connection)) {
//			            try {
//			                ldap_close($this->ldap_connection);
//			            } catch (Exception $e) {
//			                // nothing
//			            }
//			        }
//			    }
//
//			    public function listLastResults(){
//			        $tempArray = array();
//			        $entryArray = array();
//			        $k = 0;
//			        if ($this->lastFind ==null) die("no search performed");
//			        $entry = ldap_first_entry($this->ldap_connection, $this->lastFind);
//			        $attrs = ldap_get_attributes($this->ldap_connection, $entry);
//
//			        for ($i=0; $i<$attrs["count"]; $i++){
//			            $merkmal = $attrs[$i];
//			            $values = ldap_get_values($this->ldap_connection, $entry, $merkmal);#
//			            $entryArray[$merkmal]=$values[0];
//			        }
//			        $tempArray[] = $entryArray;
//			        $k++;
//			        while($entry = ldap_next_entry($this->ldap_connection, $entry)){
//			            $attrs = ldap_get_attributes($this->ldap_connection, $entry);
//			            for ($i=0; $i<$attrs["count"]; $i++){
//			                $merkmal = $attrs[$i];
//			                $values = ldap_get_values($this->ldap_connection, $entry, $merkmal);#
//			                for($j=0; $j <$values["count"]; $j++){
//			                    $entryArray[$merkmal]=$values[0];
//			                }
//			            }
//			            $tempArray[] = $entryArray;
//			            $k++;
//			        }
////			         echo $k.' Entries<br />';
//			        return $tempArray;
//			    }
//
//
//
//			}
//
//			$seLdapController = new SeLdapController();
//			$seLdapController->bindLdapStandardUser();
//
//			?>
	
	
}
