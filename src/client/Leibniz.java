package client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.Semaphore;

public class Leibniz extends UnicastRemoteObject implements InterfaceLeibniz {
    protected Leibniz() throws RemoteException {
        super();
    }

    @Override
    public double calc(Integer iterations, Integer threads) throws RemoteException {
        final double[] terms = new double[threads];
        double pi = 0;

        Integer begin = 0;
        Integer perThread = iterations / threads;
        Integer end = perThread;

        for (int iter = 0; iter < threads; iter++, begin = end + 1, end += perThread) {
            final Integer finalBegin = begin;
            final Integer finalEnd = end;
            final int currentThread = iter;

            final Thread t = new Thread(new Runnable() {
                Integer start = finalBegin;
                Integer stop = finalEnd;

                public void run() {
                    terms[currentThread] = 0;
                    for (Integer i = start; i < stop; i++) {
                        terms[currentThread] += Math.pow(-1, i) / (2 * i + 1);
                    }
                    System.out.println(terms[currentThread]);
                }
            });
            t.start();
        }

        for (double x: terms) {
            pi += x;
            System.out.println(x);
        }

        return pi * 4;
    }
}
