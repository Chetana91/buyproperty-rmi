package com.constants;

/**
 * RMIConstants.java
 * This class has RemoteInterface IDs and port numbers defined.
 * @author Chetana Vedantam
 * @version 1.0 09/25/2014
 */

public class RMIConstants {

	/**
	 * These are the IDs for binding and lookup of remote stubs in the RMI
	 * Registry
	 */
	public static final String RMI_ID_PROPERTY_DEALER = "PropertyDealerRMI";
	public static final String RMI_ID_BANK = "BankRMI";
	public static final String RMI_ID_BUYER = "BuyerRMI";

	/** These are the port numbers of different servers */
	public static final int RMI_PORT_PROPERTY_DEALER = 1991;
	public static final int RMI_PORT_BANK = 1991;
	public static final int RMI_PORT_BUYER = 1991;

}