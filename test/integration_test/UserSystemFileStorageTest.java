package integration_test;

import static org.junit.Assert.*;

import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import control.Ctrl;
import model.FileStorage;
import model.IDataStorage;
import model.UserDAO;
import view.TUI;
import view.UI;

public class UserSystemFileStorageTest {
	
	private Scanner scanner;
	private String password;
	private UI tui;

	@Before
	public void setUp() throws Exception {
		scanner = new Scanner(System.in);
		password = null;
		IDataStorage storage = new FileStorage();
		UserDAO dao = new UserDAO(storage);
		Ctrl controller = new Ctrl(dao);
        tui = new TUI(controller);
        tui.run();
	}

	@After
	public void tearDown() throws Exception {
		scanner = null;
		tui = null;
	}

	@Test
	public void createUserTest() {
		String systemOutput;
        // clean up
        nextCommand("delete");
        systemOutput = nextCommand("99");
        if (systemOutput.equals("User doesn't exist!")) {
			nextCommand("cancel");
		} else {
			nextCommand("confirm");
		}
        // creation
        nextCommand("create");
        nextCommand("99");
        nextCommand("Peter Madsen");
        nextCommand("PM");
        nextCommand("1402011234");
        nextCommand("operator");
        nextCommand("admin");
        systemOutput = nextCommand("end");
        // finder og gemmer password
        String[] wordArray = systemOutput.split(" ");
        for(int i = 0; i < wordArray.length; i++) {
        	if(wordArray[i].equals("password") && password == null) {
        		password = wordArray[i+2];
        	}
        }
        // check if the user is in the user list
        systemOutput = nextCommand("list");
        assertTrue(systemOutput.contains("userID = 99, password = " + password + ", userName = Peter Madsen, ini = PM, cpr = 1402011234, roles = [operator, admin]"));
	}
	
	@Test
	public void persistentDataTest() {
		String systemOutput;
		// check if the user is in the user list
        systemOutput = nextCommand("list");
        assertTrue(systemOutput.contains("userID = 99, password = " + password + ", userName = Peter Madsen, ini = PM, cpr = 1402011234, roles = [operator, admin]"));
	}
	
	@Test
	public void deleteUserTest() {
		String systemOutput;
		// check if the user is in the user list
        systemOutput = nextCommand("list");
        assertTrue(systemOutput.contains("userID = 99, password = " + password + ", userName = Peter Madsen, ini = PM, cpr = 1402011234, roles = [operator, admin]"));
        // deletes user
		nextCommand("delete");
        systemOutput = nextCommand("99");
        if (systemOutput.equals("User doesn't exist!")) {
			nextCommand("cancel");
		} else {
			nextCommand("confirm");
		}
        // check if its in the user list
        systemOutput = nextCommand("list");
        assertTrue(!systemOutput.contains("userID = 99, password = " + password + ", userName = Peter Madsen, ini = PM, cpr = 1402011234, roles = [operator, admin]"));
	}

	private String nextCommand(String command) {
		// prints command
		System.out.println(command);
		// collects output
		StringBuilder builder = new StringBuilder();
		while(scanner.hasNext()) {
			builder.append(scanner.nextLine() + " ");
		}
		builder.deleteCharAt(builder.length());
        return builder.toString();
	}
	
	@Test
	public void editUserNameTest() {
		fail("TODO");
	}
	
	@Test
	public void editRolesTest() {
		fail("TODO");
	}
}
