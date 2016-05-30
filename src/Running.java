import java.rmi.RemoteException;
import server.Server;

public class Running {
	
	private static Integer porta = 2020;
	
	public static void main(String[] args) throws RemoteException {
		
		server.Server[] sv = new server.Server[9]; 
		
		for (int i = 0; i < sv.length; i++) {
					
			for (int j = porta; j <= 2021; j++) {
				
				if(sv[i]!=null){
					sv[i] = new Server(j);				
					sv[i].start();
					
				}
			}
		}
	}
}
