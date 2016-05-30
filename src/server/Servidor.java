package server;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Servidor extends Thread {
    private static Integer porta = 2040;

    public static void main(String args[]) throws IOException {
        LocateRegistry.createRegistry(porta);
        
        new Servidor().start();

    @Override
    public void run() {
        super.run();

        Leibniz leibniz;

        try {
            leibniz = new Leibniz();
            Naming.rebind("//localhost:" + porta.toString() + "/Leibniz", leibniz);
           
        } catch (RemoteException re) {
            System.out.println("Ocorreu um erro ao iniciar a classe de cálculo!");
            re.printStackTrace();
        } catch (MalformedURLException e) {
            System.out.println("Verifique a URL de binding.");
        } 
    }
}
