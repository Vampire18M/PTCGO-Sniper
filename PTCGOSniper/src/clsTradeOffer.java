import java.util.ArrayList;
import java.util.Iterator;

public class clsTradeOffer {
	String offerID;
	String ownerID;
	int restzeit;
	String restlaufzeit;
	private ArrayList<Card> offCard = new ArrayList<Card>();
	private ArrayList<Card> wantCard = new ArrayList<Card>();;
	Boolean isDeck;
	String owner;
	int tradeValue;
	int modi2000 = 90;
	int modi1000 = 80;
	int modi500  = 75;
	int modir = 50;
	
	public clsTradeOffer(String offerID, String ownerID, String restlaufzeit){
		this.offerID = offerID;
		this.ownerID = ownerID;
		this.restlaufzeit = restlaufzeit;
		tradeValue = 0;
	}
	
	void addOffCard(Card ocard){
		this.offCard.add(ocard);
	}
	
	void addWantCard(Card wcard){
		this.wantCard.add(wcard);
	}
	
	void setTradeValue(){
		tradeValue = 0;
		int oval = 0;
		int wval = 0;
		Iterator<Card> offiterator = offCard.iterator();
		while (offiterator.hasNext()) {
			Card offCard = (Card)offiterator.next();
			oval += offCard.getValue();
		}
		
		Iterator<Card> wantiterator = wantCard.iterator();
		while (wantiterator.hasNext()) {
			Card wantCard = (Card)wantiterator.next();
			wval += wantCard.getValue();
		}
//		tradeValue = (100/wval) * oval;
		
		
		int tempmodi;
		//Falls oval > 2000 
		if(oval > 2000){
			tempmodi = modi2000;
		}else{
			//Falls oval > 1000 
			if(oval>1000){
				tempmodi = modi1000;
			}else{
				//Falls oval > 500
				if(oval>500){
					tempmodi = modi500;
					//Rest 
				}else{
					tempmodi = modir;
				}
			}
		}
		
		// oval *= (100/tempmodi);
		// tradeValue = oval - wval;
		tradeValue = ((int)(oval*0.5) + (int)(oval*0.2))- wval;
		//System.out.println("Ich denke: "+offiterator.toString()+" gegen " + wantiterator.toString() +" ist mit folgendem Faktor "+ tempmodi +" : " +tradeValue);
	}
	
	void changemodi (int modi2000,int modi1000,int modi500,int modir){
		this.modi2000=modi2000;
		this.modi1000=modi1000;
		this.modi500=modi500;
		this.modir=modir;
	}
	
	int getTradeValue(){
		return tradeValue;
	}
	
	String tradeInfo(){
		String ret = offerID + " ---- " + restlaufzeit + " ---- ";
		Iterator<Card> offiterator = offCard.iterator();
		while (offiterator.hasNext()) {
			Card offCard = (Card)offiterator.next();
			ret = ret + offCard.getName() + " - ";
		}
		ret = ret + " F�r - ";
		String tmpCard = "";
		int tmpMenge = 1;
		boolean firstRun = true;
		Iterator<Card> wantiterator = wantCard.iterator();
		while (wantiterator.hasNext()) {
			Card wantCard = (Card)wantiterator.next();
			if (wantCard.getName().equals(tmpCard) || firstRun){
				firstRun = false;
				tmpCard = wantCard.getName() ;
				tmpMenge+=1;
			}else{
				ret = ret + tmpMenge + "x -" +  wantCard.getName() + " - ";
				tmpCard = wantCard.getName();
				tmpMenge = 1;
				firstRun = true;
			}
		}
		ret = ret + tmpMenge + "x " +  tmpCard + " - ";
		return ret;
	}
	
	void addTable(){
		String erloes = "<html>";
		Iterator<Card> offiterator = offCard.iterator();
		while (offiterator.hasNext()) {
			Card offCard = (Card)offiterator.next();
			if(offCard.getValue() == 0)
				erloes = erloes + "<b>" + offCard.getName() + "</b> - ";
			else
				erloes = erloes + offCard.getName() + " - ";
		}
		erloes = erloes + "</html>";
		String Kosten = "<html>";
		Card tmpCard = database.cardList.get(0);
		int tmpMenge = 1;
		boolean firstRun = true;
		Iterator<Card> wantiterator = wantCard.iterator();
		while (wantiterator.hasNext()) {
			Card wantCard = (Card)wantiterator.next();
			if (wantCard == tmpCard || firstRun){
				firstRun = false;
				tmpCard = wantCard ;
				tmpMenge+=1;
			}else{	
				
				if(tmpCard.getValue() == 0)
					Kosten = Kosten +"<b>"+ tmpMenge + "x " + tmpCard.getName() + "</b>  ";
				else
					Kosten = Kosten + tmpMenge + "x " + tmpCard.getName() + " ";
				
				tmpCard = wantCard;
				tmpMenge = 1;
				firstRun = true;
			}
		}
		
		if(tmpCard.getValue() == 0)
			Kosten = Kosten + "<b>" + tmpMenge + "x " +  tmpCard.getName() + "</b>  ";
		else
			Kosten = Kosten + tmpMenge + "x -" + tmpCard.getName() + "  ";
		Kosten += "</html>";
		
		Anzeige.addTableRow(tradeValue,restlaufzeit,erloes,Kosten);
	}
	
}
