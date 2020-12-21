package Tools;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Tools {
	
	
	public boolean isReachable(String host, Integer port, Integer timeOut){
		Socket socket = null;
		boolean reachable = false;
		try {
		    try {
				socket = new Socket(host, port);
				reachable = true;
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   
		} finally {            
		    if (socket != null) try { socket.close(); } catch(IOException e) {}
		}
		return reachable;
	}

}
