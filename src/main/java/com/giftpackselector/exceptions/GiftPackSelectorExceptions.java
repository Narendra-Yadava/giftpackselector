/**
 * 
 */
package com.giftpackselector.exceptions;

/**
 * @author Narendra.Kumar
 * 
 *         This class is used to contain the error code and error message
 *
 */
public class GiftPackSelectorExceptions extends RuntimeException {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	/** errorCode */
	private String errorCode;

	/** errorMessage */
	private String errorMessage;

	/**
	 * @param errorCode
	 * @param errorMessage
	 */
	public GiftPackSelectorExceptions(String errorCode, String errorMessage) {
		super(errorMessage);
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;

	}

}
