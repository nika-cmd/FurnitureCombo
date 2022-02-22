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
 * 
 * @version 1.2
 * 
 * @since 1.0
 * 
 * Go to main to see instructions about running the code!
 * 
 */

public class Chair {
	private String ID = null;
	private String type = null;
	private String legs = null;
	private String arms = null;
	private String seat = null;
	private String cushion = null;
	private int price;
	
	Chair(){
		
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
	public void setArms (String arms) {
		this.arms = arms;
	}
	public void setSeat (String seat) {
		this.seat = seat;
	}
	public void setCushion (String cushion) {
		this.cushion = cushion;
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
	public String getArms () {
		return this.arms;
	}
	public String getSeat () {	
		return this.seat;
	}
	public String getCushion() {
		return this.cushion;
	}
	public int getPrice () {
		return this.price;
	}
	
	public void printChair() {
		
		System.out.print("id: " + this.ID);
		System.out.print(", type: " + this.type);
		System.out.print(", legs: " + this.legs);
		System.out.print(", arms: " + this.arms);
		System.out.print(", seat: " + this.seat);
		System.out.print(", cushion: "+ this.cushion);
		System.out.println(", price: "+ this.price);
	}
}