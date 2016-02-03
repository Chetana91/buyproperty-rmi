package com.serverclient;

import static org.junit.Assert.*;

import java.rmi.RemoteException;

import org.junit.Test;

import com.classes.Property;

public class PropertyDealerImplTest {

	@Test
	public void testCheckValidPurchase() throws RemoteException {
		PropertyDealerImpl propertyDealerImpl = new PropertyDealerImpl();
		Property p1 = new Property();
		p1 = new Property();
		p1.setName("Sky Line Condos");
		p1.setType("Condo");
		p1.setValue(10000);
		p1.setAlloted(false);
		assertTrue (propertyDealerImpl.checkValidPurchase(p1));
		p1.setAlloted(true);
		assertFalse (propertyDealerImpl.checkValidPurchase(p1));
	}

}
