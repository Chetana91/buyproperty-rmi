package com.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Map;

import com.classes.Buyer;
import com.classes.Property;

/**
 * PropertyDealerInterface.java
 * This is the remote interface which contains the methods for client (PurchasePoint).
 * @author Chetana Vedantam
 * @version 1.0 09/25/2014
 */

public interface PropertyDealerInterface extends Remote{
	
	/**
	 * This method first validates property purchase claims of a buyer and then further sends the request to BankImpl.
	 * A valid purchase is when the property is available for purchase.
	 * 
	 * @throws RemoteException It may throw a Remote Exception
	 * @param purchaseList It contains list of all potential buyers and properties which is processed to obtain a valid purchase list.
	 * @return void This method does not return any values.
	 */
	public void contactDealer(ArrayList<Map<Buyer, Property>> purchaseList) throws RemoteException;

}
