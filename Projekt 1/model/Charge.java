package model;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * A Charge is the Payment of an Employee. Which is added to his/her History as a Transaction
 * @author Marcel Bienia
 */
public class Charge extends Transaction implements Serializable {

	private String description;
	
    /**
     * constructor
	 * @param value - der Betrag der Aufladung
	 * @param description - die beschreibung der Aufladung (Pfand oder Aufladung)
     */
	public Charge(int value, String description) {
		super(LocalDateTime.now(), value, false);
		this.description = description;
	}

    /**
     * getter for description
     * @return description
     */
	public String getDescription() {
		return description;
	}
	
	/**
	 * setter for description
	 * @param newDescription - die Beschreibung der Aufladung - ob es eine Pfandrückgabe oder eine Guthaben-Aufladung ist
	 */
	public void setDescription(String newDescription) {
		this.description = newDescription;
	}

	/**
	 * change hashCode that it works again
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		return result;
	}

	/**
	 * checks if this charge equals the other charge object
	 * @param obj is the other charge object
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Charge other = (Charge) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		return true;
	}	
	
}
