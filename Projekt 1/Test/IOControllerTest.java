package controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import model.Charge;
import model.Employee;
import model.Food;
import model.Purchase;
import model.Transaction;

/**
 * @author Marcel Bienia
 */

public class IOControllerTest {
	
	private MainController mainCon = new MainController();
	private IOController ioc = new IOController(mainCon);


	
	@Test
	public void TestCreate() {
		// creating a register
		File testCreateFile = new File("src/IOTestFiles/IOTestRegisterCreate.txt");
		assertFalse(testCreateFile.exists());
		Employee testManagerStefan = new Employee("stefan", "passwort" , 1234 , true);
		
		try {
			assertTrue(ioc.create(testCreateFile, new Tuple<Integer,Integer>(0, 100),testManagerStefan, "testCreateRegister"));
		} catch (IOException e) {
			assertTrue(false); // creating has failed
			e.printStackTrace();
		}
		
		//testing if the register was created
		assertTrue(testCreateFile.exists());
		assertTrue(testCreateFile.length()>0);
		
		//deleting the Register so the register does not exist when the test is rerun
		try {
			ioc.delete(testCreateFile);
		} catch (IOException e) {
			assertTrue(false); // delete has failed
		}
		assertFalse(testCreateFile.exists());
	}
	
	
	@Test
	public void TestDelete() {
		Employee testManager = new Employee("stefan", "passwort" , 1234 , true);
	    String PATHDELETEKASSE = "src/IOTestFiles/IODeleteKasse.txt";
		File testDeleteFile = new File(PATHDELETEKASSE);
		
		assertFalse(testDeleteFile.exists());
		try {
			ioc.create(testDeleteFile, new Tuple<Integer,Integer>(0, 100),testManager, "testDeleteKasse");
		} catch (IOException e) {
			assertTrue(false); // Kasse konnte nicht erstellt werden
		}
		assertTrue(testDeleteFile.exists());
		assertTrue(testDeleteFile.length()>0);
		
		try {
			ioc.delete(testDeleteFile);
		} catch (IOException e) {
			assertTrue(false); // delete has failed
		}
		assertFalse(testDeleteFile.exists());
	}

	@Test(expected = IOException.class)
	public void TestDeleteWrong() throws IOException {
	    String PATHUNKNOWNKASSE = "src\\IOTestFiles\\UnKnownKasse.txt";
		File testDeleteUnKnownFile = new File(PATHUNKNOWNKASSE);
		
		ioc.delete(testDeleteUnKnownFile);
	}

	@Test
	public void TestLoad() {
		File testKasseFile = new File("src/IOTestFiles/IOTestKasse.txt");
		assertFalse(testKasseFile.exists());
		Employee testManagerStefan = new Employee("stefan", "passwort" , 1234 , true);
		
		try {
			ioc.create(testKasseFile, new Tuple<Integer,Integer>(0, 100),testManagerStefan, "IOTestKasse");
		} catch (IOException e) {
			assertTrue(false); // creating has failed
			e.printStackTrace();
		}
		
		assertTrue(testKasseFile.length()>0);
		
		try {
			ioc.load(testKasseFile);
		} catch (ClassNotFoundException e) {
			assertTrue(false);
		} catch (IOException e) {
			assertTrue(false);
		}
		assertEquals("IOTestKasse",mainCon.getRegister().getName());
		
		//deleting the Register so the register does not exist when the test is rerun
		try {
			ioc.delete(testKasseFile);
		} catch (IOException e) {
			assertTrue(false); // delete has failed
		}
		assertFalse(testKasseFile.exists());
	}
	
	@Test(expected = IOException.class)
	public void TestLoadWrong() throws IOException, ClassNotFoundException {
	    String PATHUNKNOWNKASSE = "src\\IOTestFiles\\UnKnownKasse.txt";
		File testDeleteUnKnownFile = new File(PATHUNKNOWNKASSE);
		
		ioc.load(testDeleteUnKnownFile);
	}
	
	@Test
	public void TestGetPath() {
		assertEquals(ioc.getPath(),"src/savedRegisters/");
	}
	
	
	@Test
	public void TestSave() {

		// erstellen einer neuen kasse
		Employee testManager = new Employee("stefan", "passwort" , 1234 , true);
		File testSaveFile = new File("src/savedRegisters/testSaveKasse.txt");
		
		assertFalse(testSaveFile.exists());
		try {
			ioc.create(testSaveFile, new Tuple<Integer,Integer>(0, 100),testManager, "testSaveKasse");
		} catch (IOException e) {
			assertTrue(false); // Kasse konnte nicht erstellt werden
		}
		assertTrue(testSaveFile.exists());
		assertTrue(testSaveFile.length()>0);
		
		// laden dieser kasse
		try {
			ioc.load(testSaveFile);
		} catch (ClassNotFoundException e) {
			assertTrue(false);
		} catch (IOException e) {
			assertTrue(false);
		}
		
		assertEquals("testSaveKasse",mainCon.getRegister().getName());
		
//		etwas an der Kasse aendern
		Food cola = new Food("cola", 120);
		mainCon.getRegister().addFoodToStock(cola);
		
//		diese kasse speichern
		try {
			ioc.save("testSaveKasse.txt");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		assertTrue(mainCon.getRegister().getStock().contains(cola));
		
//		irgendeine Kasse erstellen
		File testKasseFileTwo = new File("src/IOTestFiles/IOTestKasseTwo.txt");
		assertFalse(testKasseFileTwo.exists());
		Employee testManagerStefan = new Employee("stefan", "passwort" , 1234 , true);
		
		try {
			ioc.create(testKasseFileTwo, new Tuple<Integer,Integer>(0, 100),testManagerStefan, "testKasseZwei");
		} catch (IOException e) {
			assertTrue(false); // creating has failed
			e.printStackTrace();
		}
		
		assertTrue(testKasseFileTwo.length()>0);
		
//		irgendeine andere kasse laden
		try {
			ioc.load(testKasseFileTwo);
		} catch (ClassNotFoundException e) {
			assertTrue(false);
		} catch (IOException e) {
			assertTrue(false);
		}
		assertEquals("testKasseZwei",mainCon.getRegister().getName());
		
//		wieder die richtige kasse laden
		try {
			ioc.load(testSaveFile);
		} catch (ClassNotFoundException e) {
			assertTrue(false);
		} catch (IOException e) {
			assertTrue(false);
		}
		assertEquals("testSaveKasse",mainCon.getRegister().getName());
		
//		ueberpruefen ob die aenderungen gespeicher wurden
		assertTrue(mainCon.getRegister().getStock().contains(cola));
		
//		erstelle kasse wieder loeschen
		try {
			ioc.delete(testSaveFile);
		} catch (IOException e) {
			assertTrue(false); // delete has failed
		}
		assertFalse(testSaveFile.exists());
		
//		erstelle kasse wieder loeschen
		try {
			ioc.delete(testKasseFileTwo);
		} catch (IOException e) {
			assertTrue(false); // delete has failed
		}
		assertFalse(testKasseFileTwo.exists());
	}
	
	
	@Test 
	public void testSaveHistory() {
		Employee hans = new Employee("hans" , "passwort" , 123 , false);
	    String PATHSAVEHISTORY = "src/IOTestFiles/testSaveHistory.csv";
		File testSaveFile = new File(PATHSAVEHISTORY);
		assertTrue(testSaveFile.exists());
		
		Food cola = new Food("cola", 10);
		Food fanta = new Food("fanta", 30);
		Food sprite = new Food("sprite", 40);
		Food keks = new Food("keks", 50);
		
		Purchase p1 = new Purchase(cola,false,LocalDateTime.now());
		Purchase p3 = new Purchase(fanta,false,LocalDateTime.now());
		Purchase p4 = new Purchase(sprite,false,LocalDateTime.now());
		Purchase p5 = new Purchase(keks,true,LocalDateTime.now());
		Charge c2 = new Charge(20,"charge");
		Charge c6 = new Charge(60,"return");
		
		Collection<Transaction> transactions = hans.getTransactions();
		transactions.add(p1);
		transactions.add(c2);
		transactions.add(p3);
		transactions.add(p4);
		transactions.add(p5);
		transactions.add(c6);
		assertTrue(hans.getTransactions().contains(p5));
		
		try {
			ioc.saveHistory(testSaveFile, hans);
		} catch (Exception e) {
			assertTrue(false);
		}
		
		assertTrue(testSaveFile.length()>0);
	}
	
	@Test 
	public void testSaveHistoryWithGuthaben() {
		Employee hans = new Employee("hans" , "passwort" , 123 , false);
	    String PATHSAVEHISTORY = "src/IOTestFiles/testSaveHistoryWithGuthaben.csv";
		File testSaveFile = new File(PATHSAVEHISTORY);
		assertTrue(testSaveFile.exists());
		
		Food cola = new Food("cola", 10);
		Purchase p1 = new Purchase(cola,false,LocalDateTime.now());
		
		Charge guthabenAufladen = new Charge(20,"Guthaben");
		
		Collection<Transaction> transactions = hans.getTransactions();
		
		transactions.add(p1);
		transactions.add(guthabenAufladen);
		assertTrue(hans.getTransactions().contains(guthabenAufladen));
		
		try {
			ioc.saveHistory(testSaveFile, hans);
		} catch (Exception e) {
			assertTrue(false);
		}
		assertTrue(testSaveFile.length()>0);
	}
}