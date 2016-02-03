package com.allTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.client.PurchasePointTest;
import com.serverclient.PropertyDealerImplTest;

@RunWith(Suite.class)
@SuiteClasses({ PurchasePointTest.class , PropertyDealerImplTest.class})
public class AllTests {

}
