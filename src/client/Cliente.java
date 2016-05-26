package client;

import java.rmi.Naming;

public class Cliente {
    private static long tempoInicio = System.currentTimeMillis();
    private static Integer porta = 2020;

    public static void main(String[] args) {
        try {
            InterfaceLeibniz l = (InterfaceLeibniz) Naming.lookup("rmi://localhost:" + porta.toString() + "/Leibniz");

            System.out.println(l.calc(5000000));
            System.out.println("Tempo Total: " + (System.currentTimeMillis() - tempoInicio));
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
