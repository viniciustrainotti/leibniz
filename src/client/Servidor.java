package client;

import java.io.IOException;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class Servidor extends Thread{

	private static int porta = 2020;
	
	@Override
	public void run() {
	
		super.run();
		
		try{
			
			Leibnz l = new Leibnz();
			Naming.rebind("//localhost:2020/Leibnz", l);
			
		}catch(Exception e){
			
			System.out.println("Error: " + e);
			
		}	
	}
	
	public static void main(String args[]) throws IOException{
		
		LocateRegistry.createRegistry(porta);
		
		new Servidor().start();
	}

}
