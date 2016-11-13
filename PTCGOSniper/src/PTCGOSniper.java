public class PTCGOSniper {
	
	public static void main(String[] args) {
		database.connect();
		database.importCardValue();
		
		Thread x = new Thread(new Anzeige());
		x.start();
		//Open TCP Listener
		try {
			TCPServer.startServer();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
	}	
}
