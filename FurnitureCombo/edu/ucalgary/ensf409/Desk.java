package edu.ucalgary.ensf409;
/**
 * @author Ibrahim Asad
 * 
 * @author Vrund Patel 
 * 
 * @author Brian Chen 
 * 
 * @author Thessalonika Magadia 
 * 
 * @version 1.2
 * 
 * @since 1.0
 * 
 * Go to main to see instructions about running the code!
 * 
 */
public class Desk {
	private String ID = null;
	private String type = null;
	private String legs = null;
	private String top = null;
	private String drawer = null;
	private int price;
	
	Desk(){
		
	}
	public void setID (String id) {
		this.ID = id;
	}
	public void setType (String type) {
		this.type = type;
	}
	public void setLegs (String legs) {
		this.legs = legs;
	}
	public void setTop (String top) {
		this.top = top;
	}
	public void setDrawer (String drawer) {
		this.drawer = drawer;
	}
	public void setPrice (int price) {
		this.price = price;
	}
	
	public String getID () {
		return this.ID;
	}
	public String getType () {
		return this.type;
	}
	public String getLegs () {
		return this.legs;
	}
	public String getTop () {
		return this.top;
	}
	public String getDrawer () {	
		return this.drawer;
	}
	public int getPrice () {
		return this.price;
	}
        
	
	public void printDesk() {
            System.out.print("id: " + this.ID);
            System.out.print(", type: " + this.type);
            System.out.print(", legs: " + this.legs);
            System.out.print(", top: " + this.top);
            System.out.print(", drawer: " + this.drawer);
            System.out.println(", price: "+ this.price);
	}
}