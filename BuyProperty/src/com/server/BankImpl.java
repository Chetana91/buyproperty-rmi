package com.server;

import java.rmi.AlreadyBoundException;
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
import com.remote.BuyerInterface;

/**
 * BankImpl.java This class acts as server to PropertyDealerImpl. Its main task
 * is to determine if a buyer may purchase the properties he claims or not.
 * 
 * @author Chetana Vedantam
 * @version 1.0 09/25/2014
 */

public class BankImpl extends UnicastRemoteObject implements BankInterface {

	private static final long serialVersionUID = 1L;
	
	int maxSize = 0;
	ArrayList<Buyer> buyersList = null;
	ArrayList<Double> buyersFinances = null;
	ArrayList<ArrayList<Property>> PropertyList = null;
	boolean[] purchasesValid;
	boolean[] loanOffered;
	
	/** Constructor for BankImpl Class
	 * @throws RemoteException
	 */
	protected BankImpl() throws RemoteException {
		super();
	}
	
	/**
	 * This method determines whether a buyer may claim his purchases or not.
	 * 
	 * @throws RemoteException It may throw a Remote Exception
	 * @param evaluationList It contains list of all potential buyers and properties for which evaluation is to be done. 
	 * @return void This method does not return any values.
	 */

	@Override
	public void verifyBuyers(ArrayList<Map<Buyer, Property>> evaluationList) throws RemoteException {

		System.out.println("At the bank. Processing eligibility of buyers.");

		ArrayList<Map<Buyer, Property>> list = new ArrayList<Map<Buyer,Property>>(evaluationList);
		Buyer b;
		Property p;
		Map<Buyer, Property> map = new HashMap<Buyer, Property>();
		int i = 0;
		System.out.println("*****Evaluation List***** ");
		System.out.println("-------------------------------");
		while (i < list.size()) {
			map = list.get(i);
			for (Entry<Buyer, Property> entry : map.entrySet()) {
				b = entry.getKey();
				p = entry.getValue();
				System.out.println(String.format(" %-12s| %s",b.getName(), p.getName()));
				System.out.println(String.format(" %-12s| %s",b.hashCode(), p.hashCode()));
				System.out.println("-------------------------------");
			}
			i++;
		}

		/** creating a unique buyer's list as the evaluationList may contain multiple references of same buyer */

		maxSize = evaluationList.size();
		buyersList = new ArrayList<Buyer>(maxSize);
		buyersFinances = new ArrayList<Double>(maxSize);
		PropertyList = new ArrayList<ArrayList<Property>>(maxSize);
		ArrayList<Property> subList = new ArrayList<Property>();
		purchasesValid = new boolean[maxSize];
		loanOffered = new boolean[maxSize];

		i = 0;
		int position = 0, j = 0;
		double balanceAmount = 0.0;
		while (i < list.size()) {
			map = list.get(i);
			for (Entry<Buyer, Property> entry : map.entrySet()) {
				b = entry.getKey();
				p = entry.getValue();
				// CHECKS REFERENTIAL INTEGRITY
				if (!buyersList.contains(b)) {
					buyersList.add(b);
					balanceAmount = b.getFinances() - p.getValue();
					buyersFinances.add(balanceAmount);
					purchasesValid[j] = true;
					loanOffered[j] = false;
					subList = new ArrayList<Property>();
					subList.add(p);
					j++;
				} else {
					position = buyersList.indexOf(b); // search for the buyer
					balanceAmount = buyersFinances.get(position);
					balanceAmount = balanceAmount - p.getValue();
					buyersFinances.set(position, balanceAmount);
					subList.add(p);
					if (balanceAmount < 0) {
						// bank offers loan to buyers if shortage money <= (0.25*finances)
						if ((Math.abs(balanceAmount) <= 0.25 * b.getFinances())) {
							loanOffered[j] = true;
						} else {
							loanOffered[j] = false;
							// properties cannot be bought by the buyer with available finances
							purchasesValid[position] = false;
							subList.clear();
						}
					}
				}
				PropertyList.add(subList);
			}
			i++;
		}
		
		// Displaying final result
		System.out.println("*****Final Purchase List*****");
		System.out.println("-----------------------------------------");
		System.out.println(" Buyer || Accepted || BalanceAmount || LoanOffered || Property ");
		System.out.println("-----------------------------------------");
		for (i = 0; i < buyersList.size(); i++) {
			System.out.print( String.format(" %-6s| %-5s | %-9s | %-7s |",buyersList.get(i).getName(),purchasesValid[i],buyersFinances.get(i),loanOffered[i]));
			subList = PropertyList.get(i);
			for(j=0;j<subList.size();j++) {
				System.out.print(subList.get(j).getName() + " ; ");
			}
			System.out.println("\n-----------------------------------------");
		}
		
		/** Send result back to PurchasePoint */

		Registry registry = LocateRegistry.getRegistry(RMIConstants.RMI_PORT_BANK);
		BuyerInterface buyerInterface=null;
		try {
			buyerInterface = (BuyerInterface) registry.lookup(RMIConstants.RMI_ID_BUYER);
		} catch (NotBoundException e) {
			System.out.println("Problem is here BankImpl 3");
			System.out.println("NotBoundException!\n"+e.getMessage());
		}
		catch (RemoteException e) {
			System.out.println("Problem is here BankImpl 4");
			System.out.println("RemoteException!\n"+e.getMessage());
		}
		if(buyerInterface!=null) {
			//call PurchasePoint 
			try {
				buyerInterface.notifyBuyersLoanDetails(buyersList, loanOffered, PropertyList);
			}
			catch (Exception e) {
				System.out.println("Problem is here BankImpl 5");
				System.out.println("Exception!\n"+e.getMessage());
			}
		}

	}

	/**
	 * The main method for the BankImpl program.
	 * 
	 * @param args
	 *            Not used
	 * @throws AlreadyBoundException 
	 */

	public static void main(String args[]) throws AlreadyBoundException {
		Registry registry = null;
		try {
			BankImpl bankImpl = new BankImpl();
			// Registry registry = LocateRegistry.createRegistry(RMIConstants.RMI_PORT_BANK);
			registry = LocateRegistry.getRegistry(RMIConstants.RMI_PORT_BANK);
			registry.rebind(RMIConstants.RMI_ID_BANK, bankImpl);
			System.out.println("Bank Ready");
		} catch (RemoteException e) {
			System.out.println("Problem is here BankImpl 2");
			System.out.println("RemoteException!\n"+e.getMessage());
		}

	}
}
