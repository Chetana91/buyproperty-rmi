package com.classes;

import java.io.Serializable;

/**
 * Buyer.java
 * The class defines attributes of a buyer (who intends to purchase property)
 * It holds the following details:
 *  - name : Buyer's Name,
 *  - contact number : Buyer's Phone Number
 *  - financial assets : Money the Buyer is willing to spend.
 * @author Chetana Vedantam
 * @version 1.0 09/25/2014
 */

public class Buyer implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String name;
	
	private String contactNumber;
	
	private double finances;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public double getFinances() {
		return finances;
	}

	public void setFinances(double finances) {
		this.finances = finances;
	}

}
