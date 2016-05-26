package client;

import java.rmi.Naming;

public class Cliente {
	
	private static long tempoInicio = System.currentTimeMillis();
	
	public static void main(String[] args) {
		try {
			InterfaceLeibnz l = (InterfaceLeibnz) Naming.lookup("rmi://localhost:2020/Leibnz");
			
			System.out.println(l.calc(5000000));
			System.out.println("Tempo Total: " + (System.currentTimeMillis() - tempoInicio));
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
