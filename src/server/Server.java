package server;

import java.io.IOException;
import java.net.*;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

class Server extends Thread {
    private static Registry registry;
    private final Integer porta = 2230;

    public static void main(String args[]) throws IOException {
        System.setProperty("java.rmi.server.hostname", InetAddress.getLocalHost().getHostAddress());

        registry = LocateRegistry.createRegistry(2230);

        new Server().start();
    }

    public Server() {
    }

    @Override
    public void run() {
        super.run();

        String msg = "SUP!!!";

        initServer();

        InetAddress group = null;
        try {
            group = InetAddress.getByName("224.2.2.224");
        } catch (UnknownHostException e) {
            System.out.println("Ocorreu um erro ao iniciar o multicast");
        }

        if (null == group) throw new AssertionError();

        MulticastSocket multicastSocket = null;
        Integer multicastPort = 2240;
        try {
            multicastSocket = new MulticastSocket(multicastPort);
            multicastSocket.joinGroup(group);
        } catch (IOException e) {
            System.out.println("Ocorreu um erro ao escutar na porta " + multicastPort.toString());
        }

        if (null == multicastSocket) throw new AssertionError();
        DatagramPacket myAddress = new DatagramPacket(msg.getBytes(), msg.length(), group, multicastPort);
        try {
            multicastSocket.send(myAddress);
        } catch (IOException e) {
            System.out.println("Ocorreu um erro ao enviar o IP deste servidor!");
        }
    }

    private void initServer() {
        Leibniz leibniz;

        try {
            LocateRegistry.createRegistry(porta);
            leibniz = new Leibniz();
            registry.bind("Leibniz", leibniz);
        } catch (RemoteException re) {
            System.out.println("Ocorreu um erro ao iniciar a classe de cálculo!");
        } catch (AlreadyBoundException e) {
            System.out.println("Já existe um servidor rodando nesse host.");
        }
    }
}
