package com.classes;

import java.io.Serializable;

/**
 * Property.java
 * The class defines attributes of a property
 * It holds the following details:
 *  - name: Property name,
 *  - value: Value of Property
 *  - type: Category of the property
 *  - alloted: If the property has been allotted this value us true, else false
 * @author Chetana Vedantam
 * @version 1.0 09/25/2014
 */

public class Property implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String name;
	
	private double value;
	
	private String type;
	
	private boolean alloted;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isAlloted() {
		return alloted;
	}

	public void setAlloted(boolean alloted) {
		this.alloted = alloted;
	}

}
