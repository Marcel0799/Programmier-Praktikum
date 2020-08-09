package model;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * A Purchase is an Food that was bought
 * @author Marcel Bienia
 */
public class Purchase extends Transaction  implements Serializable {

	private boolean returned;

	private Food food;

	private String name;
	/**
	 * minimal Constructor
	 * @param food is the food that was bought
	 * @param returned if the food was returned or is returnable
	 * @param time TODO
	 * @param time of the Purchase or order
	 */
	public Purchase(Food food, boolean returned, LocalDateTime time) {
		super(time, food.getPrice(), true);
		this.food = food;
		this.returned = returned;
		name = food.getName();
	}
	
    /**
     * getter for returned
     * @return returned if the Purchased was returned
     */
	public boolean getReturned() {
		return this.returned;
	}
	/**
	 * setter for returned
	 * @param returned is the new returned
	 */
	public void setReturned(boolean returned) {
		this.returned = returned;
	}
	
    /**
     * setter for food
     * @param food is the new Food
     */
	public void setFood(Food food) {
		this.food = food;
		this.setValue(food.getPrice());
	}
	/**
	 * getter for food
	 * @return food the food of the purchase
	 */
	public Food getFood() {
		return food;
	}

	/**
	 * change the hashCode that it works again
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((food == null) ? 0 : food.hashCode());
		result = prime * result + (returned ? 1231 : 1237);
		return result;
	}

	/**
	 * checks if this Purchase is equal to the other purchase
	 * @param obj is the other purchase
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Purchase other = (Purchase) obj;
		if (food == null) {
			if (other.food != null) {
				return false;
			}
		} else if (!food.equals(other.food)) {
			return false;
		}
		if (returned != other.returned) {
			return false;
		}
		return true;
	}

	/**
	 * get method
	 * @return name String
	 */
	public String getName() {
		return name;
	}

	/**
	 * change toString that it returns all the Information a Purchase has
	 * @return String
	 */
	@Override
	public String toString() {
		return food + " geliefert am " + this.getTransactionDate().toLocalDate() + " von " + food.getRetailer();
	}
}
