package middleware;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Leibniz  extends UnicastRemoteObject implements client.InterfaceLeibniz {
    private static long tempoInicio;
    private static Integer porta = 2040;

    protected Leibniz() throws RemoteException {
    }

    @Override
    public double calc(Integer iterations, Integer threads) throws RemoteException {
        tempoInicio = System.currentTimeMillis();

        middleware.InterfaceLeibniz leibniz;

        try {
            leibniz = (middleware.InterfaceLeibniz) Naming.lookup("rmi://localhost:" + porta.toString() + "/Leibniz");

            System.out.println("Tempo Total: " + (System.currentTimeMillis() - tempoInicio));

            return leibniz.calc(iterations, threads);
        } catch (NotBoundException e) {
            System.out.println("Ocorreu um erro com a porta. Verifique seu firewall.");
        } catch (MalformedURLException e) {
            System.out.println("Ocorreu um erro ao conectar ao servidor. Verifique a URL.");
        } catch (RemoteException e) {
            System.out.println("Ocorreu um erro no servidor.");
        }

        return 0d;
    }
}
