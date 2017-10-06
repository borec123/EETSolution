package cz.borec.demo.rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;

import cz.borec.demo.AppProperties;

public class RMIClient {

	private static ObserverRMIInterface look_up;

	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {
		//System.setSecurityManager(new SecurityManager());
		look_up = (ObserverRMIInterface) Naming.lookup("//localhost/RMIServer");

		look_up.update();
		System.out.println("Done.");
	}
	
	public static void notifyRMIListeners() {
		String[] listeners = AppProperties.getProperties().getRmiListeners();
		for (String string : listeners) {
			notifyListener(string);
		}
	}
	
	
	private static void notifyListener(String string) {
		ObserverRMIInterface look_up;
		
		try {
			String[] s = string.split(":");
			look_up = (ObserverRMIInterface) Naming.lookup("//" + s[0] + "/" + s[1]);
			look_up.update();
		} 
		
		catch (Exception e) {
			e.printStackTrace();
		}
	}


	
}
