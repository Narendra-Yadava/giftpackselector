package com.giftpackselector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.giftpackselector.services.IGiftPackSelector;
import com.giftpackselector.services.impl.GiftPackSelector;

/**
 * This class is main class which starts application
 * 
 * @author Narendra.Kumar
 * 
 */
public class GiftItemSelectorApp {
	private static final Logger LOGGER = LogManager.getLogger(GiftItemSelectorApp.class);

	public static final String fileName = "sampleinput.txt";

	public static void main(String[] args) {

		IGiftPackSelector iGiftPackSelector = new GiftPackSelector();

		String selectedGiftItems = iGiftPackSelector.packetsReadyToSend(fileName);
		LOGGER.info(selectedGiftItems);

	}
	
	
}
