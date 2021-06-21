/**
 * 
 */
package com.giftpackselector.constants;

/**
 * @author Narendra.Kumar
 * 
 *         This interface holds all error constants which are used in this
 *         project
 *
 */
public interface GiftPackSelectorErrorConstants {
	public String EMPTY = "";
	public String SPACE = " ";
	public String FILE_NAME_LOCATION_ERROR_CODE = "1001";
	public String FILE_NAME_LOCATION_ERROR_MESSAGE = "File name or location is incorrect.";

	public String MAX_WEIGHT_ERROR_CODE = "1002";
	public String MAX_WEIGHT_ERROR_MESSAGE = "Total Package weight limit exceeded.";

	public String MAX_PACKAGE_COUNT_ERROR_CODE = "1003";
	public String MAX_PACKAGE_COUNT_ERROR_MESSAGE = "Total package count limit exceeded.";

	public String INVALID_INPUT_FORMAT_ERROR_CODE = "1004";
	public String INVALID_INPUT_FORMAT_ERROR_MESSAGE = "File contains invalid input format.";

	public String GIFT_ITEM_WEIGHT_OR_AND_COST_LIMIT_EXCEEDED_ERROR_CODE = "1005";
	public String GIFT_ITEM_WEIGHT_OR_AND_COST_LIMIT_EXCEEDED_ERROR_MESSAGE = "Gift Item weight or/and cost limit exceeded.";

}
