/**
 * 
 */
package com.giftpackselector.services;

/**
 * This interface is exposed the gift item selector API
 * 
 * @author Narendra.Kumar
 *
 */
public interface IGiftPackSelector {

	/**
	 * This method is used to return the selected items which are are ready to send.
	 * 
	 * @param fileName
	 * @return
	 */
	public String packetsReadyToSend(String fileName);

}
