package com.client;

import static org.junit.Assert.*;

import java.rmi.RemoteException;

import org.junit.Test;

public class PurchasePointTest {
	
	PurchasePoint purchasePoint;

	@Test
	public void testGenerateBuyers() throws RemoteException {
		purchasePoint = new PurchasePoint();
		purchasePoint.generateBuyers();
		assertNotNull(purchasePoint.b1);
		assertNotNull(purchasePoint.b2);
	}

	@Test
	public void testGenerateProperties() throws RemoteException {
		purchasePoint = new PurchasePoint();
		purchasePoint.generateProperties();
		assertNotNull(purchasePoint.p1);
		assertNotNull(purchasePoint.p2);
		assertNotNull(purchasePoint.p3);
		assertNotNull(purchasePoint.p4);
	}
	
	@Test
	public void testGeneratePurchaseList() throws RemoteException {
		purchasePoint = new PurchasePoint();
		assertEquals("PurchaseList size initailly" , 0, purchasePoint.purchaseList.size());
		
		purchasePoint.generateBuyers();
		purchasePoint.generateProperties();
		
		purchasePoint.generatePurchaseList();
		
		assertEquals("PurchaseList size initailly" , 4, purchasePoint.purchaseList.size());
	}

}
