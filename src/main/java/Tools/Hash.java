package Tools;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hash {
	//MD5 Hash
	public String generateMD5Hash(String value){
		String md5Hash = "";
		try{
			MessageDigest md = MessageDigest.getInstance("MD5");
			StringBuilder sb = new StringBuilder();
			
			md.update(value.getBytes());
			byte[] bytes = md.digest();
			
			for(int i=0; i< bytes.length ;i++){
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			md5Hash = sb.toString();
		}catch(NoSuchAlgorithmException ex){
			ex.printStackTrace();
		}	
		return md5Hash;
	}
}
