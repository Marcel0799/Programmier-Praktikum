package model;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

/**
 * Represents the Register
 * @author Marc and a little bit from Marcel
 */
public class Register implements Serializable {

	private String registerName;
	private int debtLimit;
	private int balanceLimit;
	private Employee currentEmployee;

	private final Collection<Employee> employees;
	private final Collection<Food> foods;
	private final Collection<Food> empties;
	private final LinkedList<Purchase> orders;
	private final Collection<Food> stock;

	public Register(String registerName, int debtLimit, int balanceLimit) {
		this.registerName = registerName;
		this.debtLimit = debtLimit;
		this.balanceLimit = balanceLimit;

		employees = new ArrayList<>();
		foods = new ArrayList<>();
		empties = new LinkedList<>();
		orders = new LinkedList<>();
		stock = new LinkedList<>();
	}

	/**
	 * Add food to foods list.
	 * @param food element to be appended to the foods list.
	 */
	public void addFood(Food food) {
		boolean isExisting = false;
		for (Food f : foods) {
			if (f.equals(food)) {
				isExisting = true;
			}
		}
		if(!isExisting) {
			foods.add(food);
		}
	}

	/**
	 * Add order to orders list.
	 * @param order
	 */
	public void addOrder(Purchase order) {
		orders.add(order);
	}
	
	/**
	 * Removes food from the foods list.
	 * @param food element to be removed from the foods list.
	 * @throws IllegalArgumentException if the food that should be removed is still in Stock
	 */
	public void deleteFood(Food food) {
		boolean success = foods.remove(food);
		if(!success) {
			throw new IllegalStateException();
		}
	}

	/**
	 * Add food to stock list.
	 * @param food element to be appended to the stock list.
	 */
	public void addFoodToStock(Food food) {
		boolean added = stock.add(food);
		if(!added) {
			throw new IllegalStateException();
		}
	}
	
    /**
	 * Clears the empties list.
	 */
	public void clearEmpties() {
		empties.clear();
	}

	/**
	 * Add food to employees transaction list.
	 * @param food element to be appended to the employees transaction list.
	 * @throws IllegalStateException if the food is not in the stock list
	 */
	public void buyFood(Food food) {
		boolean removed = stock.remove(food);
		if(!removed) {
			throw new IllegalStateException();
		}

		Purchase purchase = new Purchase(food, false, LocalDateTime.now());
		currentEmployee.addTransaction(purchase);
	}

	/**
	 * Add food to empties list.
	 * @param purchase element to be appended to empties list.
	 * @throws IllegalArgumentException if purchase is null, returned is true or can not be refunded.
	 */
	public void refundEmpty(Purchase purchase) {
		if (purchase == null) {
			throw new IllegalArgumentException();
		}
		if (purchase.getReturned()) {
			throw new IllegalArgumentException();
		}
		Purchase refunded = currentEmployee.refundEmpty(purchase);
		if (refunded == null) {
			throw new IllegalArgumentException();
		}
		empties.add(purchase.getFood());
	}

	/**
	 * Add employee to employees list.
	 * @param employee element to be appended to employees list.
	 * @throws IllegalStateException if the employee can not be added to the employee list.
	 */
	public void addEmployee(Employee employee) {
		boolean success = employees.add(employee);
		if(!success) {
			throw new IllegalStateException();
		}
	}

	/**
	 * Removes employee from the employees list.
	 * @param employee element to be removes from the employees list.
	 * @throws IllegalArgumentException if the Value of the Employees History is negative.
	 */
	public void deleteEmployee(Employee employee) {
		boolean success = employees.remove(employee);
		if(!success) {
			throw new IllegalStateException();
		}
	}

	/**
	 * Returns the name of the register.
	 * @return name
	 */
	public String getName() {
		return registerName;
	}

	/**
	 * Returns the debt limit.
	 * @return debtLimit
	 */
	public int getDebtLimit() {
		return debtLimit;
	}

	/**
	 * Returns the balance limit.
	 * @return balanceList
	 */
	public int getBalanceLimit() {
		return balanceLimit;
	}

	/**
	 * Returns the employee who is logged in.
	 * @return currentEmployee.
	 */
	public Employee getCurrentEmployee() {
		return currentEmployee;
	}

	/**
	 * Sets the employee who is logged in.
	 * @param currentEmployee - eingeloggter Mitarbeiter
	 */
	public void setCurrentEmployee(Employee currentEmployee) {
		this.currentEmployee = currentEmployee;
	}

	/**
	 * Returns the employee list.
	 * @return employees
	 */
	public Collection<Employee> getEmployees() {
		return employees;
	}

	/**
	 * Returns the product line.
	 * @return foods
	 */
	public Collection<Food> getFoods() {
		return foods;
	}

	/**
	 * Returns the deposit list.
	 * @return empties
	 */
	public Collection<Food> getEmpties() {
		return empties;
	}

	/**
	 * Returns the order list.
	 * @return orders
	 */
	public LinkedList<Purchase> getOrders() {
		return orders;
	}

	/**
	 * Returns the purchasable food.
	 * @return stock
	 */
	public Collection<Food> getStock() {
		return stock;
	}

	/**
	 * compares this register to the second register
	 * @param obj is the second register
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {return true;}
		if (obj == null) {return false;}
		if (getClass() != obj.getClass()) {return false;}
		Register other = (Register) obj;
		if (balanceLimit != other.balanceLimit) {return false;}
		if (debtLimit != other.debtLimit) {return false;}
		if (employees == null) {
			if (other.employees != null) {return false;}
		} else if (!employees.equals(other.employees)) {return false;}
		if (empties == null) {
			if (other.empties != null) {return false;}
		} else if (!empties.equals(other.empties)) {return false;}
		if (foods == null) {
			if (other.foods != null) {return false;}
		} else if (!foods.equals(other.foods)) {return false;}
		if (orders == null) {
			if (other.orders != null) {return false;}
		} else if (!orders.equals(other.orders)) {return false;}
		if (stock == null) {
			if (other.stock != null) {return false;}
		} else if (!stock.equals(other.stock)) {return false;}
		return true;
	}
}
