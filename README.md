# FurnitureCombo
ENSF409 Project + Hack Your Learning
1) Change directory one command line to root directory.

2) Compile the code. You can type this into the command line:
    javac -cp .;lib/mysql-connector-java-8.0.23.jar edu/ucalgary/ensf409/Inventory.java
    (** Note: syntax will vary machine to machine, this is the syntax for a windows machine)
      
3) Run the code. You can type this into the command line:
    java -cp .;lib/mysql-connector-java-8.0.23.jar edu/ucalgary/ensf409/Inventory
    (** Note: syntax will vary machine to machine, this is the syntax for a windows machine)
      
4) Next, it will prompt for some info in the terminal:
      		
      - first the FURNITURE: chair, desk, filing, or lamp 
      	(if the furniture inputed is not one of these it will prompt you to try entering
      	furniture again).
      
      - second the TYPE of that furniture: if the type of furniture is invalid and/or
      	does not match any of the types for that furniture in the database (i.e. there is
     		not a single item of that type in inventory) it will prompt you to try entering
   			a type again.
        
      - third it will ask for QUANTITY: if quantity is not valid it will prompt you to enter
      	quantity again (for a valid quantity it must be a integer greater than zero).
      
5) Then, it will prompt for you to enter a filename for the order form (do NOT include an extention).
      
6) Finally, terminal will notify if an order or not could not be placed (includes some info
    about the order if any). The order form created will be saved in the root directory and
    holds more information about the order.
