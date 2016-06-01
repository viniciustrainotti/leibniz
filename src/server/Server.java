package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

class Server extends Thread {
    private static Registry registry;
    private static final Integer porta = 2230;

    public static void main(String args[]) throws IOException, InterruptedException {
        System.setProperty("java.rmi.server.hostname", InetAddress.getLocalHost().getHostAddress());

        registry = LocateRegistry.createRegistry(porta);

        Server s = new Server();
        s.start();

        // s.multicast("KTHXBAI");
    }

    private Server() {
    }

    @Override
    public void run() {
        super.run();

        String msg = "SUP!!!";

        initServer();

        multicast(msg);
    }

    private void multicast(String msg) {
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
            leibniz = new Leibniz();
            registry.bind("Leibniz", leibniz);
        } catch (RemoteException re) {
            System.out.println("Ocorreu um erro ao iniciar a classe de cálculo!");
        } catch (AlreadyBoundException e) {
            System.out.println("Já existe um servidor rodando nesse host.");
        }
    }
}
