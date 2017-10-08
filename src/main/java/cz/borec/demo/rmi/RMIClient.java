package cz.borec.demo.rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
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
		Runnable r = new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String[] listeners = AppProperties.getProperties().getRmiListeners();
				for (String string : listeners) {
					notifyListener(string);
				}
			}
		};
		new Thread(r).start();
	}
	
	
	private static void notifyListener(String string) {
		ObserverRMIInterface look_up;
		
		try {
			String[] s = string.split(":");
			String n = "//" + s[0] + "/" + s[1];
			System.out.println("Look up: " + n);
			look_up = (ObserverRMIInterface) Naming.lookup(n);
			look_up.update();
		} 
		
		catch (Exception e) {
			e.printStackTrace();
		}
	}


	
}
