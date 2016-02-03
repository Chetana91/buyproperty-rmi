package com.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Map;

import com.classes.Buyer;
import com.classes.Property;

/**
 * BankInterface.java
 * This is the remote interface which contains the methods for clients (PropertyDealer and PurchasePoint).
 * @author Chetana Vedantam
 * @version 1.0 09/25/2014
 */

public interface BankInterface extends Remote{

	/**
	 * This method evaluates the list and determines whether a buyer may claim his purchases or not.
	 * He can have enough funds to purchase, else the bank offers loan to the buyer under specific criteria.
	 * 
	 * @throws RemoteException It may throw a Remote Exception
	 * @param evaluationList It contains list of all potential buyers and properties for which evaluation is to be done. 
	 * @return void This method does not return any values.
	 */
	public void verifyBuyers(ArrayList<Map<Buyer, Property>> evaluationList) throws RemoteException;

}