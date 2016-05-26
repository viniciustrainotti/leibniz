package client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Leibniz extends UnicastRemoteObject implements InterfaceLeibniz{

	private static final long serialVersionUID = 1L;

	protected Leibniz() throws RemoteException {
		
	}

	@Override
	public double calc(double n) throws RemoteException {
		
		double pi = 0;
		
		for (int i = 0; i < n; i++) 
		{
			pi += Math.pow(-1,i)/(2*i+1);
		}
		return pi * 4;
	}
	// Teste
}
