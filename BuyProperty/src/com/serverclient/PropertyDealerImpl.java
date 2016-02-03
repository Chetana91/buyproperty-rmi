/**
 * PropertyDealerImpl.java
 * This class generates purchase claims and creates a list of different buyers and properties.
 * @author Chetana Vedantam
 */


package com.serverclient;

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
import com.remote.BankInterface;
import com.remote.PropertyDealerInterface;

public class PropertyDealerImpl extends UnicastRemoteObject implements PropertyDealerInterface{

	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor
	 */

	public PropertyDealerImpl() throws RemoteException {
		super();
	}

	/**
	 * The method accepts list of buyers and their requests and checks if the property is available.
	 * It invokes remote method verifyBuyers to BankImpl and sends all valid purchases.
	 * 
	 * @param purchaseList It contains list of all potential buyers and properties which is processed to obtain a valid purchase list.
	 * @throws RemoteException This method places a remote function call and thus may throw RemoteException
	 * @return void This method does not return any values.
	 */
	@Override
	public void contactDealer(ArrayList<Map<Buyer, Property>> purchaseList) throws RemoteException {
		
		//Iteration in the purchaseList
		ArrayList<Map<Buyer, Property>> list = purchaseList;
		ArrayList<Map<Buyer, Property>> evaluationList = new ArrayList<Map<Buyer,Property>>(10);
		Buyer b;
		Property p;
		boolean valid = false;
		Map<Buyer, Property> map = new HashMap<Buyer, Property>();
		
		int i=0;
		while(i<list.size()){
			map = list.get(i);
			for(Entry<Buyer, Property> entry: map.entrySet()){
				b = entry.getKey();
				p = entry.getValue();
				/** check if the property can be alloted to the buyer then consider for bank approval */
				valid = checkValidPurchase(p);
				if(valid) {
					evaluationList.add(map);
				}
				System.out.println(String.format(" %-12s| %-16s| %s",b.getName(), p.getName(),valid));
				System.out.println(String.format(" %-12s| %-16s|",b.hashCode(), p.hashCode()));
				System.out.println("-------------------------------");
			}
			i++;
		}
		
		/** Request bank for evaluation of finances i.e. RMI call to invoke verifyBuyer*/

		Registry registry = LocateRegistry.getRegistry(RMIConstants.RMI_PORT_BANK);
		BankInterface bank=null;
		try {
			bank = (BankInterface) registry.lookup(RMIConstants.RMI_ID_BANK);
		} catch (NotBoundException e) {
			System.out.println("Problem is here PropertyDealerImpl 3");
			System.out.println("NotBoundException!\n"+e.getMessage());
		}
		catch (RemoteException e) {
			System.out.println("Problem is here PropertyDealerImpl 4");
			System.out.println("RemoteException!\n"+e.getMessage());
		}
		if(bank!=null) {
			//call bank 
			try {
				bank.verifyBuyers(evaluationList);
			}
			catch (Exception e) {
				System.out.println("Problem is here PropertyDealerImpl 5");
				System.out.println("Exception!\n"+e.getMessage());
			}
		}
	}

	/**
	 * The method checks if the property is available.
	 * 
	 * @param property An object of Property.
	 * @return boolean The method returns true if property is available else false.
	 */
	public boolean checkValidPurchase(Property property) {
		if(property.isAlloted())
			return false;
		else
			return true;
	}
	
	/**
	 * The main method for the PropertyDealerImpl program.
	 * 
	 * @param args
	 *            Not used
	 */
	public static void main(String args[]) {
		try {
			PropertyDealerImpl propertyDealer = new PropertyDealerImpl();
			Registry registry = LocateRegistry.getRegistry("localhost",RMIConstants.RMI_PORT_PROPERTY_DEALER);
			registry.rebind(RMIConstants.RMI_ID_PROPERTY_DEALER, propertyDealer);
			System.out.println("Propertey Dealer Ready");
		} catch (RemoteException e) {
			System.out.println("Problem is here PropertyDealerImpl 2");
			e.printStackTrace();
		}
	}
}
