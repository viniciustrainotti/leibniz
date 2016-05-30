package client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;


public class Client {

    private static long tempoInicio;
    private static Integer porta = 2020;

    public static void main(String[] args) {
        tempoInicio = System.currentTimeMillis();
        Integer iterations = 5000000;
        Integer threads = 32;

        client.InterfaceLeibniz leibniz;

        try {
            leibniz = (client.InterfaceLeibniz) Naming.lookup("rmi://localhost:" + porta.toString() + "/Leibniz");

            System.out.println(leibniz.calc(iterations, threads));
        } catch (NotBoundException e) {
            System.out.println("Ocorreu um erro com a porta. Verifique seu firewall.");
            e.printStackTrace();
        } catch (MalformedURLException e) {
            System.out.println("Ocorreu um erro ao conectar ao servidor. Verifique a URL.");
        } catch (RemoteException e) {
            System.out.println("Ocorreu um erro no servidor.");
        }

        System.out.println("Tempo Total: " + (System.currentTimeMillis() - tempoInicio));
    }
}
