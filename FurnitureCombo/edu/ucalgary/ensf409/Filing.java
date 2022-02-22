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
public class Filing {
	private String ID = null;
	private String type = null;
	private String rails = null;
	private String drawers = null;
	private String cabinet = null;
	private int price;
	
	Filing(){
		
	}
	public void setID (String id) {
		this.ID = id;
	}
	public void setType (String type) {
		this.type = type;
	}
	public void setRails (String rails) {
		this.rails = rails;
	}
	public void setDrawers (String drawers) {
		this.drawers = drawers;
	}
	public void setCabinet (String cabinet) {
		this.cabinet = cabinet;
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
	public String getRails () {
		return this.rails;
	}
	public String getDrawers () {
		return this.drawers;
	}
	public String getCabinet () {	
		return this.cabinet;
	}
	public int getPrice () {
		return this.price;
	}
	
	public void printFiling() {
		
		System.out.print("id: " + this.ID);
		System.out.print(", type: " + this.type);
		System.out.print(", rails: " + this.rails);
		System.out.print(", drawers: " + this.drawers);
		System.out.print(", cabinet: " + this.cabinet);
		System.out.println(", price: "+ this.price);
	}
}