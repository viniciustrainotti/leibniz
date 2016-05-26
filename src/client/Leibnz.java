package client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Leibnz extends UnicastRemoteObject implements InterfaceLeibnz{
	
	//private static long tempoInicio = System.currentTimeMillis();
	//private static int number;
	
		//double pi = computePi(number); 
	    //System.out.println(pi);
	    //System.out.println("Tempo Total: " + (System.currentTimeMillis() - tempoInicio));
	
	
	protected Leibnz() throws RemoteException {
		super();
	}

	@Override
	public double calc(double n) throws RemoteException {
		// TODO Auto-generated method stub
		double pi = 0;
		
		for (int i = 0; i < n; i++) 
		{
			pi += Math.pow(-1,i)/(2*i+1);
		}
		return pi * 4;
	}
}
