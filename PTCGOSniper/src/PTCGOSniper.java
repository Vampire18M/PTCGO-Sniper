import javax.swing.JOptionPane;

public class PTCGOSniper {
	
	public static void main(String[] args) {
		//Datenbanken Initialisieren
		database.connect();
		OnlineImporter.MySQLConnection();
		//Online Import?
		int dialogButton = JOptionPane.showConfirmDialog (null, "Online Daten Import durchführen?\n Dadurch werden die Kartenpreise die online gepflegt wurden übernommen\n !!Dies kann bis zu 15 min in Anspruch nehmen!!","Warning",JOptionPane.YES_NO_OPTION);
        if(dialogButton == 0){	
        	database.resetDatabase();
        	OnlineImporter.FullDataImporter();
        }
		//Lokal Import
        database.importCardValue();
		
		Thread x = new Thread(new Anzeige());
		x.start();
		//Open TCP Listener
		try {
			TCPServer.startServer();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		};
	}	
}
