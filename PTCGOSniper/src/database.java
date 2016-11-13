import java.sql.*;
import java.util.Arrays;
import java.util.ArrayList;

public class database {
	static Connection c;
	static ArrayList<Card> cardList = new ArrayList<Card>();
	static ArrayList<String> archIDs = new ArrayList<String>();
	static ArrayList<clsTradeOffer> offerList = new ArrayList<clsTradeOffer>();
	static ArrayList<String> tradeIDs = new ArrayList<String>();
	//DB Connection
	public static void connect(){
		c = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      String dbPath = "C:\\Users\\dsmai_000\\Documents\\PTCGO Sniper\\";
	      c = DriverManager.getConnection("jdbc:sqlite:"+dbPath+"database.db");
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    System.out.println("Opened database successfully");
	}
	
	//Load Cards from DB to CardArray
	public static void importCardValue(){
		Statement stmt = null;		
		try {
			stmt = c.createStatement();
		    ResultSet rs = stmt.executeQuery( "SELECT archID, CardName, Value, isHolo, isFA, isSR FROM CardData WHERE Booster NOT NULL;" );
			while ( rs.next() ) {
		    	String id = rs.getString("archID");
		    	String CardName = rs.getString("CardName");
		    	Boolean infoFA = (rs.getString("isFA")).contains("True");
		    	Boolean infoSR = (rs.getString("isSR")).contains("True");
		    	Boolean infoRV = (rs.getString("isHolo")).contains("True");
		    	if(infoRV&&!infoSR&&!!infoFA)
	    			CardName = CardName + "_RV";
		    	if(infoFA)
		    			CardName = CardName + "_FA";
		    	if(infoSR)
	    				CardName = CardName + "_SR";
		    	int value = rs.getInt("Value");
		    	archIDs.add(id);
		    	cardList.add(new Card(id,CardName,value));
		    }
		 } catch ( Exception e ) {
			 System.out.println(e.getMessage());
		 }
		
	}
	
	//DB Update of Missing Informations (Name, Booster, Nummer, isHolo)
	public static void exportCardData(String archID, String CardName){

		Statement stmt = null;
		String sql = "";
		boolean isHolo = false;
		ArrayList <String> parts = new ArrayList<String>(Arrays.asList((CardName.split("_"))));
		
		//Aufbereiten der Daten
		if (parts.get(1).equals("Promo")) 
			parts.remove(1);
		if (parts.size() >= 4) 
			isHolo = true;
		
		//Update
		try {
			stmt = c.createStatement();
			sql = "UPDATE CardData SET CardName = \""+parts.get(0)+"\", Booster = \""+parts.get(1)+"\", Nummer = "+parts.get(2)+", isHolo = \""+isHolo+"\" WHERE archID = \""+archID+"\";";
		    stmt.executeUpdate(sql);
		     c.commit();
		}catch ( Exception e ) {
			//System.out.println(sql);
		}
		
		
	}
	
}
