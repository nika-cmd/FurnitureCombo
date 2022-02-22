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
 * @version 1.3
 * 
 * @since 1.0
 * 
 * Go to main to see instructions about running the code!
 * 
 */
public class Order {
	private String furniture;
	private String type;
	private int quantity;
	private int totalPrice;
	private String[] idList;
	
	
	Order (String furniture, String type, int quantity,
			int price, String[] ids){
            this.furniture = furniture;
            this.type = type;
            this.quantity = quantity;
            this.totalPrice = price;
            this.idList = ids;
	}
	
	public String getFurniture () {
		return this.furniture;
	}
	public String getType () {
		return this.type;
	}
	public int getQuantity () {
		return this.quantity;
	}
	public int getTotalPrice () {
		return this.totalPrice;
	}
	public String[] getIdList () {
		return this.idList;
	}
	
	/**
	 * Returns a String containing all the order info needed
	 * for the order form.
	 * @return
	 */
	public String getOrderInfo () {
		String info = new String("");
		info = info.concat("Original Request: " + this.type.toLowerCase() + " " + this.furniture + ", " + this.quantity + "\n");
		info = info.concat("\nItems Ordered");
		for (int i = 0; i < idList.length; i++) {
			info = info.concat("\nID: " + this.idList[i]);
		}
		info = info.concat("\n");
		info = info.concat("\nTotal Price: $" + this.totalPrice);
		return info;
	}
	/**
	 * Prints order information onto the terminal.
	 */
	public void printOrder () {
		System.out.println();
		System.out.println("Original request: " + this.type.toLowerCase() + " " + this.furniture + ", " + this.quantity);
		System.out.println("Items Ordered:");
		for (int i = 0; i < idList.length; i++) {
			System.out.println("ID: " + this.idList[i]);
		}
		System.out.println("Total Price: $" + this.totalPrice);
		System.out.println();
	}
}
