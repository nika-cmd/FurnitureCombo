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
 * @version 2.5
 * 
 * @since 1.0
 * 
 * IMPORTANT NOTES: Unit testing requires the given database, inventory.sql, that was
 * given to us on D2L
 *
 * Make sure to include all three .jar files when compiling and running
 * 
 */
import java.util.*;
import java.sql.*;
import org.junit.Test;
import static org.junit.Assert.*;

public class InventoryTest {
    
    public InventoryTest() {
    }

    /**
     * Test of getDburl method, of class Inventory.
     */
    @Test
    public void testGetDburl() {
        Inventory inv = new Inventory("jdbc:mysql://localhost/inventory","scm","ensf409");
        String expected = "jdbc:mysql://localhost/inventory";
        String actual = inv.getDburl();
        assertEquals("Expected getter was incorrect: ",expected,actual);
    }

    /**
     * Test of getUsername method, of class Inventory.
     */
    @Test
    public void testGetUsername() {
        Inventory inv = new Inventory("jdbc:mysql://localhost/inventory","scm","ensf409");
        String expected = "scm";
        String actual = inv.getUsername();
        assertEquals("Expected getter was incorrect: ",expected,actual);
    }

    /**
     * Test of getPassword method, of class Inventory.
     */
    @Test
    public void testGetPassword() {
        Inventory inv = new Inventory("jdbc:mysql://localhost/inventory","scm","ensf409");
        String expected = "ensf409";
        String actual = inv.getPassword();
        assertEquals("Expected getter was incorrect: ",expected,actual);
    }
    
    /**
     * Test of initializeConnection method, of class Inventory.
     */
    @Test
    public void testInitializeConnection() {
        Inventory inv = new Inventory("jdbc:mysql://localhost/inventory","scm","ensf409");
        
        Connection result = inv.initializeConnection();
        assertEquals("Could not create successful connection: ",result!=null,true);
    }
    
    /**
     * Test of createOrder method, of class Inventory.
     */
    @Test
    public void testCreateOrder_IncompleteOrder() {
        Inventory inv = new Inventory("jdbc:mysql://localhost/inventory","scm","ensf409");
        inv.initializeConnection();

        Order expected = null;
        Order actual = inv.createOrder("chair", "kneeling", 1);

        assertEquals("Did not create null order: ",expected,actual);
    }
    
    /**
     * Test of createOrder method, of class Inventory.
     */
    @Test
    public void testCreateOrder() {

        Inventory inv = new Inventory("jdbc:mysql://localhost/inventory","scm","ensf409");
        inv.initializeConnection();

        Order expected = inv.compareChairs("mesh", 1);
        Order actual = inv.createOrder("chair", "mesh", 1);

        assertEquals("Expected order does not match" ,expected.getFurniture(), actual.getFurniture());
        assertEquals("Expected order does not match: ",expected.getType(), actual.getType());
        assertEquals("Expected order does not match: ",expected.getQuantity(), actual.getQuantity());
        assertEquals("Expected order does not match: ",expected.getTotalPrice(), actual.getTotalPrice());
        assertArrayEquals ("arrays not equal", expected.getIdList(), actual.getIdList());

    }
    
    /**
     * Test of updateInventory method, of class Inventory
     */
    @Test
    public void testUpdateInventory() {
        Inventory inv = new Inventory("jdbc:mysql://localhost/inventory","scm","ensf409");
        inv.initializeConnection();
        Connection dbConnect = inv.getConnection();
        boolean x = false;
        Order o = inv.createOrder("chair", "task", 1);
        int sizeOG = inv.resSize("chair", "task");
        inv.updateInventory(o);
        int sizeAf = inv.resSize("chair", "task");

        if(sizeAf < sizeOG && sizeAf == 1) {
            x = true;
        }

        assertTrue("updateInventory method did not delete the records: ",x);
    }
    
    /**
     * Test of getIDsChair method with null Chair[], of class Inventory.
     */
    @Test
    public void testGetIDsChair_nullTest() {
        Inventory inv = new Inventory("jdbc:mysql://localhost/inventory","scm","ensf409");
        Chair[] c = null;
        String[] n = null;
        
        String[] x = inv.getIDsChair(c);
        assertArrayEquals("Did not create null ID String array: ",n,x);
    }
    
    /**
     * Test of getIDsChair method, of class Inventory.
     */
    @Test
    public void testGetIDsChair() {
        Inventory inv = new Inventory("jdbc:mysql://localhost/inventory","scm","ensf409");
        inv.initializeConnection();
        
        Chair[] c = new Chair[2];
        c[0] = new Chair();
        c[0].setID("C0942");
        c[1] = new Chair();
        c[1].setID("C9890");
        
        String[] expected = new String[] {"C0942","C9890"};
        String[] actual = inv.getIDsChair(c);
        
        assertArrayEquals("Did not create expected ID String array: ",expected,actual);
    }

    /**
     * Test of getIDsDesk method with null Desk[], of class Inventory.
     */
    @Test
    public void testGetIDsDesk_nullTest() {
        Inventory inv = new Inventory("jdbc:mysql://localhost/inventory","scm","ensf409");
        Desk[] c = null;
        String[] n = null;
        
        String[] x = inv.getIDsDesk(c);
        assertArrayEquals("Did not create null ID String array: ",n,x);
    }
    
    /**
     * Test of getIDsDesk method, of class Inventory.
     */
    @Test
    public void testGetIDsDesk() {
        Inventory inv = new Inventory("jdbc:mysql://localhost/inventory","scm","ensf409");
        inv.initializeConnection();
        
        Desk[] d = new Desk[2];
        d[0] = new Desk();
        d[0].setID("D1927");
        d[1] = new Desk();
        d[1].setID("D2341");
        
        String[] expected = new String[] {"D1927","D2341"};
        String[] actual = inv.getIDsDesk(d);
        
        assertArrayEquals("Did not create expected ID String array: ",expected,actual);
    }

    /**
     * Test of getIDsFiling method with null Filing[], of class Inventory.
     */
    @Test
    public void testGetIDsFiling_nullTest() {
        Inventory inv = new Inventory("jdbc:mysql://localhost/inventory","scm","ensf409");
        Filing[] c = null;
        String[] n = null;
        
        String[] x = inv.getIDsFiling(c);
        assertArrayEquals("Did not create null ID String array: ",n,x);
    }
    
    /**
     * Test of getIDsFiling method, of class Inventory.
     */
    @Test
    public void testGetIDsFiling() {
        Inventory inv = new Inventory("jdbc:mysql://localhost/inventory","scm","ensf409");
        inv.initializeConnection();
        
        Filing[] f = new Filing[2];
        f[0] = new Filing();
        f[0].setID("F001");
        f[1] = new Filing();
        f[1].setID("F013");
        
        String[] expected = new String[] {"F001","F013"};
        String[] actual = inv.getIDsFiling(f);
        
        assertArrayEquals("Did not create null ID String array: ",expected,actual);
    }

    /**
     * Test of getIDsLamp method with null Lamp[], of class Inventory.
     */
    @Test
    public void testGetIDsLamp_nullTest() {
        Inventory inv = new Inventory("jdbc:mysql://localhost/inventory","scm","ensf409");
        Lamp[] c = null;
        String[] n = null;
        
        String[] x = inv.getIDsLamp(c);
        assertArrayEquals("Did not create null ID String array: ",n,x);
    }
    
    /**
     * Test of getIDsLamp method, of class Inventory.
     */
    @Test
    public void testGetIDsLamp() {
        Inventory inv = new Inventory("jdbc:mysql://localhost/inventory","scm","ensf409");
        inv.initializeConnection();
        
        Lamp[] l = new Lamp[2];
        l[0] = new Lamp();
        l[0].setID("L013");
        l[1] = new Lamp();
        l[1].setID("L208");
        
        String[] expected = new String[] {"L013","L208"};
        String[] actual = inv.getIDsLamp(l);
        
        assertArrayEquals("Did not create expected ID String array: ",expected,actual);
    }
    
    /**
     * Test of compareChairs method, of class Inventory.
     */
    @Test
    public void testCompareChairs() {
		
        Inventory inv = new Inventory("jdbc:mysql://localhost/inventory","scm","ensf409");
        
        inv.initializeConnection();
          
        String [] id = new String[2];
        id[0]= "C4839";
        id[1]= "C5409";
        Order ex = new Order ("chair", "ergonomic", 1, 250, id);   
          
        Order in= inv.compareChairs("ergonomic", 1);
        String [] asd = in.getIdList();
       
        boolean tom = Arrays.equals(asd, id);
	   
        if (ex.getTotalPrice() != in.getTotalPrice()){
                tom = false;
        }
        if (!(ex.getType()).equals(in.getType())){
                 tom = false;
        } 

        if (in.getQuantity() != ex.getQuantity()){
                 tom = false;
        }

        assertEquals("The cheapest filings are not accurate", tom, true);     
    }
	
    /**
    * Test of compareDesks method, of class Inventory.
    */	
    @Test
    public void testCompareDesks() {
		
        Inventory inv = new Inventory("jdbc:mysql://localhost/inventory","scm","ensf409");
        
        inv.initializeConnection();
          
        String [] id = new String[2];
        id[0]= "D1927";
        id[1]= "D2341";
        Order ex = new Order ("desk", "standing", 1, 300, id);   
          
        Order in= inv.compareDesks("standing", 1);
        String [] asd = in.getIdList();
       
        boolean tom = Arrays.equals(asd, id);
	   
        if (ex.getTotalPrice() != in.getTotalPrice()){
                tom = false;
        }
        if(  !(ex.getType()).equals(in.getType())){
                 tom = false;
        } 

        if( in.getQuantity() != ex.getQuantity()){
                 tom = false;
        }
	   
       
        assertEquals("The cheapest filings are not accurate", tom, true);
    }

    /**
     * Test of compareLamps method, of class Inventory.
     */
    @Test
    public void testCompareLamps() {
		
        Inventory inv = new Inventory("jdbc:mysql://localhost/inventory","scm","ensf409");

        inv.initializeConnection();

        String [] id = new String[2];
        id[0]= "L223";
        id[1]= "L982";
        Order ex = new Order ("lamp", "study", 1, 10, id);   
          
        Order in= inv.compareLamps("study", 1);
        String [] asd = in.getIdList();
       
        boolean tom = Arrays.equals(asd, id);
	   
        if (ex.getTotalPrice() != in.getTotalPrice()){
                tom = false;
        }
        if(  !(ex.getType()).equals(in.getType())){
                 tom = false;
        } 

        if( in.getQuantity() != ex.getQuantity()){
                 tom = false;
        }
	   
       
        assertEquals("The cheapest filings are not accurate", tom, true);
             
           
    }

    /**
    * Test of compareFilings method, of class Inventory.
    */
    @Test
    public void testCompareFilings() {
		
        Inventory inv = new Inventory("jdbc:mysql://localhost/inventory","scm","ensf409");
        
        inv.initializeConnection();
          
        String [] id = new String[2];
        id[0]= "F001";
        id[1]= "F013";
        Order ex = new Order ("filing", "small", 1, 100, id);    // expected 
          
        Order in= inv.compareFilings("small", 1);
        String [] asd = in.getIdList();
       
        boolean tom = Arrays.equals(asd, id);
	   
        if (ex.getTotalPrice() != in.getTotalPrice()){
                tom = false;
        }
        if(  !(ex.getType()).equals(in.getType())){
                 tom = false;
        } 

        if( in.getQuantity() != ex.getQuantity()){
                 tom = false;
        }
	   
       
        assertEquals("The cheapest filings are not accurate", tom, true);
             
     
    }
	
    /**
    * Test of resSizes method, of class Inventory.
    */
    @Test
    public void testResSizes() {

        boolean c = false;

        Inventory inv = new Inventory("jdbc:mysql://localhost/inventory","scm","ensf409");

        inv.initializeConnection();

        int exp = inv.resSize("lamp", "desk");

        if (exp == 7) {
            c = true;
        }

        assertTrue("The program does not read all the possible points", c);
    }
    
    /**
    * Test of validFurniture method with an invalid furniture, of class Inventory.
    */	
    @Test
    public void testIsValidFurniture_notValid() {
        Inventory inv = new Inventory("jdbc:mysql://localhost/inventory","scm","ensf409");
        inv.initializeConnection();   
        boolean ex = inv.isValidFurniture("bus");
        assertEquals("Order could not be made since there is no such type of furniture", ex, false);
    }
    
    /**
    * Test of validFurniture method, of class Inventory.
    */	
    @Test
    public void testIsValidFurniture() {
        Inventory inv = new Inventory("jdbc:mysql://localhost/inventory","scm","ensf409");
        inv.initializeConnection();   
        boolean ex = inv.isValidFurniture("chair");
        assertEquals("Order could not be made since there is no such type of furniture", ex, true);
    }
    
    /**
    * Test of isValidQuantity method with an invalid quantity, of class Inventory.
    */
    @Test
    public void testIsValidQuantity_notValid() {	
        Inventory inv = new Inventory("jdbc:mysql://localhost/inventory","scm","ensf409");
        
        inv.initializeConnection();   
        boolean ex = inv.isValidQuantity("-1");
        assertEquals("Input is not valid as a possitive integer", ex, false);
     
    }    
	
	 
    /**
    * Test of isValidQuantity method, of class Inventory.
    */
    @Test
    public void testIsValidQuantity() {	
        Inventory inv = new Inventory("jdbc:mysql://localhost/inventory","scm","ensf409");
        
        inv.initializeConnection();   
        boolean ex = inv.isValidQuantity("1");
        assertEquals("Input is not valid as a possitive integer", ex, true);
     
    }    

}