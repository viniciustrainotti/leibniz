package middleware;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

class LeibnizServer implements InterfaceLeibniz {
    private final InetAddress address;

    public LeibnizServer(InetAddress address) {
        this.address = address;
    }

    @Override
    public double calc(Integer iterations, Integer threads) throws RemoteException {
        long tempoInicio = System.currentTimeMillis();

        middleware.InterfaceLeibniz leibniz;

        try {
            leibniz = (middleware.InterfaceLeibniz) Naming.lookup("rmi://" + this.address.getHostAddress() + "/Leibniz");

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

    public InetAddress getAddress() {
        return address;
    }
}
