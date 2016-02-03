package com.client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.classes.Buyer;
import com.classes.Property;
import com.constants.RMIConstants;
import com.remote.BuyerInterface;
import com.remote.PropertyDealerInterface;

/**
 * PurchasePoint.java This class generates purchase claims and creates a list of
 * different buyers and properties.
 * 
 * @author Chetana Vedantam
 * @version 1.0 09/25/2014
 */

public class PurchasePoint extends UnicastRemoteObject implements BuyerInterface{

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 */
	public PurchasePoint() throws RemoteException {
		super();
	}

	/** Variables to store Buyers data */
	Buyer b1, b2, b3, b4;

	/** Variables to store Property Data */
	Property p1, p2, p3, p4;

	/** purchaseList contains all purchases multiple buyers want to make */
	ArrayList<Map<Buyer, Property>> purchaseList = new ArrayList<Map<Buyer, Property>>();

	/**
	 * This method generates Purchase List.
	 * 
	 * @param void This method does not take any parameter.
	 * @return void This method does not return any values.
	 */
	public void generatePurchaseList() {
		purchaseList = new ArrayList<Map<Buyer, Property>>(10);
		Map<Buyer, Property> purchaseMap = new HashMap<Buyer, Property>();
		purchaseMap.put(b1, p1);
		purchaseList.add(purchaseMap);
		purchaseMap = new HashMap<Buyer, Property>();
		purchaseMap.put(b2, p2);
		purchaseList.add(purchaseMap);
		/** Buyer b2 creates another record for himself b3 but reference b2.*/
		b3 = b2;
		purchaseMap = new HashMap<Buyer, Property>();
		purchaseMap.put(b3, p3);
		purchaseList.add(purchaseMap);
		b4 = b1;
		purchaseMap = new HashMap<Buyer, Property>();
		purchaseMap.put(b4, p4);
		purchaseList.add(purchaseMap);
	}

	/**
	 * This method generates Buyers Data
	 * 
	 * @param void This method does not take any parameter.
	 * @return void This method does not return any values.
	 */
	public void generateBuyers() {
		b1 = new Buyer();
		b1.setName("Ron");
		b1.setContactNumber("XXX-XXXX");
		b1.setFinances(15000);

		b2 = new Buyer();
		b2.setName("Kel");
		b2.setContactNumber("YYY-YYYY");
		b2.setFinances(20000);
	}

	/**
	 * Generates Property Data
	 * 
	 * @param void This method does not take any parameter.
	 * @return void This method does not return any values.
	 */
	public void generateProperties() {

		p1 = new Property();
		p1.setName("Sky Line Condos");
		p1.setType("Condo");
		p1.setValue(10000);
		p1.setAlloted(false);

		p2 = new Property();
		p2.setName("Mansion Estates");
		p2.setType("Bungalow");
		p2.setValue(20000);
		p2.setAlloted(false);

		p3 = new Property();
		p3.setName("Village Cottage");
		p3.setType("Bungalow");
		p3.setValue(25000);
		p3.setAlloted(false);

		p4 = new Property();
		p4.setName("XYZ Heights");
		p4.setType("Apartment");
		p4.setValue(1500);
		p4.setAlloted(true);
	}
	
	public void printPurchaseList(){
		Buyer b;
		Property p;
		Map<Buyer, Property> map = new HashMap<Buyer, Property>();
		int i = 0;
		System.out.println("*****Purchase List*****");
		System.out.println("-------------------------------");
		while (i < purchaseList.size()) {
			map = purchaseList.get(i);
			for (Entry<Buyer, Property> entry : map.entrySet()) {
				b = entry.getKey();
				p = entry.getValue();
				System.out.println(String.format("%s | %-10s| %s",i+1,b.getName(), p.getName()));
				System.out.println(String.format("   %-11s| %s",b.hashCode(), p.hashCode()));
				System.out.println("-------------------------------");
			}
			i++;
		}
	}
	
	
	/** The method notifies PurchasePoint if the purchases have been accepted with loan details.
	* @throws RemoteException It may throw a Remote Exception
	 * @param buyersList It contains buyer's details.
	 * @param purchasesValid It contains whether for each buyer purchase request has been accepted
	 * @param loanOffered true if bank offers loan, else false.
	 * @return void This method does not return any values.
	 */
	@Override
	public void notifyBuyersLoanDetails(ArrayList<Buyer> buyersList,boolean loanOffered[], ArrayList<ArrayList<Property>> PropertyList) throws RemoteException {
		ArrayList<Property>subList = null;
		String offered = "Yes";
		System.out.println("*****Final Purchase List*****");
		System.out.println("-----------------------------------------");
		System.out.println(" Buyer || LoanOffered || Property");
		System.out.println("-----------------------------------------");
		for (int i = 0; i < buyersList.size(); i++) {
			System.out.print( String.format(" %-6s||",buyersList.get(i).getName()));
			if(loanOffered[i])
				offered = "Yes";
			else
				offered = "No";
			System.out.print( String.format(" %-9s || ",offered));
			subList = PropertyList.get(i);
			for(int j=0;j<subList.size();j++) {
				System.out.print(subList.get(j).getName() + " ; ");
			}
			System.out.println("\n-----------------------------------------");
		}
	}
	
	/**
	 * The main method for the PurchasePoint program. It invokes remote
	 * method contactDealer to PropertyDealer and sends purchaseList.
	 * 
	 * @param args Not used
	 */

	public static void main(String args[]) throws RemoteException, NotBoundException {

		PurchasePoint purchasePoint = new PurchasePoint();
		purchasePoint.generateBuyers();
		purchasePoint.generateProperties();
		purchasePoint.generatePurchaseList();
		purchasePoint.printPurchaseList();
		
		/** RMI Binding method*/
		
		try {
			Registry anotherRegistry = LocateRegistry.getRegistry(RMIConstants.RMI_PORT_BUYER);
			anotherRegistry.rebind(RMIConstants.RMI_ID_BUYER, purchasePoint);
			System.out.println("\nBuyer Ready\n");
		} catch (RemoteException e) {
			System.out.println("Problem is here PurchasePoint 4");
			e.printStackTrace();
		}

		/** RMI Method Invocation */
		
		Registry registry = LocateRegistry.getRegistry(RMIConstants.RMI_PORT_PROPERTY_DEALER);
		PropertyDealerInterface propertyDealerInterface=null;
		try {
			propertyDealerInterface = (PropertyDealerInterface) registry.lookup(RMIConstants.RMI_ID_PROPERTY_DEALER);
		} catch (NotBoundException e) {
			System.out.println("Problem is here PurchasePoint 1");
			System.out.println("NotBoundException!\n"+e.getMessage());
		}
		catch (RemoteException e) {
			System.out.println("Problem is here PurchasePoint 2");
			System.out.println("RemoteException!\n"+e.getMessage());
		}
		if(propertyDealerInterface!=null) {
			try {
				propertyDealerInterface.contactDealer(purchasePoint.purchaseList);
			}
			catch (Exception e) {
				System.out.println("Problem is here PurchasePoint 3");
				System.out.println("Exception!\n"+e.getMessage());
			}
		}

	}
	
}