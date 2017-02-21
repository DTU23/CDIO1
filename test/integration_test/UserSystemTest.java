package integration_test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;

import model.IDAL;
import model.PersistentUserDAO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import control.Ctrl;
import model.storage.FileStorage;
import model.storage.IDataStorage;
import model.UserDAO;
import model.Validation;

public class UserSystemTest {

	Ctrl controller;
	private String password;

	@Before
	public void setUp() throws Exception {
		IDataStorage storage = new FileStorage();
		IDAL dao = new PersistentUserDAO(storage);
		controller = new Ctrl(dao);
		controller.initStorage();
	}

	@After
	public void tearDown() throws Exception {
		controller = null;
	}

	@Test
	public void createUserTest() {
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		String ID = "99";
		String userName = "Peter Madsen";
		String initials = "PM";
		String cpr = "1402011234";
		String[] roles = {"operator", "admin"};
		//clean up
		try {
			hashMap.put("ID", 99);
			controller.deleteUser(hashMap);
		} catch (Exception e) {}
		// validation of input
		if(Validation.isValidID("99")) {
			hashMap.put("ID", 99);
		}
		if(Validation.isValidUserName("Peter Madsen")) {
			hashMap.put("userName", "Peter Madsen");
		}
		if(Validation.isValidID("99")) {
			hashMap.put("ID", 99);
		}
		if(Validation.isValidID("99")) {
			hashMap.put("ID", 99);
		}
		if(Validation.isValidID("99")) {
			hashMap.put("ID", 99);
		}
	}



	String systemOutput;
	// creation
	systemOutput = tui.mainMenu("create", "99", "Peter Madsen", "PM", "1402011234", "operator", "admin", "done");
	// finder og gemmer password
	String[] wordArray = systemOutput.split(" ");
	for(int i = 0; i < wordArray.length; i++) {
		if(wordArray[i].equals("password")) {
			password = wordArray[i+2];
		}
	}
	// check if the user is in the user list
	systemOutput = tui.mainMenu("list");
	assertTrue(systemOutput.contains("userID = 99, password = " + password + ", userName = Peter Madsen, ini = PM, cpr = 1402011234, roles = [operator, admin]"));
}

@Test
public void persistentDataTest() {
	String systemOutput;
	// check if the user is in the user list
	systemOutput = tui.mainMenu("list");
	assertTrue(systemOutput.contains("userID = 99, password = " + password + ", userName = Peter Madsen, ini = PM, cpr = 1402011234, roles = [operator, admin]"));
}

@Test
public void editUserNameTest() {
	fail("TODO");
}

@Test
public void editRolesTest() {
	fail("TODO");
}

@Test
public void deleteUserTest() {
	String systemOutput;
	// check if the user is in the user list
	systemOutput = tui.mainMenu("list");
	assertTrue(systemOutput.contains("userID = 99, password = " + password + ", userName = Peter Madsen, ini = PM, cpr = 1402011234, roles = [operator, admin]"));
	// deletes user
	systemOutput = tui.mainMenu("delete", "99", "confirm");
	// check if its in the user list
	systemOutput = tui.mainMenu("list");
	assertTrue(!systemOutput.contains("userID = 99, password = " + password + ", userName = Peter Madsen, ini = PM, cpr = 1402011234, roles = [operator, admin]"));
}
}
