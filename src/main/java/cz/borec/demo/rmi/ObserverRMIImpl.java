package cz.borec.demo.rmi;

import java.net.InetAddress;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Observable;
import java.util.Observer;

import javax.naming.Context;

import cz.borec.demo.Constants;
import cz.borec.demo.gui.controls.AppPropertiesProxy;
import cz.borec.demo.sound.SoundPlayer;

public class ObserverRMIImpl extends UnicastRemoteObject implements ObserverRMIInterface {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7982663773621352599L;
	private static ObserverRMIImpl instance;
	private MyObservable observable;

	public ObserverRMIImpl() throws RemoteException {
		super();
		observable = new MyObservable();
	}
	
	public void addObserver(Observer o) {
		observable.addObserver(o);
	}

	@Override
	public void update()  throws RemoteException {
		//SoundPlayer.playSound();
		String cash_id = "cash_" + AppPropertiesProxy.get(Constants.CONFIG_CASH_ID);
		System.out.println("chuj " + cash_id);

		observable.myNotifyObservers();
	}
	
	public static ObserverRMIImpl getInstance() {
		if(instance == null) {
			try {
				instance = new ObserverRMIImpl();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return instance;
		
	}
	
/*	public static void main(String[] args) {
		String codeBasePath = "file:/./target/classes";

		String hostname = "Unknown";

		try
		{
		    InetAddress addr;
		    addr = InetAddress.getLocalHost();
		    hostname = addr.getHostName();
		

			
		    Registry registry = LocateRegistry.createRegistry(1099);

			ObserverRMIImpl server = new ObserverRMIImpl();
			Naming.rebind("RMIServer", server);

			//ObserverRMIImpl stub = (ObserverRMIImpl) UnicastRemoteObject.exportObject(server,1099);
			//registry.rebind ("dataSource", stub); 
			
			//registry.rebind("//localhost/RMIServer", server);
			
			

			System.out.println("Server ready");	
		}
		
		catch (Exception e) {
			
			e.printStackTrace();
		}

	}*/

}
