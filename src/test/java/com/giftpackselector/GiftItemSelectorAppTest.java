package com.giftpackselector;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import com.giftpackselector.exceptions.GiftPackSelectorExceptions;
import com.giftpackselector.services.IGiftPackSelector;
import com.giftpackselector.services.impl.GiftPackSelector;

/**
 * Unit test for GiftItemSelectorApp.
 */
@RunWith(JUnitPlatform.class)

public class GiftItemSelectorAppTest {
	private static final Logger LOGGER = LogManager.getLogger(GiftItemSelectorAppTest.class);


	private IGiftPackSelector giftPackSelector;
	public static final String correctInputFile = "correctInput.txt";
	public static final String totalWeightIsGreaterThanGivenMaxWeightFile = "totalWeightGreaterThanMaxWeight.txt";
	public static final String itemCostGreaterThanMaxCostFile = "itemCostGreaterThanMaxCost.txt";
	public static final String itemWeightGreaterThanMaxWeightFile = "itemWeightGreaterThanMaxWeight.txt";
	public static final String itemCountExceededFile = "itemCountExceeded.txt";
	public static final String invalidItemInputDataFile = "invalidItemInputData.txt";
	public static final String invalidWeightInputDataFile = "invalidWeightInputData.txt";
	public static final String invalidFile = "invalidFile.txt";

	@BeforeEach
	public void setUp() throws Exception {
		giftPackSelector = new GiftPackSelector();

	}

	
	@Test
	public void testMain() throws IOException {
		LOGGER.info("++testMain()");
		
		Assertions.assertThrows(GiftPackSelectorExceptions.class, () -> {
			String[] args = null;
		    GiftItemSelectorApp.main(args);
		});

		LOGGER.info("--testMain()");

	}
	
	
	/**
	 * Test with valid input data
	 */
	@Test
	public void testWithValidInputDataTest() {
		LOGGER.info("++testWithValidInputDataTest()");
		
		Assertions.assertThrows(GiftPackSelectorExceptions.class, () -> {
			giftPackSelector.packetsReadyToSend(correctInputFile);
		});
		
		LOGGER.info("--testWithValidInputDataTest()");

	}

	/**
	 * given max weight is 100 but here is 101: 101 : (1,85.31,€29) (2,14.55,€74)
	 * (3,3.98,€16) (4,26.24,€55) (5,63.69,€52) (6,76.25,€75) (7,60.02,€74)
	 * (8,93.18,€35) (9,89.95,€78)
	 * 
	 */
	@Test
	public void totalWeightIsGreaterThanGivenMaxWeightFileTest() {
		LOGGER.info("++totalWeightIsGreaterThanGivenMaxWeightFileTest()");
		Assertions.assertThrows(GiftPackSelectorExceptions.class, () -> {
			giftPackSelector.packetsReadyToSend(totalWeightIsGreaterThanGivenMaxWeightFile);
		});
		LOGGER.info("--totalWeightIsGreaterThanGivenMaxWeightFileTest()");
	}

	/**
	 * max weight is 100 but here is (1,102,€45) 81 : (1,102,€45) (2,88.62,€98)
	 * (3,78.48,€3) (4,72.30,€76) (5,30.18,€9) (6,46.34,€48)
	 * 
	 */
	@Test
	public void itemWeightGreaterThanMaxWeightFileTest() {
		LOGGER.info("++itemWeightGreaterThanMaxWeightFileTest()");

		Assertions.assertThrows(GiftPackSelectorExceptions.class, () -> {
			giftPackSelector.packetsReadyToSend(itemWeightGreaterThanMaxWeightFile);
		});
		LOGGER.info("--itemWeightGreaterThanMaxWeightFileTest()");

	}

	/**
	 * max cost is 100 but here is (1,53.38,€425) 81 : (1,102,€45) (2,88.62,€98)
	 * (3,78.48,€3) (4,72.30,€76) (5,30.18,€9) (6,46.34,€48)
	 * 
	 */
	@Test
	public void itemCostGreaterThanMaxCostFileTest() {
		LOGGER.info("++itemCostGreaterThanMaxCostFileTest()");

		Assertions.assertThrows(GiftPackSelectorExceptions.class, () -> {
			giftPackSelector.packetsReadyToSend(itemCostGreaterThanMaxCostFile);
		});
		LOGGER.info("--itemCostGreaterThanMaxCostFileTest()");


	}

	/**
	 * allowed items is 15 but here is 18 10 : (1,90.72,€13) (2,33.80,€40)
	 * (3,43.15,€10) (4,37.97,€16) (5,46.81,€36) (6,48.77,€79) (7,81.80,€45)
	 * (8,19.36,€79) (9,6.76,€64)(1,90.72,€13) (2,33.80,€40) (3,43.15,€10)
	 * (4,37.97,€16) (5,46.81,€36) (6,48.77,€79) (7,81.80,€45) (8,19.36,€79)
	 * (9,6.76,€64)
	 * 
	 */
	@Test
	public void itemCountExceededTest() {
		LOGGER.info("++itemCountExceededTest()");

		Assertions.assertThrows(GiftPackSelectorExceptions.class, () -> {
			giftPackSelector.packetsReadyToSend(itemCountExceededFile);
		});
		LOGGER.info("--itemCountExceededTest()");

	}

	@Test
	public void invalidFileTest() {
		LOGGER.info("++invalidFileTest()");

		Assertions.assertThrows(GiftPackSelectorExceptions.class, () -> {
			giftPackSelector.packetsReadyToSend(invalidFile);
		});
		LOGGER.info("--invalidFileTest()");

	}

	/**
	 * Invalid (1,53.38f,€45) weight 81 : (1,53.38f,€45) (2,88.62,€98) (3,78.48,€3)
	 * (4,72.30,€76) (5,30.18,€9) (6,46.34,€48)
	 * 
	 */
	@Test
	public void invalidItemInputDataFileTest() {
		LOGGER.info("++invalidItemInputDataFileTest()");

		Assertions.assertThrows(GiftPackSelectorExceptions.class, () -> {
			giftPackSelector.packetsReadyToSend(invalidItemInputDataFile);
		});
		LOGGER.info("--invalidItemInputDataFileTest()");

	}

	/**
	 * Invalid 81X : weight 81X : (1,53.38,€45) (2,88.62,€98) (3,78.48,€3)
	 * (4,72.30,€76) (5,30.18,€9) (6,46.34,€48)
	 * 
	 */

	@Test
	public void invalidWeightInputDataFileTest() {
		LOGGER.info("++invalidWeightInputDataFileTest()");

		Assertions.assertThrows(GiftPackSelectorExceptions.class, () -> {
			giftPackSelector.packetsReadyToSend(invalidWeightInputDataFile);
		});
		LOGGER.info("--invalidWeightInputDataFileTest()");

	}

}
