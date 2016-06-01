package middleware;

import java.net.InetAddress;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Leibniz extends UnicastRemoteObject implements client.InterfaceLeibniz {
    private static final ArrayList<LeibnizServer> leibnizServers = new ArrayList<>();

    Leibniz() throws RemoteException {
    }

    @Override
    public double calc(Integer iterations, Integer threads) throws RemoteException {
        long tempoInicio = System.currentTimeMillis();
        Integer serverId = 0;
        double sum = 0;
        double[] result = new double[leibnizServers.size()];

        Integer iterationPerServer = null;
        try {
            iterationPerServer = iterations / leibnizServers.size();
        } catch (ArithmeticException e) {
            System.out.println("Nenhum servidor para calcular!");
            System.exit(1);
        }

        ExecutorService executor = Executors.newFixedThreadPool(leibnizServers.size());

        for (LeibnizServer server : leibnizServers) {
            Runnable worker = new LeibnizWorker(tempoInicio, iterationPerServer, threads, serverId++, result, server);
            executor.execute(worker);
        }

        executor.shutdown();
        //noinspection StatementWithEmptyBody
        while (!executor.isTerminated()) ;

        for (double x : result) {
            sum += x;
        }

        return sum;
    }

    void addServer(InetAddress serverAddress) {
        leibnizServers.add(new LeibnizServer(serverAddress));
    }

    void removeServer(InetAddress serverAddress) {
        for (LeibnizServer server: leibnizServers) {
            if (Objects.equals(server.getAddress(), serverAddress)) {
                leibnizServers.remove(server);
                break;
            }
        }
    }
}
