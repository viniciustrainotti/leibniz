package middleware;

import client.*;
import server.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Server extends Thread {
    private static Integer porta = 2020;

    public static void main(String args[]) throws IOException {
        LocateRegistry.createRegistry(porta);

        new middleware.Server().start();
    }

    @Override
    public void run() {
        super.run();

        middleware.Leibniz leibniz;

        try {
            leibniz = new middleware.Leibniz();
            System.out.println("Porta: " + porta.toString());
            Naming.rebind("//localhost:" + porta.toString() + "/Leibniz", leibniz);
        } catch (RemoteException re) {
            System.out.println("Ocorreu um erro ao iniciar a classe de cálculo!");
        } catch (MalformedURLException e) {
            System.out.println("Verifique a URL de binding.");
        }
    }
}
