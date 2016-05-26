package client;

import java.io.IOException;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class Servidor extends Thread {
    private static Integer porta = 2020;

    public static void main(String args[]) throws IOException {
        LocateRegistry.createRegistry(porta);
        
        new Servidor().start();
    }

    @Override
    public void run() {
        super.run();

        try {
            Leibniz l = new Leibniz();
            Naming.rebind("//localhost:" + porta.toString() + "/Leibniz", l);
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }
}
