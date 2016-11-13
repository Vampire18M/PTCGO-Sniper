import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class JsonParser {
	
	public static void parseOffer(String offer){
	JSONParser parser = new JSONParser();
	 try {	 
         Object obj = parser.parse(offer);
         JSONObject jsonObject = (JSONObject) obj;
         String tradeID = (String) jsonObject.get("id");
         String author = (String) jsonObject.get("ownerName");
         String restlaufzeit = (String) jsonObject.get("expirationHR");
         JSONArray offerList = (JSONArray) jsonObject.get("items");
         Object wantedList = jsonObject.get("cardPrice");
         clsTradeOffer newTrade;
         if(database.tradeIDs.contains(tradeID)){
        	 return;
         }else{
        	 newTrade = new clsTradeOffer(tradeID,author,restlaufzeit);
        	 database.offerList.add(newTrade);
         }
         
         //Angebot Karten Prüfen und ggf in DB einpflegen
         Iterator<JSONObject> iterator = offerList.iterator();
         while (iterator.hasNext()) {
        	 JSONObject offerCard = (JSONObject) iterator.next();
        	 String archID = (String) offerCard.get("archetypeID");
        	 if (database.archIDs.contains(archID)){
        		 Card oCard = database.cardList.get(database.archIDs.indexOf(archID));
        		 newTrade.addOffCard(oCard);
        	 }else{
        		 String CardName = (String) offerCard.get("name");
        		 database.exportCardData(archID, CardName);
        	 }
         }
         
         //Nachfrage
         int i = 0;
         String strwantedList = wantedList.toString();
         strwantedList = strwantedList.replace("{", "");
         strwantedList = strwantedList.replace("}", "");
         strwantedList = strwantedList.replace("\"", "");
         String arrWantedList[] = strwantedList.split(",");
         while (i < arrWantedList.length){
        	 String arrCardList[] = arrWantedList[i].split(":");
        	 int menge = Integer.parseInt(arrCardList[1]);
        	 while(menge > 0){
        		 if (database.archIDs.contains(arrCardList[0])){
        			 Card wCard = database.cardList.get(database.archIDs.indexOf(arrCardList[0]));
            		 newTrade.addWantCard(wCard);
            		 menge--;
        		 }else{
            		 throw new Exception("Karte nich in DB");
            	 }
        	 }
        	 i++;
         }
         
     //Berechne Trade Value
         newTrade.setTradeValue();
         if(newTrade.getTradeValue() > 100){
        	 newTrade.addTable();
        	 System.out.println(newTrade.getTradeValue()+"  -----  "+newTrade.tradeInfo());
         }
         
         
         
     } catch (Exception e) {
         //e.printStackTrace();
     }
	}
}
