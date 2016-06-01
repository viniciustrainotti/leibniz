package middleware;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Objects;

class Server extends Thread {
    private static Registry registry;

    public static void main(String args[]) throws IOException {
        System.setProperty("java.rmi.server.hostname", InetAddress.getLocalHost().getHostAddress());
        registry = LocateRegistry.createRegistry(2220);

        new middleware.Server().start();
    }

    @Override
    public void run() {
        super.run();

        middleware.Leibniz leibniz = null;

        try {
            leibniz = new Leibniz();
        } catch (RemoteException e) {
            System.out.println("Ocorreu um erro ao iniciar a classe de cálculo!");
            e.printStackTrace();
        }
        try {
            registry.bind("LeibnizMiddleware", leibniz);
        } catch (RemoteException re) {
            System.out.println("Ocorreu um erro ao registrar o serviço.");
            re.printStackTrace();
            System.exit(1);
        } catch (AlreadyBoundException e) {
            System.out.println("Ocorreu um erro ao iniciar o serviço.");
            System.exit(1);
        }

        InetAddress group = null;
        try {
            group = InetAddress.getByName("224.2.2.224");
        } catch (UnknownHostException e) {
            System.out.println("Ocorreu um erro ao iniciar o multicast");
            System.exit(1);
        }

        MulticastSocket multicastSocket = null;
        Integer multicastPort = 2240;
        try {
            multicastSocket = new MulticastSocket(multicastPort);
            multicastSocket.joinGroup(group);
        } catch (IOException e) {
            System.out.println("Ocorreu um erro ao escutar na porta " + multicastPort.toString());
            System.exit(1);
        }

        final MulticastSocket finalMulticastSocket = multicastSocket;
        final Leibniz finalLeibniz = leibniz;

        new Thread(new Runnable() {
            @Override
            public void run() {
                String msg;
                //noinspection InfiniteLoopStatement
                while (true) {
                    byte[] buf = new byte[1000];
                    DatagramPacket packet = new DatagramPacket(buf, buf.length);

                    try {
                        finalMulticastSocket.receive(packet);
                    } catch (IOException e) {
                        System.out.println("Ocorreu um erro ao ler o multicast");
                    }

                    byte[] data = new byte[packet.getLength()];
                    System.arraycopy(packet.getData(), packet.getOffset(), data, 0, packet.getLength());

                    msg = new String(data);

                    assert finalLeibniz != null;

                    if (Objects.equals(msg, "SUP!!!")) {
                        System.out.println("Servidor encontrado: " + packet.getAddress());
                        finalLeibniz.addServer(packet.getAddress());
                    } else if (Objects.equals(msg, "KTHXBAI")) {
                        System.out.println("Servidor encerrado: " + packet.getAddress());
                        finalLeibniz.removeServer(packet.getAddress());
                    }
                }
            }
        }).start();
    }
}
