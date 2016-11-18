import java.sql.*;


public class OnlineImporter {
	private static Connection con = null;
	private static String dbHost = "sql7.freemysqlhosting.net"; // Hostname
	private static String dbPort = "3306";      		 // Port -- Standard: 3306
	private static String dbName = "sql7144966";   // Datenbankname
	private static String dbUser = "sql7144966";   // Datenbankuser
	private static String dbPass = "La5GqGPACL";       // Datenbankpasswort
	private static int ImportCounter = 0;
	
	public static void MySQLConnection(){
	    try {
	        Class.forName("com.mysql.jdbc.Driver"); // Datenbanktreiber für JDBC Schnittstellen laden.
	        con = DriverManager.getConnection("jdbc:mysql://"+dbHost+":"+ dbPort+"/"+dbName+"?"+"user="+dbUser+"&"+"password="+dbPass);
	    } catch (ClassNotFoundException e) {
	        System.out.println("Treiber nicht gefunden");
	    } catch (SQLException e) {
	        System.out.println("Verbindung nicht moglich");
	        System.out.println("SQLException: " + e.getMessage());
	        System.out.println("SQLState: " + e.getSQLState());
	        System.out.println("VendorError: " + e.getErrorCode());
	    }
	  }
	
	public static void FullDataImporter(){
		Statement stm = null;
		 try {
			stm = con.createStatement();
			ResultSet rs = stm.executeQuery( "SELECT * FROM CardData" );
			while ( rs.next() ) {
				ImportCounter++;
				//System.out.println("Import: "+  ImportCounter);
				database.InsertToLocal(rs.getString("CardName"), rs.getString("Booster"), rs.getInt("Nummer"), rs.getInt("Value"), rs.getInt("isHolo"), rs.getInt("isSR"), rs.getInt("isFA"), rs.getString("searchText"), rs.getString("archID"));		
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void OnlineUpdate(String sql){
		Statement stm = null;
		try {
			stm = con.createStatement();
			stm.executeUpdate(sql);
		     con.commit();
		} catch (SQLException e) {
			;
		}
		
	}
}
