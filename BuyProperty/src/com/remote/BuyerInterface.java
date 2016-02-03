package com.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import com.classes.Buyer;
import com.classes.Property;

public interface BuyerInterface extends Remote{
	
	//bank notifies the buyer if a loan is required.
	public void notifyBuyersLoanDetails(ArrayList<Buyer> buyersList,boolean[] loanOffered, ArrayList<ArrayList<Property>> PropertyList) throws RemoteException;

}
