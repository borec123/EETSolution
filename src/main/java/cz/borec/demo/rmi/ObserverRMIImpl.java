package cz.borec.demo.rmi;

import java.net.InetAddress;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import javax.naming.Context;

import cz.borec.demo.Constants;
import cz.borec.demo.gui.controls.AppPropertiesProxy;
import cz.borec.demo.sound.SoundPlayer;

public class ObserverRMIImpl extends UnicastRemoteObject implements ObserverRMIInterface {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7982663773621352599L;

	public ObserverRMIImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update()  throws RemoteException {
		//SoundPlayer.playSound();
		String cash_id = "cash_" + AppPropertiesProxy.get(Constants.CONFIG_CASH_ID);
		System.out.println("chuj " + cash_id);

	}
	
	public static void main(String[] args) {
		String codeBasePath = "file:/./target/classes";

		String hostname = "Unknown";

		try
		{
		    InetAddress addr;
		    addr = InetAddress.getLocalHost();
		    hostname = addr.getHostName();
		
/*			System.setProperty("java.rmi.server.codebase", codeBasePath);
			System.setProperty("java.rmi.server.hostname", hostname);
			System.setProperty("java.security.policy", "server.policy");
			System.setProperty(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.rmi.registry.RegistryContextFactory");
			System.setProperty(Context.PROVIDER_URL, "rmi://localhost:1099"); 
*/
			
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

	}

}
