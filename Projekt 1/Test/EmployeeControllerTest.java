package controller;

import org.junit.Assert;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import view.EmployeeManageViewController;
import view.aui.EmployeeManageAUI;
import view.aui.OrdersAUI;

import java.util.Collection;

import model.Register;

import java.io.File;
import java.time.LocalDateTime;

import model.Employee;
import model.Food;
import model.Purchase;
import model.Transaction;
import controller.EmployeeController;
import model.Charge;

import static org.junit.Assert.*;

/**
 * Testet die Methoden {@link controller.EmployeeController}
 *
 * @author Elidona and Marcel
 */
public class EmployeeControllerTest {
    /**
     * passende Umgebung zum Testen.
     */

    class TestEmployeeManageViewController implements EmployeeManageAUI {

        boolean refreshEmployeesCalled = false;
        boolean EmployeeErrorCalled = false;
        boolean refreshSubscriptionsCalled = false;

        @Override
        public void refreshEmployees() {
            refreshEmployeesCalled = true;
        }

        @Override
        public void showEmployeeError(String msg) {
            EmployeeErrorCalled = true;
        }

        @Override
        public void refreshSubscriptions() {
            refreshSubscriptionsCalled = true;

        }

    }

    TestEmployeeManageViewController employeeManageViewController = new TestEmployeeManageViewController();
    MainController mainController = new MainController();
    Register register = new Register("Register", 0, 100);
    Employee employee = new Employee("", "", 0, false);
    EmployeeController employeeController = mainController.getEmployeeController();
    Employee employee1 = new Employee("Christian C.", "christian$", 10, false);
    Employee employee2 = new Employee("Daniel S.", "daniel&", 11, false);
    Employee employee3 = new Employee("Sara K.", "sara12", 12, false);
    Employee employee4 = new Employee("Lisa L.", "lisa1234$", 13, true);
    Employee employee7 = new Employee("Harald", "hallo$", 100, false);
    Employee employee8 = new Employee("Eli Sh.", "helloWord!", 16, false);
    Employee employee9 = new Employee("Eli Sh.", "irgendwass123", 299, false);
    Employee employee5 = new Employee("Eli S.", "eli$", 12, false);
    Employee employee6 = new Employee("Flo G.", "1234F", 15, false);
    Food f1 = new Food("Cola", 150, 25, "XY", 0, false);
    Food f2 = new Food("Fanta", 150, 25, "XY", 0, false);
    Purchase purchase1 = new Purchase(f1, false, LocalDateTime.now());
    Purchase purchase2 = new Purchase(f2, false, LocalDateTime.now());

    @Before
    public void setUp() throws Exception {
        employeeController.setEmployeeManageAUI(employeeManageViewController);
        mainController.setRegister(register);
        register.addEmployee(employee1);
        register.addEmployee(employee2);
        register.addEmployee(employee3);
        register.addEmployee(employee4);
        register.addEmployee(employee7);
        register.addEmployee(employee8);
        register.addEmployee(employee9);
    }

    /**
     * die Methode addEmployee in employeeController  wird getestet.
     */

    @Test
    public void testAddEmployee() {

        employeeController.addEmployee(employee5);
        employeeController.addEmployee(employee6);
        assertTrue(mainController.getRegister().getEmployees().contains(employee5));
        assertTrue(mainController.getRegister().getEmployees().contains(employee6));
    }

    @Test
    public void testAddEmployeeNull() {
        employeeController.addEmployee(null);
    }

    @Test
    public void testAddEmployeeShowError() {
        employeeController.addEmployee(employee1);
        assertTrue(employeeManageViewController.EmployeeErrorCalled);
    }

    @Test
    public void testDeleteEmployeeShowError() {
        Employee employee10 = new Employee("Marcel", "1234F", 15, false);
        employeeController.deleteEmployee(employee10);
        assertTrue(employeeManageViewController.EmployeeErrorCalled);
    }

    /**
     * deleteEmployee in employeeController
     */
    @Test
    public void testDeleteEmployee() {
        assertFalse(mainController.getRegister().getEmployees().isEmpty());
        assertFalse(mainController.getRegister().getEmployees().size() == 0);
        employeeController.deleteEmployee(employee2);
        assertTrue(mainController.getRegister().getEmployees().size() == 6);
        // der before test also das Setup  wird vor jedem test ausge�fhrt deswegen sind employee 5 und 6 nicht enthalten
        // im setup werden 7 employees hizugefuegt, deswegen muessen es nach dem loechen von einem noch 6 sein
    }

    @Test
    public void testDeleteEmployeeNull() {
        employeeController.deleteEmployee(null);
        assertTrue(mainController.getRegister().getEmployees().size() == 7);
    }

    /**
     * die Methode ChangePassword in employeeController
     */

    @Test
    public void testChangePassword() {
        register.setCurrentEmployee(employee1);
        employeeController.changePassword("christian$", "hallo1234", "hallo1234");
        //change the password wrong
        assertFalse(employeeController.changePassword("falscheEingabe", "hallo1234", "hallo1234"));
        assertFalse(employeeController.changePassword("hallo1234", "tennis", "falscheEingabe"));

        //change password correct and test
        assertTrue(employee1.checkPassword("hallo1234"));
        assertFalse(employee1.checkPassword("hallo123"));
        assertFalse(employee1.checkPassword("Hallo1234"));
        assertFalse(employee1.checkPassword("yksjdhbcyhdldhfljhdyfbjhydbfhvbdfhb"));
        assertFalse(employee1.checkPassword(""));
        assertFalse(employee1.checkPassword(null));

    }

    /**
     * checkEquals
     */
    @Test
    public void testCheckEquals(){
        Employee employee = new Employee("Heinz","12345",1,true);
        register.setCurrentEmployee(employee);
        assertFalse(employeeController.checkEquals("1111"));
    }

    /**
     * setPassword
     */
    @Test
    public void testSetPassword() {

        employeeController.setPassword(employee2, "idontknowwhat");
        assertTrue(employee2.checkPassword("idontknowwhat"));
        assertFalse(employee2.checkPassword("casdjncaksljdnasjnfvlkajnfvlkajsnvlkjadnflkvjnalfjvlajdfbvljabvljahbfv"));
        assertFalse(employee2.checkPassword("ich probiere mal:)"));
        assertFalse(employee2.checkPassword(""));
        assertFalse(employee2.checkPassword(null));
    }

    /**
     * setName -Methode in employeeContoller
     */
    @Test
    public void testSetName() {
        employeeController.setName(employee1, "Hanna Becker");
        Assert.assertEquals(employee1.getName(), "Hanna Becker");
        Assert.assertNotEquals(employee1.getName(), "Hanna Mueller");
        Assert.assertNotEquals(employee1.getName(), "");
    }

    /**
     * setPermissions in employeeController
     */

    @Test
    public void testSetPermissions() {
        employeeController.setPermissions(employee1, true);
        assertTrue(employee1.getManaging());
        assertFalse(!employee1.getManaging());
    }

    /**
     * Charge in employeeController
     */
    @Test
    public void testCharge() {
        employeeController.charge(employee7, 29);
        employeeController.charge(employee8, 23);
        employeeController.charge(employee1, 101);
        employeeController.charge(employee2, -10);

        Collection<Transaction> transactions = employee7.getTransactions();
        boolean contains = false;
        for (Transaction element : transactions) {
            if (!element.getIsPurchase()) {
                Charge testCharge = (Charge) element;
                if (testCharge.getValue() == 29 && testCharge.getDescription() == "Guthaben") {
                    contains = true;
                }
            }
        }
        assertTrue(contains);

    }

    /**
     * changeSubstcriptions in employeeController
     */


}