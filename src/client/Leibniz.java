package client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

<<<<<<< cd75430ee50d709b7a96b7b826c03f67c377c792
public class Leibniz extends UnicastRemoteObject implements InterfaceLeibniz{
		
	protected Leibniz() throws RemoteException {
		super();
		System.out.println("Marcelo curte pirocas!");
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
=======
class Leibniz extends UnicastRemoteObject implements InterfaceLeibniz {
    //private static long tempoInicio = System.currentTimeMillis();
    //private static int number;

    //double pi = computePi(number);
    //System.out.println(pi);
    //System.out.println("Tempo Total: " + (System.currentTimeMillis() - tempoInicio));


    public Leibniz() throws RemoteException {
        super();
    }

    @Override
    public double calc(double n) throws RemoteException {
        // TODO Auto-generated method stub
        double pi = 0;

        for (int i = 0; i < n; i++) {
            pi += Math.pow(-1, i) / (2 * i + 1);
        }
        return pi * 4;
    }
>>>>>>> Reformatando código
}
