package model;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Represents the Employee
 *
 * @author Marcel Bienia
 */
public class Employee implements Serializable, Comparable {

    private String name;

    private boolean managing;

    private int employeeID;

    private String hashPassword;

    private final Collection<Transaction> transactions;


    /**
     * constructor
     *
     * @param name       - name of the Employee
     * @param password   - password of the Employee
     * @param empleyeeID - ID of the Employee
     * @param managing   true, if the Employee is managing/ Administrator
     */
    public Employee(String name, String password, int empleyeeID, boolean managing) {
        this.name = name;
        this.hashPassword = hashPw(password);
        this.employeeID = empleyeeID;
        this.managing = managing;
        this.transactions = new ArrayList<>();
    }

    /**
     * turns (new) password into String with the password hashed
     * @param newPassword is the new password that gets hashed
     * @return String 
     */
    public String hashPw(String newPassword) {
        try {
            MessageDigest messaged = MessageDigest.getInstance("MD5");
            messaged.update(newPassword.getBytes());
            byte[] bites = messaged.digest();
            StringBuffer stringb = new StringBuffer();
            for (byte b1 : bites) {
                stringb.append(Integer.toHexString(b1 & 0xff).toString());
            }
            return stringb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * getter for name
     *
     * @return Name des Mitarbeiters
     */
    public String getName() {
        return this.name;
    }

    /**
     * setter for name
     *
     * @param name is the new name
     */
    public void setName(String name) {
        if (name != null) {
            this.name = name;
        } else {
            throw new IllegalArgumentException();
        }

    }

    /**
     * getter for managing
     *
     * @return managing
     */
    public boolean getManaging() {
        return this.managing;
    }

    /**
     * setter for managing
     *
     * @param newManaging is the new managing argument
     */
    public void setManaging(boolean newManaging) {

        this.managing = newManaging;
    }

    /**
     * getter for employeeID
     *
     * @return employeeID
     */
    public int getEmployeeID() {
        return this.employeeID;
    }

    /**
     * setter for employeeID
     *
     * @param newID sets the employeeID new
     */
    public void setEmployeeID(int newID) {
        this.employeeID = newID;
    }

    /**
     * setter for password
     *
     * @param newPW is the new Password
     */
    public void setPassword(String newPW) {
        if (newPW != null) {
            this.hashPassword = hashPw(newPW);
        } else {
            throw new IllegalArgumentException();
        }

    }

    /**
     * check if password is correct
     *
     * @param givenPw - altes Passwort
     * @return if given password is correct
     */
    public boolean checkPassword(String givenPw) {
        if (givenPw == null) {
            return false;
        }
        return hashPassword.equals(hashPw(givenPw));
    }

    /**
     * getter for Transactions
     *
     * @return all known transactions from this employee
     */
    public Collection<Transaction> getTransactions() {
        return this.transactions;
    }

    /**
     * Add transaction to transactions list.
     *
     * @param transaction element to be appended to transaction list.
     */
    public void addTransaction(Transaction transaction) {
        if (transaction != null) {
            this.transactions.add(transaction);
        } else {
            throw new IllegalArgumentException();
        }

    }

    /**
     * Add food to the empties list and sets the purchase as refunded
     *
     * @param purchase element to be appended to empties list.
     * @return Food
     */
    public Purchase refundEmpty(Purchase purchase) {
        // if their are no transactions, you can not return anything
        if (transactions.isEmpty()) {
            return null;
        }
        // check all transactions of the employee
        for (Transaction current : transactions) {
            //if they are a Purchase
            if (current.getIsPurchase()) {
                Purchase other = (Purchase) current;
                // and if the food that shall be returned equals one in the transaction list that hasent already been returned
                if (purchase.equals(other) && !other.getReturned()) {
                    // the Purchase gets returned
                    other.setReturned(true);
                    purchase.setReturned(true);
                    return purchase;
                }
            }
        }
        return null;
    }
    
    /**
     * change the hashCode that it works again
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + employeeID;
        return result;
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param obj the reference object with which to compare.
     * @return true if this object is the same as the obj argument; false otherwise.
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
        Employee other = (Employee) obj;
        if (employeeID != other.employeeID) {
            return false;
        }
        return true;
    }

    /**
     * change compareTo so that it can compare Employees to other Employees
     */
    @Override
    public int compareTo(Object object) {
        if (object == null)
            return 0;
        if (getClass() != object.getClass())
            return 0;
        Employee other = (Employee) object;
        return this.name.compareTo(other.getName());
    }
}
