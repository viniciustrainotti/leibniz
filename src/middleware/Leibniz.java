package middleware;

import java.net.InetAddress;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Leibniz extends UnicastRemoteObject implements client.InterfaceLeibniz {
    private static final ArrayList<LeibnizServer> leibnizServers = new ArrayList<>();

    Leibniz() throws RemoteException {
    }

    @Override
    public double calc(Integer iterations, Integer threads) throws RemoteException {
        long tempoInicio = System.currentTimeMillis();
        Integer serverId = 0;
        double sum = 0;
        double[] result = new double[leibnizServers.size()];

        Integer iterationPerServer = iterations / leibnizServers.size();

        ExecutorService executor = Executors.newFixedThreadPool(leibnizServers.size());

        for (LeibnizServer server: leibnizServers) {
            Runnable worker = new LeibnizWorker(tempoInicio, iterationPerServer, threads, serverId++, result, server);
            executor.execute(worker);
        }

        executor.shutdown();
        while (!executor.isTerminated());

        for (double x: result) {
            sum += x;
        }

        return sum;
    }

    public void addServer(InetAddress serverAddress)
    {
        leibnizServers.add(new LeibnizServer(serverAddress));
    }
}
