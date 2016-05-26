package client;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InterfaceLeibnz extends Remote {
	
	public double calc(double n)throws RemoteException;

}
