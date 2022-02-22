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
import java.io.*;	
	
public class OrderForm {
	// file output name
	private String filename = "orderForm.txt";
	
	public OrderForm (String fileName) {
		this.filename = fileName + ".txt";
	}
	
	public void over() {
		try{
			BufferedWriter writer = new BufferedWriter(new FileWriter (filename));
			writer.close();
		}
		catch(IOException e){
			System.out.println("Could not overwrite");
		}
	}
	
		
	// if true 
	public void TruthOutput(Order myOrder){

		// 	String [] FacultyName for the facultys used
		//	String [] ItemOrder for the furtintures orders
		//  int Price   for the final price
		// String Furntype for the furntiure type ex: mesh chair
		// int Furnamount for the amount of furniture wanted

	
		try{
			BufferedWriter writer = new BufferedWriter(new FileWriter (filename));
		
			// to enter the faculty name 
			writer.write("\n");
			writer.write("Furniture Order Form \n");
			writer.write("Faculty Name: ");
			writer.write("\n");
	
			//to enter date 
			writer.write("Date: ");
			writer.write("\n");
			writer.write("\n");
   
			// to enter the original request
			writer.write(myOrder.getOrderInfo());
   
			writer.close();
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}


	// if false
	public void FalseOutput(String furniture, String type, int numberoforder){
   
    try{
		BufferedWriter writer = new BufferedWriter(new FileWriter (filename));

		if (furniture.equals("chair")){
			writer.write("Order cannot be fulfilled based on current inventory for: \n" + numberoforder + ", " + type  + " " + furniture + "\n"); 
			writer.write("\nSuggested manufacturers are Office Furnishings, Chairs R Us,");
			writer.write(" Furniture Goods, and Fine Office Supplies.");
			writer.write("\n");
		}
   
		else if (furniture.equals("desk")){
			writer.write("Order cannot be fulfilled based on current inventory for: \n" + numberoforder + ", " + type  + " " + furniture + "\n"); 
			writer.write("\nSuggested manufacturers are Office Furnishings, Academic Desks,");
			writer.write(" Furniture Goods, and Fine Office Supplies.");
			writer.write("\n");
		}
   
		else if (furniture.equals("lamp")){
			writer.write("Order cannot be fulfilled based on current inventory for: \n" + numberoforder + ", " + type  + " " + furniture + "\n"); 
			writer.write("\nSuggested manufacturers are Office Furnishings,");
			writer.write(" Furniture Goods, and Fine Office Supplies.");
			writer.write("\n");
		}
   
		else if (furniture.equals("filing")){
			writer.write("Order cannot be fulfilled based on current inventory for: \n" + numberoforder + ", " + type  + " " + furniture + "\n"); 
			writer.write("\nSuggested manufacturers are Office Furnishings,");
			writer.write(" Furniture Goods, and Fine Office Supplies.");
			writer.write("\n");
		}
		else {
			writer.write(type + " " + furniture + " Is not available");
			writer.write("\n");		
		}
   
		writer.close();
    }
    catch(IOException e){
        System.out.println("Can't Enter Values is");
        e.printStackTrace();
    }
}



}

/* if true
 
 * Contact:
 
 * Original Request: mesh chair, 1
 * Items OrderedID: C9890ID
 * 				  : C0942
 * 
 * Total Price: $150
 * 
 * 
 * 
 *Furniture Order Form
 * Faculty Name:
 * Contact:
 * Date:
 * Original Request: mesh chair, 1
 * Items OrderedID: C9890ID
 * 				  : C0942
 * 
 * Total Price: $150
 */