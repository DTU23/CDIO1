package integration_test;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import control.Ctrl;
import model.IDAL;
import model.PersistentUserDAO;
import model.Validation;
import model.storage.FileStorage;
import model.storage.IDataStorage;
import view.TUI;

public class UserSystemTest {

	private Ctrl controller;

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

	/**
	 * Tests if the object gets created by calling the createUser method in the controller.
	 */
	@Test
	public void createUserTest() {
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		String ID = "99";
		String userName = "Peter Madsen";
		String initials = "PM";
		String cpr = "1402011234";
		String password;
		ArrayList<String> roles = new ArrayList<>(); roles.add("operator"); roles.add("admin");
		String output = null;
		//clean up
		hashMap.put("ID", 99);
		try {
			controller.deleteUser(hashMap);
		} catch (Exception e) {}
		// validation of input
		if(Validation.isValidID(ID)) {
			hashMap.put("ID", Integer.parseInt(ID));
		}
		if(Validation.isValidUserName(userName)) {
			hashMap.put("userName", userName);
		}
		if(Validation.isValidInitials(initials)) {
			hashMap.put("ini", initials);
		}
		if(Validation.isValidCpr(cpr)) {
			hashMap.put("cpr", cpr);
		}
		do {
			password = TUI.generatePassword(10);
		} while(!Validation.isValidPassword(password));
		hashMap.put("password", password);
		if(Validation.isValidRole(roles.get(0)) && Validation.isValidRole(roles.get(1))) {
			hashMap.put("roles", roles);
		}
		try {
			controller.createUser(hashMap);
			output = controller.getUserList().toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(output, output.contains("userID = 99, password = " + password + ", userName = Peter Madsen, ini = PM, cpr = 1402011234, roles = [operator, admin]"));
	}

	/**
	 * Tests if the previously created user is still in system after relaunching the system.
	 */
	@Test
	public void persistentDataTest() {
		String output = null;
		try {
			output = controller.getUserList().toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(output, output.contains("userID = 99, password = ") && output.contains(", userName = Peter Madsen, ini = PM, cpr = 1402011234, roles = [operator, admin]"));
	}

	/**
	 * Tests if the user name can be edited.
	 */
	@Test
	public void editUserNameTest() {
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		String ID = "99";
		String userName = "Peter Jensen";
		String output = null;
		hashMap.put("ID", Integer.parseInt(ID));
		if(controller.exists(hashMap)) {
			if(Validation.isValidUserName(userName)) {
				hashMap.put("userName", userName);
			}
		}
		try {
			controller.editUser(hashMap);
			output = controller.getUserList().toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(output, output.contains("userID = 99, password = ") && output.contains(", userName = Peter Jensen, ini = PM, cpr = 1402011234, roles = [operator, admin]"));
	}

	/**
	 * Tests if the roles can be edited.
	 */
	@Test
	public void editRolesTest() {
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		String ID = "99";
		ArrayList<String> roles = new ArrayList<>(); roles.add("pharmacist"); roles.add("foreman");
		String output = null;
		hashMap.put("ID", Integer.parseInt(ID));
		if(controller.exists(hashMap)) {
			if(Validation.isValidRole(roles.get(0)) && Validation.isValidRole(roles.get(1))) {
				hashMap.put("roles", roles);
			}
		}
		try {
			controller.editUser(hashMap);
			output = controller.getUserList().toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue(output, output.contains("userID = 99, password = ") && output.contains(", userName = Peter Jensen, ini = PM, cpr = 1402011234, roles = [pharmacist, foreman]"));
	}

	/**
	 * Tests if the user can be deleted from the system.
	 */
	@Test
	public void deleteUserTest() {
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		String ID = "99";
		String output = null;
		hashMap.put("ID", Integer.parseInt(ID));
		// deletes user
		try {
			controller.deleteUser(hashMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// check if its in the user list
		try {
			output = controller.getUserList().toString();
		} catch (Exception e) {}
		assertTrue(output, !output.contains("userID = 99, password = ") && output.contains(", userName = Peter Jensen, ini = PM, cpr = 1402011234, roles = [pharmacist, foreman]")
				&& !output.equals(null));
	}
}
