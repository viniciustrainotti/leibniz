package client;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

class Client {
    public static void main(String[] args) {
        long tempoInicio = System.currentTimeMillis();
        Integer iterations = 500000000;
        Integer threads = 32;

        client.InterfaceLeibniz leibniz;

        try {
            String URI = "rmi://" + InetAddress.getLocalHost().getHostAddress() + ":2220/LeibnizMiddleware";
            leibniz = (client.InterfaceLeibniz) Naming.lookup(URI);

            double ourPi = leibniz.calc(iterations, threads);
            System.out.println(ourPi);
            System.out.println(getErro(ourPi));
        } catch (NotBoundException e) {
            System.out.println("Ocorreu um erro com a porta. Verifique seu firewall.");
            e.printStackTrace();
        } catch (MalformedURLException e) {
            System.out.println("Ocorreu um erro ao conectar ao servidor. Verifique a URL.");
        } catch (RemoteException e) {
            System.out.println("Ocorreu um erro no servidor.");
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        System.out.println("Tempo Total: " + (System.currentTimeMillis() - tempoInicio));
    }

    private static String getErro(double ourPi) {
        return String.format("Erro: %f %%", (Math.PI - ourPi)/Math.PI * 100);
    }
}
