package cz.borec.demo.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ObserverRMIInterface extends Remote {
	
	public void update()  throws RemoteException ;

}
