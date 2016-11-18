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
	      //String dbPath = "C:\\Users\\dsmai_000\\Documents\\PTCGO Sniper\\";
	      c = DriverManager.getConnection("jdbc:sqlite:database.db");
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
		    ResultSet rs = stmt.executeQuery( "SELECT archID, CardName, Value, isHolo, isFA, isSR FROM CardData WHERE Booster != \"null\";" );
			while ( rs.next() ) {
		    	String id = rs.getString("archID");
		    	String CardName = rs.getString("CardName");
		    	Boolean infoFA = (rs.getInt("isFA") == 1);
		    	Boolean infoSR = (rs.getInt("isSR") == 1);
		    	Boolean infoRV = (rs.getInt("isHolo") == 1);
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
	
	//DB Update must update Online Database
	public static void exportCardData(String archID, String CardName){
		Statement stmt = null;
		String sql = "";
		int isHolo = 0;
		ArrayList <String> parts = new ArrayList<String>(Arrays.asList((CardName.split("_"))));	
		//Aufbereiten der Daten
		if (parts.get(1).equals("Promo")) 
			parts.remove(1);
		if (parts.size() >= 4) 
			isHolo = 1;
		//Update
		try {
			stmt = c.createStatement();
			sql = "UPDATE CardData SET CardName = \""+parts.get(0)+"\", Booster = \""+parts.get(1)+"\", Nummer = "+parts.get(2)+", isHolo = "+isHolo+" WHERE archID = \""+archID+"\";";
		    stmt.executeUpdate(sql);
		     c.commit();
		     OnlineImporter.OnlineUpdate(sql);
		}catch ( Exception e ) {
			;
		}		
	}
	
	public static void resetDatabase(){
		Statement stmt = null;
		String sql = "";
		try {
			stmt = c.createStatement();
			sql = "DROP TABLE IF EXISTS CardData;";
			stmt.executeUpdate(sql);
		    c.commit();
		    
		}catch ( Exception e ){
			
		}
		
		stmt = null;
		sql = "";
		try {
		stmt = c.createStatement();
		sql = "CREATE TABLE CardData (CardName STRING(127),Booster STRING(12), Nummer INTEGER, Value INTEGER,isHolo INTEGER,isSR INTEGER,isFA BOOLEAN,searchText TEXT,archID STRING(50)  PRIMARY KEY);";
		stmt.executeUpdate(sql);
	    c.commit();
		}catch ( Exception e ){
			
		}
	}
	
	
	public static void InsertToLocal(String CardName, String Booster, int Nummer, int Value, int isHolo, int isSR, int isFa, String searchText, String archID){
		Statement stmt = null;
		String sql = "";
		try {
			stmt = c.createStatement();
			sql = "INSERT INTO CardData (CardName,Booster,Nummer,Value,isHolo,isSR,isFA,searchText,archID)VALUES (\""+CardName+"\",\""+Booster+"\","+Nummer+","+Value+","+isHolo+","+isSR+","+isFa+",\""+searchText+"\",\""+archID+"\");";
		    stmt.executeUpdate(sql);
		     c.commit();
		}catch ( Exception e ){
			
		}
	}
	
	
}
