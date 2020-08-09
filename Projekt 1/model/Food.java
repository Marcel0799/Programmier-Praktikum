package model;

import java.io.Serializable;

/**
	 * Represents an Object which is eat- or drinkable.
	 * Every Food has to have a name and a price. 
	 * @author Marcel Bienia
	 */
public class Food implements Serializable, Comparable {

	private String name;

	private int price;

	//The cashReturn represents the Pfand which equals zero if the Food has no Return value.
	private int cashReturn;

	private String retailer;

	private int discount;

	private boolean isSubscription;

    /**
     * Minimal constructor
     * @param name of the food
     * @param price of the food
     */
	public Food(String name, int price) {
		this.name = name;
		this.price = price;
		this.cashReturn = 0;
		this.retailer = "";
		this.discount = 0;
		this.isSubscription = false;
	}

	/**
	 * Maximal constructor
	 * @param name of the food
	 * @param price of the food
	 * @param cashReturn of the food
	 * @param retailer of the food
	 * @param discount of the food
	 * @param isSubscription true if the food is an subscription
	 */
	public Food(String name, int price, int cashReturn , String retailer, int discount, boolean isSubscription) {
		this.name = name;
		this.price = price;
		this.cashReturn = cashReturn;
		this.retailer = retailer;
		this.discount = discount;
		this.isSubscription = isSubscription;
	}

    /**
     * setter for name
     * @param name  is the new name
     */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * getter for name
	 * @return name of the food
	 */
	public String getName() {
		return name;
	}

    /**
     * setter for price
     * @param price is the new price
     */
	public void setPrice(int price) {
		this.price = price;
	}
	
	/**
	 * getter for price with discount
	 * @return price with discount ((price * discount) / 100)
	 */
	public int getPrice() {
		int discountOfPrice = ((price * discount)/100);
		return price - discountOfPrice;
	}
	/**
	 * getter for price without discount
	 * @return price without discount
	 */
	public int getPriceWithoutDiscount() {
		return price;
	}

    /**
     * setter for cashReturn
     * @param cashReturn sets the new cashReturn
     */
	public void setCashReturn(int cashReturn) {
		this.cashReturn = cashReturn;
	}
	/**
	 * getter for cashReturn
	 * @return cashReturn of the food
	 */
	public int getCashReturn() {
		return cashReturn;
	}
	
    /**
     * setter for retailer
     * @param retailer is the new name of the retailer
     */
	public void setRetailer(String retailer) {
		this.retailer = retailer;
	}
	/**
	 * getter for retailer
	 * @return retailer name of the retailer
	 */
	public String getRetailer() {
		return retailer;
	}
	
    /**
     * setter for discount
     * @param discount is the new discount
     */
	public void setDiscount(int discount) {
		this.discount = discount;
	}
	
	/**
	 * getter for discount
	 * @return the discount of the food
	 */
	public int getDiscount() {
		return discount;
	}
	
    /**
     * setter for isSubscription
     * @param isSub if the food is an subscription
     */
	public void setIsSubscription(boolean isSub) {
		this.isSubscription = isSub;
	}
	
	/**
	 * getter for isSubscription
	 * @return isSubscription
	 */
	public boolean getIsSubscription() {
		return this.isSubscription;
	}
	
	/**
	 * change the hashCode that it works again
	 */
    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + cashReturn;
		result = prime * result + discount;
		result = prime * result + (isSubscription ? 1231 : 1237);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + price;
		result = prime * result + ((retailer == null) ? 0 : retailer.hashCode());
		return result;
	}

	/**
     * Equals Method
     * @return true if calling Food is equal to the second food  
     * @param obj is the second Food
     */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Food other = (Food) obj;
		if (cashReturn != other.cashReturn)
			return false;
		if (discount != other.discount)
			return false;
		if (isSubscription != other.isSubscription)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (price != other.price)
			return false;
		if (retailer == null) {
			if (other.retailer != null)
				return false;
		} else if (!retailer.equals(other.retailer))
			return false;
		return true;
	}

	/**
	 * change the compareTo that it can compare Foods to other Foods
	 */
	@Override
	public int compareTo(Object object) {
		if (object == null)
			return 0;
		if (getClass() != object.getClass())
			return 0;
		Food other = (Food) object;
		return this.name.compareTo(other.getName());
	}

	/**
	 * change the toString that it returns the name
	 * @return name String
	 */
	@Override
	public String toString() {
		return name + " | " + getPrice() + "€ | " + cashReturn + "€ | " + retailer ;
	}
}
