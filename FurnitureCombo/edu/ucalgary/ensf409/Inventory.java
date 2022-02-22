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
import java.sql.*;
import java.util.*;

public class Inventory {
    public final String DBURL; //store the database url information
    public final String USERNAME; //store the user's account username
    public final String PASSWORD; //store the user's account password
    
    private Connection dbConnect;
    private ResultSet results;
    
    private List<Chair[]> listChair = new ArrayList<Chair[]>();
    private List<Desk[]> listDesk = new ArrayList<Desk[]>();
    private List<Filing[]> listFiling = new ArrayList<Filing[]>();
    private List<Lamp[]> listLamp = new ArrayList<Lamp[]>();
    
    // UTILITY METHODS TO CONNECTION TO DATABASE
    /**
     * Initializes registration by taking in values that will be needed to
     * initialize the connection to the database.
     * @param url
     * @param user
     * @param pass
     */
    public Inventory (String url, String user, String pass) {
    	this.DBURL = url;
    	this.USERNAME = user;
    	this.PASSWORD = pass;
    }
    
    /**
     * This method is only used for unit testing
     * @return Connection dbConnect
     */
    public Connection getConnection() {
        return this.dbConnect;
    }
    
    // GETTERS
    /**
     * Returns the DBURL as a String value.
     * @return
     */
    public String getDburl () {
    	return this.DBURL;
    }
    /**
     * Returns the USERNAME as a String value.
     * @return
     */
    public String getUsername () {
    	return this.USERNAME;
    }
    /**
     * Returns the PASSWORD as a String value.
     * @return
     */
    public String getPassword () {
    	return this.PASSWORD;
    }
    
    /**
     *  Connects to the database (needs Registration object to be initialized first).
     */
    public Connection initializeConnection () {
    	try {
            dbConnect = DriverManager.getConnection(DBURL, USERNAME, PASSWORD);
    	}
    	catch (SQLException e) {
            System.err.println("Error: could not connect to data base.");
            e.printStackTrace();
    	}
        
        return dbConnect;
    }
    
    /**
     * Closes connections to the database.
     */
    public void close() {
        try {
            results.close();
            dbConnect.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // METHODS FOR PROGRAM FUNCTIONALITY
    /**
     * Returns an Order which includes an array of the best combination of items.
     * If Order could not be made it will return null.
     * @param furniture
     * @param type
     * @param quantity
     * @return
     */
    public Order createOrder (String furniture, String type, int quantity) {
    	Order myOrder = null;
    	if(furniture.equals("chair")) {
    		myOrder = compareChairs (type, quantity);
    		if (myOrder != null) {
    			return myOrder;
    		} 
    	} else if (furniture.equals("desk")) {
    		myOrder = compareDesks (type, quantity);
    		if (myOrder != null) {
    			return myOrder;
    		}
    	} else if (furniture.equals("filing")) {
    		myOrder = compareFilings (type, quantity);
    		if (myOrder != null) {
    			return myOrder;
    		}
    	} else if (furniture.equals("lamp")) {
    		myOrder = compareLamps (type, quantity);
    		if (myOrder != null) {
    			return myOrder;
    		}
    	}
    	return null;
    }
    
    /**
     * Takes in an order as an argument removes the items with the IDs 
     * specified in the order.
     * @param myOrder
     */
    public void updateInventory (Order myOrder) {
    	if (myOrder == null) {
    		return;
    	}
    	int i = 0;
    	while (i < myOrder.getIdList().length) {
            try {
                String query = "DELETE FROM " + myOrder.getFurniture() + 
                                " WHERE ID = ?";
                PreparedStatement myStmt = dbConnect.prepareStatement(query);

                myStmt.setString(1, myOrder.getIdList()[i]);
                myStmt.executeUpdate();
                myStmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            i++;
    	}
    }
    
    /**
     * Returns the IDs of items in array of type Chair, which is passed in
     * as an argument.
     * @param chairs
     * @return
     */
    public String[] getIDsChair (Chair[] chairs) {
    	if (chairs == null) {
            return null;
    	}
    	String[] ids = new String[chairs.length];
    	for (int i = 0; i < chairs.length; i++) {
            ids[i] = chairs[i].getID();
    	}
        
    	return ids;
    }
    
    /**
     * Returns the IDs of items in array of type Desk, which is passed i
     * as an argument.
     * @param desks
     * @return
     */
    public String[] getIDsDesk (Desk[] desks) {
    	if (desks == null) {
            return null;
    	}
    	String[] ids = new String[desks.length];
    	for (int i = 0; i < desks.length; i++) {
    		ids[i] = desks[i].getID();
    	}
    	return ids;
    }
    
    /**
     * Returns the IDs of items in array of type Filing, which is passed i
     * as an argument.
     * @param filings
     * @return
     */
    public String[] getIDsFiling (Filing[] filings) {
    	if (filings == null) {
    		return null;
    	}
    	String[] ids = new String[filings.length];
    	for (int i = 0; i < filings.length; i++) {
            ids[i] = filings[i].getID();
    	}
    	return ids;
    }
    
    /**
     * Returns the IDs of items in array of type Lamp, which is passed i
     * as an argument.
     * @param lamps
     * @return
     */
    public String[] getIDsLamp (Lamp[] lamps) {
    	if (lamps == null) {
    		return null;
    	}
    	String[] ids = new String[lamps.length];
    	for (int i = 0; i < lamps.length; i++) {
    		ids[i] = lamps[i].getID();
    	}
    	return ids;
    }
    
    /**
     * Helper function of compareChairs. Copies chairs of parameter type into an array
     * from the database.
     * @param type
     * @return
     */
    private Chair[] loadIntoArrayChair (String type) {
    	Chair[] chairs = new Chair[resSize("chair", type)];
    	int i = 0;
    	while (i < chairs.length) {
    		chairs[i] = new Chair();
    		i++;
    	}
    	try {
            Statement myStmt = dbConnect.createStatement();
            results = myStmt.executeQuery("SELECT * FROM chair WHERE Type = " + "'"+ type+"'");

            i = 0;
            while (results.next() && i < chairs.length) {

                    chairs[i].setType(results.getString("Type"));
                    chairs[i].setID(results.getString("ID"));
                    chairs[i].setLegs(results.getString("Legs"));
                    chairs[i].setArms(results.getString("Arms"));
                    chairs[i].setSeat(results.getString("Seat"));
                    chairs[i].setCushion(results.getString("Cushion"));
                    chairs[i].setPrice(results.getInt("Price"));

                    i++;

            }
            myStmt.close();
    	}
    	catch (SQLException e) {
    		e.printStackTrace();
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    	}
    	return chairs;
    }
    
    /**
     * Helper function of compareDesks. Copies desks of parameter type into an array
     * from the database.
     * @param type
     * @return
     */
    private Desk[] loadIntoArrayDesk (String type) {
    	Desk[] desks = new Desk[resSize("desk", type)];
    	int i = 0;
    	while (i < desks.length) {
    		desks[i] = new Desk();
    		i++;
    	}
    	try {
            Statement myStmt = dbConnect.createStatement();
            results = myStmt.executeQuery("SELECT * FROM desk WHERE Type = " + "'"+ type+"'");

            i = 0;
            while (results.next() && i < desks.length) {
                desks[i].setType(results.getString("Type"));
                desks[i].setID(results.getString("ID"));
                desks[i].setLegs(results.getString("Legs"));
                desks[i].setTop(results.getString("Top"));
                desks[i].setDrawer(results.getString("Drawer"));
                desks[i].setPrice(results.getInt("Price"));

                i++;
            }
            myStmt.close();
    	}
    	catch (SQLException e) {
    		e.printStackTrace();
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    	}
    	return desks;
    }
    
    /**
     * Helper function of compareFilings. Copies filings of parameter type into an array
     * from the database.
     * @param type
     * @return
     */
    private Filing[] loadIntoArrayFiling (String type) {
    	Filing[] filings = new Filing[resSize("filing", type)];
    	int i = 0;
    	while (i < filings.length) {
            filings[i] = new Filing();
            i++;
    	}
    	try {
            Statement myStmt = dbConnect.createStatement();
            results = myStmt.executeQuery("SELECT * FROM filing WHERE Type = " + "'"+ type+"'");

            i = 0;
            while (results.next() && i < filings.length) {

                    filings[i].setType(results.getString("Type"));
                    filings[i].setID(results.getString("ID"));
                    filings[i].setRails(results.getString("Rails"));
                    filings[i].setDrawers(results.getString("Drawers"));
                    filings[i].setCabinet(results.getString("Cabinet"));
                    filings[i].setPrice(results.getInt("Price"));

                    i++;

            }
            myStmt.close();
    	}
    	catch (SQLException e) {
    		e.printStackTrace();
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    	}
    	return filings;
    }
    
    /**
     * Helper function of compareLamps. Copies lamps of parameter type into an array
     * from the database.
     * @param type
     * @return
     */
    private Lamp[] loadIntoArrayLamp (String type) {
    	Lamp[] lamps = new Lamp[resSize("lamp", type)];
    	int i = 0;
    	while (i < lamps.length) {
    		lamps[i] = new Lamp();
    		i++;
    	}
    	try {
            Statement myStmt = dbConnect.createStatement();
            results = myStmt.executeQuery("SELECT * FROM lamp WHERE Type = " + "'"+ type+"'");

            i = 0;
            while (results.next() && i < lamps.length) {

                    lamps[i].setType(results.getString("Type"));
                    lamps[i].setID(results.getString("ID"));
                    lamps[i].setBase(results.getString("Base"));
                    lamps[i].setBulb(results.getString("Bulb"));
                    lamps[i].setPrice(results.getInt("Price"));

                    i++;

                    }
            myStmt.close();
            }
            catch (SQLException e) {
                    e.printStackTrace();
            }
            catch (Exception e) {
                    e.printStackTrace();
            }
            return lamps;
    }
    
    /**
     * Helper function of compareChairs.
     * Makes a combination of all possible chair combinations, and
     * saves them into listChair (One combination is saved into an array).
     * (Initially)'left' starts empty while 'right' stores all the items
     * @param left
     * @param right
     */
    private void chairCombos (Chair[] left, Chair[] right) {
    	if(right.length == 0) {
    		listChair.add(left);
    	}
    	else {
            Chair[] subRight = Arrays.copyOfRange(right, 0, 1);
            Chair[] tmp = new Chair[left.length + 1];
            System.arraycopy(left, 0, tmp, 0, left.length);
            System.arraycopy(subRight, 0, tmp, left.length, 1);
            chairCombos (tmp, Arrays.copyOfRange(right, 1, right.length));
            chairCombos (left, Arrays.copyOfRange(right, 1, right.length));
    	}
    
    }
    
    /**
     * Helper function of compareDesks.
     * Makes a combination of all possible desk combinations, and
     * saves them into listDesk (One combination is saved into an array).
     * (Initially)'left' starts empty while 'right' stores all the items
     * @param left
     * @param right
     */
    private void deskCombos (Desk[] left, Desk[] right) {
    	if(right.length == 0) {
    		listDesk.add(left);
    	}
    	else {
            Desk[] subRight = Arrays.copyOfRange(right, 0, 1);
            Desk[] tmp = new Desk[left.length + 1];
            System.arraycopy(left, 0, tmp, 0, left.length);
            System.arraycopy(subRight, 0, tmp, left.length, 1);
            deskCombos (tmp, Arrays.copyOfRange(right, 1, right.length));
            deskCombos (left, Arrays.copyOfRange(right, 1, right.length));
    	}
    
    }
    
    /**
     * Helper function of compareFilings.
     * Makes a combination of all possible filing combinations, and
     * saves them into listFiling (One combination is saved into an array).
     * (Initially)'left' starts empty while 'right' stores all the items
     * @param left
     * @param right
     */
    private void filingCombos (Filing[] left, Filing[] right) {
    	if(right.length == 0) {
    		listFiling.add(left);
    	}
    	else {
    		Filing[] subRight = Arrays.copyOfRange(right, 0, 1);
    		Filing[] tmp = new Filing[left.length + 1];
    		System.arraycopy(left, 0, tmp, 0, left.length);
    		System.arraycopy(subRight, 0, tmp, left.length, 1);
    		filingCombos (tmp, Arrays.copyOfRange(right, 1, right.length));
    		filingCombos (left, Arrays.copyOfRange(right, 1, right.length));
    	}
    
    }
    
    /**
     * Helper function of compareLamps.
     * Makes a combination of all possible lamp combinations, and
     * saves them into listLamp (One combination is saved into an array).
     * (Initially)'left' starts empty while 'right' stores all the items
     * @param left
     * @param right
     */
    private void lampCombos (Lamp[] left, Lamp[] right) {
    	if(right.length == 0) {
    		listLamp.add(left);
    	}
    	else {
    		Lamp[] subRight = Arrays.copyOfRange(right, 0, 1);
    		Lamp[] tmp = new Lamp[left.length + 1];
    		System.arraycopy(left, 0, tmp, 0, left.length);
    		System.arraycopy(subRight, 0, tmp, left.length, 1);
    		lampCombos (tmp, Arrays.copyOfRange(right, 1, right.length));
    		lampCombos (left, Arrays.copyOfRange(right, 1, right.length));
    	}
    
    }
    
    /**
     * Returns an order that has the lowest costs for that type and quantity
     * of chair(s). Returns null if an order could not be made with those 
     * specifications.
     * @param type
     * @param quantity
     * @return
     */
    public Order compareChairs (String type, int quantity) {
    	
    	Chair[] chairInv = loadIntoArrayChair(type);
    	listChair = new ArrayList<Chair[]>();
    	Chair[] tmp = new Chair[0];
    	chairCombos(tmp, chairInv);
    	
    	// prints out the list of combos
    	
    	/*
    	int i = 0;
    	while (i < listChair.size()) {
    		System.out.println("index: "+ i + " "+listChair.get(i).length);
    		for (int j = 0; j < listChair.get(i).length; j++) {
    			listChair.get(i)[j].printChair();
    		}
    		i++;
    	}
    	*/
    	
    	Chair[] bestCombo = null;
    	int priceCurrent = 0;

    	int i = 0;
    	while (i < listChair.size()) {
    		int legCount = 0;
        	int armCount = 0;
        	int seatCount = 0;
        	int cushCount = 0;
    		int priceTotal = 0;
    		for (int j = 0; j < listChair.get(i).length; j++) {
    			if(listChair.get(i)[j].getLegs().equals("Y")) {
    				legCount++;
    			}
    			if(listChair.get(i)[j].getArms().equals("Y")) {
    				armCount++;
    			}
    			if(listChair.get(i)[j].getSeat().equals("Y")) {
    				seatCount++;
    			}
    			if(listChair.get(i)[j].getCushion().equals("Y")) {
    				cushCount++;
    			}
    			priceTotal += listChair.get(i)[j].getPrice();
    		}
    		
    		// if quantity of chairs can be met
    		if (legCount >= quantity && armCount >= quantity && seatCount >= quantity
    				&& cushCount >= quantity) {
    			// if there's nothing currently in bestCombo (price is zero)
    			// or if the price of this combo is less than the current 
    			// best combo,
    			if (priceCurrent == 0 || priceTotal < priceCurrent) {
    				bestCombo = new Chair[listChair.get(i).length];
    				//System.out.println("bestCombo length: " + bestCombo.length);
    				for (int n = 0; n < bestCombo.length; n++) {
    					bestCombo[n] = new Chair();
    					bestCombo[n] = listChair.get(i)[n];
    				}
    				priceCurrent = priceTotal;
    			}
    		}
    		i++;
    	}
    	if (getIDsChair(bestCombo) != null) { 
            return new Order ("chair", type, quantity, priceCurrent, getIDsChair(bestCombo));
    	}
    	return null;
    }
    
    /**
     * Returns an order that has the lowest costs for that type and quantity
     * of desk(s). Returns null if an order could not be made with those 
     * specifications.
     * @param type
     * @param quantity
     * @return
     */
    public Order compareDesks (String type, int quantity) {
    	
    	Desk[] deskInv = loadIntoArrayDesk(type);
    	listDesk = new ArrayList<Desk[]>();
    	Desk[] tmp = new Desk[0];
    	deskCombos(tmp, deskInv);
    	
    	Desk[] bestCombo = null;
    	int priceCurrent = 0;

    	int i = 0;
    	while (i < listDesk.size()) {
    		int legCount = 0;
        	int topCount = 0;
        	int drawerCount = 0;
    		int priceTotal = 0;
    		for (int j = 0; j < listDesk.get(i).length; j++) {
    			if(listDesk.get(i)[j].getLegs().equals("Y")) {
    				legCount++;
    			}
    			if(listDesk.get(i)[j].getTop().equals("Y")) {
    				topCount++;
    			}
    			if(listDesk.get(i)[j].getDrawer().equals("Y")) {
    				drawerCount++;
    			}
    			priceTotal += listDesk.get(i)[j].getPrice();
    		}
    		
    		// if quantity of desks can be met
    		if (legCount >= quantity && topCount >= quantity && drawerCount >= quantity) {
    			// if there's nothing currently in bestCombo (price is zero)
    			// or if the price of this combo is less than the current 
    			// best combo, bestCombo is updated
    			if (priceCurrent == 0 || priceTotal < priceCurrent) {
    				bestCombo = new Desk[listDesk.get(i).length];
    			
    				for (int n = 0; n < bestCombo.length; n++) {
    					bestCombo[n] = new Desk();
    					bestCombo[n] = listDesk.get(i)[n];
    				}
    				priceCurrent = priceTotal;
    			}
    		}
    		i++;
    	}
    	 if (getIDsDesk(bestCombo) != null)
    		return new Order ("desk", type, quantity, priceCurrent, getIDsDesk(bestCombo));
    	return null;
    }
    
    /**
     * Returns an order that has the lowest costs for that type and quantity
     * of filing(s). Returns null if an order could not be made with those 
     * specifications.
     * @param type
     * @param quantity
     * @return
     */
    public Order compareFilings (String type, int quantity) {
    	
    	Filing[] filingInv = loadIntoArrayFiling(type);
    	listFiling = new ArrayList<Filing[]>();
    	Filing[] tmp = new Filing[0];
    	filingCombos(tmp, filingInv);
    	
    	Filing[] bestCombo = null;
    	int priceCurrent = 0;

    	int i = 0;
    	while (i < listFiling.size()) {
    		int railsCount = 0;
        	int drawersCount = 0;
        	int cabinetCount = 0;
    		int priceTotal = 0;
    		for (int j = 0; j < listFiling.get(i).length; j++) {
    			if(listFiling.get(i)[j].getRails().equals("Y")) {
    				railsCount++;
    			}
    			if(listFiling.get(i)[j].getDrawers().equals("Y")) {
    				drawersCount++;
    			}
    			if(listFiling.get(i)[j].getCabinet().equals("Y")) {
    				cabinetCount++;
    			}
    			priceTotal += listFiling.get(i)[j].getPrice();
    		}
    		
    		// if quantity of desks can be met
    		if (railsCount >= quantity && drawersCount >= quantity && cabinetCount >= quantity) {
    			// if there's nothing currently in bestCombo (price is zero)
    			// or if the price of this combo is less than the current 
    			// best combo, bestCombo is updated
    			if (priceCurrent == 0 || priceTotal < priceCurrent) {
    				bestCombo = new Filing[listFiling.get(i).length];
    				for (int n = 0; n < bestCombo.length; n++) {
    					bestCombo[n] = new Filing();
    					bestCombo[n] = listFiling.get(i)[n];
    				}
    				priceCurrent = priceTotal;
    			}
    		}
    		i++;
    	}
    	if (getIDsFiling(bestCombo) != null) { 
    		return new Order ("filing", type, quantity, priceCurrent, getIDsFiling(bestCombo));
    	}
    	return null;
    }
    
    /**
     * Returns an order that has the lowest costs for that type and quantity
     * of lamp(s). Returns null if an order could not be made with those 
     * specifications.
     * @param type
     * @param quantity
     * @return
     */
    public Order compareLamps (String type, int quantity) {
    	Lamp[] lampInv = loadIntoArrayLamp(type);
    	listLamp = new ArrayList<Lamp[]>();
    	Lamp[] tmp = new Lamp[0];
    	lampCombos(tmp, lampInv);
    	
    	Lamp[] bestCombo = null;
    	int priceCurrent = 0;

    	int i = 0;
    	while (i < listLamp.size()) {
    		int baseCount = 0;
        	int bulbCount = 0;
    		int priceTotal = 0;
    		for (int j = 0; j < listLamp.get(i).length; j++) {
    			if(listLamp.get(i)[j].getBase().equals("Y")) {
    				baseCount++;
    			}
    			if(listLamp.get(i)[j].getBulb().equals("Y")) {
    				bulbCount++;
    			}
    			priceTotal += listLamp.get(i)[j].getPrice();
    		}
    		
    		// if quantity of desks can be met
    		if (baseCount >= quantity && bulbCount >= quantity) {
    			// if there's nothing currently in bestCombo (price is zero)
    			// or if the price of this combo is less than the current 
    			// best combo, bestCombo is updated
    			if (priceCurrent == 0 || priceTotal < priceCurrent) {
    				bestCombo = new Lamp[listLamp.get(i).length];
    				for (int n = 0; n < bestCombo.length; n++) {
    					bestCombo[n] = new Lamp();
    					bestCombo[n] = listLamp.get(i)[n];
    				}
    				priceCurrent = priceTotal;
    			}
    		}
    		i++;
    	}
    	if (getIDsLamp(bestCombo) != null) { 
    		return new Order ("lamp", type, quantity, priceCurrent, getIDsLamp(bestCombo));
    	}
    	return null;
    }
    
    /**
     * Returns the number of items from inventory that are 
     * of 'type' from 'furniture'.
     * @param furniture
     * @param type
     * @return
     */
    public int resSize (String furniture, String type) {
    	int size = 0;
    	try {
            Statement myStmt = dbConnect.createStatement();
            results = myStmt.executeQuery("SELECT * FROM " + furniture);

            while (results.next()) {
                if (results.getString("Type").equalsIgnoreCase(type)) {
                        size++;
                }
            }
            myStmt.close();
    	}
    	catch (SQLException e) {
    		e.printStackTrace();
    	}
    	return size;
    }
    
    /**
     * Sees if furniture is a valid type (furniture argument must be all lowercase).
     * Returns true if furniture is valid and false otherwise.
     * @param furn
     * @return
     */
    public static boolean isValidFurniture (String furn) {
    	if (furn.equals("chair") || furn.equals("desk") 
    			|| furn.equals("filing") || furn.equals("lamp")) {
    		return true;
    	}
    	return false;
    }
    
    /**
     * See if quantity is valid. A valid quantity is defined as a positive
     * integer (greater than zero). Returns true if quantity is valid, and
     * false otherwise.
     * @param quantity
     * @return
     */
    public static boolean isValidQuantity (String quantity) {
    	try {
    		int q = Integer.parseInt(quantity);
			if (q < 1) {
				return false;
			}
			return true;
    	}
    	catch (NumberFormatException e) {
			return false;
		}
    }
    
    /**
     * Main program prompts user input from the terminal and uses this input to create orders,
     * update the database, and create order forms.
     * @param args
     * 
     * <<<<<<<<<<<<<<<<<< HOW TO RUN PROGRAM >>>>>>>>>>>>>>>>>>
     * 
     * 1) Change directory one command line to root directory.
     * 
     * 2) Compile the code. You can type this into the command line:
     * 	  	javac -cp .;lib/mysql-connector-java-8.0.23.jar edu/ucalgary/ensf409/Inventory.java
     * 		(** Note: syntax will vary machine to machine, this is the syntax for a windows machine)
     * 
     * 3) Run the code. You can type this into the command line:
     * 		java -cp .;lib/mysql-connector-java-8.0.23.jar edu/ucalgary/ensf409/Inventory
     * 		(** Note: syntax will vary machine to machine, this is the syntax for a windows machine)
     * 
     * 4) Next, it will prompt for some info in the terminal:
     * 		
     * 		- first the FURNITURE: chair, desk, filing, or lamp 
     * 			(if the furniture inputed is not one of these it will prompt you to try entering
     * 			furniture again).
     * 
     * 		- second the TYPE of that furniture: if the type of furniture is invalid and/or
     * 			does not match any of the types for that furniture in the database (i.e. there is
     * 			not a single item of that type in inventory) it will prompt you to try entering
     * 			a type again.
     * 
     * 		- third it will ask for QUANTITY: if quantity is not valid it will prompt you to enter
     * 			quantity again (for a valid quantity it must be a integer greater than zero).
     * 
     * 5) Then, it will prompt for you to enter a filename for the order form (do NOT include
     * 	  an extention).
     * 
     * 6) Finally, terminal will notify if an order or not could not be placed (includes some info
     * 	  about the order if any). The order form created will be saved in the root directory and
     * 	  holds more information about the order.
     * 		
     */
    public static void main(String[] args) {
    	
    	// remember to change user name and password
        Inventory myJDBC = new Inventory("jdbc:mysql://localhost/inventory","user","pass");
        myJDBC.initializeConnection();
        
        Scanner scan = new Scanner(System.in);
        
        //gets furniture
        System.out.println("Enter furniture. <Then press enter>: ");
        String furniture = scan.nextLine().trim().toLowerCase();
        while (!isValidFurniture (furniture)) {
            System.out.println("Not a valid furniture. Please enter valid furniture: ");
            furniture = scan.nextLine().trim().toLowerCase();
        }
        
        //gets type of furniture
        System.out.println("Enter furniture type <Then press enter>: ");
        String type = scan.nextLine().trim().toLowerCase();
        //type = type.substring(0,1).toUpperCase()+type.substring(1);
        while(myJDBC.resSize(furniture, type) == 0) {
            System.out.println("This type of " + furniture +
                            " does not exist in inventory or is invalid. Please try again: ");
            type = scan.nextLine().trim().toLowerCase();
            //type = type.substring(0,1).toUpperCase()+type.substring(1);
        }
        
        //gets quantity of furniture
        System.out.println("Enter quantity <Then press enter>: ");
        String quantity = scan.nextLine().trim();
        while (!isValidQuantity(quantity)) {
            System.out.println ("Not a valid quanity. Please try again: ");
            quantity = scan.nextLine().trim();
        }
        int quantityInt = Integer.parseInt(quantity);
        
        
        //tries to initialize the order
        Order myOrder = myJDBC.createOrder(furniture, type, quantityInt);
        
        System.out.println("\nEnter a name for the order form <Then press enter>:");
        String fileName = scan.nextLine().trim();
        OrderForm myForm = new OrderForm(fileName);
        scan.close(); //closes scanner
        
        if (myOrder != null) {
            System.out.println("Order successfully placed. Check order form for more information.");
            myForm.TruthOutput(myOrder);
            myJDBC.updateInventory(myOrder); //updates database
            myOrder.printOrder();
        } else { //  if order is null create corresponding output form
            myForm.FalseOutput(furniture, type, quantityInt);
            System.out.println("Order could not be fulfilled based on current inventory. "
                            + "Check order form for more information.");
        }
        
        myJDBC.close(); // closes connection
    }
}