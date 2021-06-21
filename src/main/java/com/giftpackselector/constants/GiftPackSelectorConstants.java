/**
 * 
 */
package com.giftpackselector.constants;

import java.util.regex.Pattern;

/**
 * @author Narendra.Kumar
 * 
 *         This interface holds all constants which are used in this project
 */
public interface GiftPackSelectorConstants {
	public String EMPTY = "";
	public String SPACE = " ";
	public String COLON = ":";
	public String BRACKET_CONTENT_FETCH_REGEX="\\((.*?)\\)";
	public int MAX_PACKAGE_WEIGHT = 100;
	public int MAX_ITEM_WEIGHT = 100;
	public int MAX_ITEM_COST = 100;
	public int MAX_ITEMS_COUNT = 15;
    public Pattern pattern = Pattern.compile("\\(([^)]*)\\)");

}
