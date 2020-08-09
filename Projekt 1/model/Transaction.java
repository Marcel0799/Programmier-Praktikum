package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collections;

/**
 * Represents a Purchase or a Charge
 * @author Marcel Bienia
 */
public abstract class Transaction  implements Serializable, Comparable {

	private LocalDateTime transactionDate;

	private int value;

	private final boolean isPurchase;
	
    /**
     * abstract constructor
	 * @param date - Das Datum der Transaktion
	 * @param value - der Betrag der Transaktion
	 * @param isPurchase - ob, die Transaktion ein Kauf (und keine Guthaben-Aufladung) ist
     */
	public Transaction(LocalDateTime date, int value, boolean isPurchase) {
		this.transactionDate = date;
		this.value = value; 
		this.isPurchase = isPurchase;
	}
	
    /**
     * getter for Transaction
     * @return transactionDate the date of the transaction
     */
	public LocalDateTime getTransactionDate() {
		return transactionDate;
	}
	
	/** 
	 * setter for the transaction time
	 * @param date the new transaction time
	 */
	public void setTransactionDate(LocalDateTime date) {
		this.transactionDate = date;
	}

    /**
     * getter for value
     * @return the value of the transaction
     */
	public int getValue() {
		return value;
	}
	
	/**
	 * setter for value
	 * @param newValue is the new value
	 */
	public void setValue(int newValue) {
		this.value = newValue;
	}
	
    /**
     * getter for isPurchase
     * @return isPurchase
     */
	public boolean getIsPurchase() {
		return isPurchase;
	}
	
	/**
	 * change the hashCode that it works again
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (isPurchase ? 1231 : 1237);
		result = prime * result + ((transactionDate == null) ? 0 : transactionDate.hashCode());
		result = prime * result + value;
		return result;
	}

	/**
	 * checks if this transaction is equal to the other transaction
	 * @param obj is the other transaction
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Transaction other = (Transaction) obj;
		if (isPurchase != other.isPurchase) {
			return false;
		}
		if (transactionDate == null) {
			if (other.transactionDate != null) {
				return false;
			}
		} else if (!transactionDate.equals(other.transactionDate)) {
			return false;
		}
		if (value != other.value) {
			return false;
		}
		return true;
	}

	/**
	 * Compare the transaction dates.
	 * @param object
	 * @return
	 */
	@Override
	public int compareTo(Object object) {
		if (object == null)
			return 0;
		if (getClass() != object.getClass())
			return 0;

		Transaction other = (Transaction) object;
		if(transactionDate.isBefore(other.getTransactionDate())) {
			return -1;
		} else if(transactionDate.isEqual(other.getTransactionDate())) {
			return 0;
		} else {
			return 1;
		}
	}
}
