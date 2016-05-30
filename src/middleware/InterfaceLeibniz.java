package middleware;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InterfaceLeibniz extends Remote {
    double calc(Integer iterations, Integer threads) throws RemoteException;
}
