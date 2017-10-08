package cz.borec.demo.rmi;

import java.util.Observable;

public class MyObservable extends Observable {

	
	public void myNotifyObservers() {
		setChanged();
		super.notifyObservers();
	}

}
