package middleware;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

class LeibnizWorker implements Runnable {
    private final long tempoInicio;
    private final Integer iterations;
    private final Integer threads;
    private final int serverId;
    private final double[] result;
    private final LeibnizServer server;

    public LeibnizWorker(long tempoInicio, Integer iterations, Integer threads, int serverId, double[] result, LeibnizServer server) {
        this.tempoInicio = tempoInicio;
        this.iterations = iterations;
        this.threads = threads;
        this.serverId = serverId;
        this.result = result;
        this.server = server;
    }

    @Override
    public void run() {
        middleware.InterfaceLeibniz leibniz;

        try {
            String URI = "rmi://" + InetAddress.getLocalHost().getHostAddress() + ":2230/Leibniz";
            leibniz = (middleware.InterfaceLeibniz) Naming.lookup(URI);

            System.out.println("Tempo Total: " + (System.currentTimeMillis() - tempoInicio));

            result[serverId] = leibniz.calc(iterations, threads);
        } catch (NotBoundException e) {
            System.out.println("Ocorreu um erro com a porta. Verifique seu firewall.");
        } catch (MalformedURLException e) {
            System.out.println("Ocorreu um erro ao conectar ao servidor. Verifique a URL.");
        } catch (RemoteException e) {
            System.out.println("Ocorreu um erro no servidor.");
        } catch (UnknownHostException e) {
            System.out.println("Erro ao conectar ao servidor " + server.getAddress());
        }
    }
}
