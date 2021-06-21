/**
 * 
 */
package com.giftpackselector.models;

/**
 * This class holds the gift item data
 * @author Narendra.Kumar
 *
 */
public class GiftItem  {
	public Integer sn;
	public double weight;
	public double cost;
	
	


	public GiftItem(int sn, double weight, double cost) {
		super();
		this.sn = sn;
		this.weight = weight;
		this.cost = cost;
	}

	/**
	 * @return the sn
	 */
	public Integer getSn() {
		return sn;
	}


	/**
	 * @return the weight
	 */
	public double getWeight() {
		return weight;
	}

	

	/**
	 * @return the cost
	 */
	public double getCost() {
		return cost;
	}

	
}
