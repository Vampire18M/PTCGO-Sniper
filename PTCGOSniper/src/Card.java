public class Card {
	String cardID;
	String cardName;
	int value;
	
	public Card(String ID, String Name, Integer val){
		cardID = ID;
		cardName = Name;
		value = val;
	}
	
	public int getValue(){
		return value;
	}
	
	public String getName(){
		return cardName;
	}
}
