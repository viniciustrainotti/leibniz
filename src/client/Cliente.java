package client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Cliente {
    private static long tempoInicio;
    private static Integer porta = 2020;

    public static void main(String[] args) {
        tempoInicio = System.currentTimeMillis();

        InterfaceLeibniz leibniz;

        try {
            leibniz = (InterfaceLeibniz) Naming.lookup("rmi://localhost:" + porta.toString() + "/Leibniz");

            System.out.println(leibniz.calc(5000000));
        } catch (NotBoundException e) {
            System.out.println("Ocorreu um erro com a porta. Verifique seu firewall.");
        } catch (MalformedURLException e) {
            System.out.println("Ocorreu um erro ao conectar ao servidor. Verifique a URL.");
        } catch (RemoteException e) {
            System.out.println("Ocorreu um erro no servidor.");
        }

        System.out.println("Tempo Total: " + (System.currentTimeMillis() - tempoInicio));
    }
}
