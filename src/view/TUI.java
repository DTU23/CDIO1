package view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

import control.Ctrl;
import model.IDAL.DALException;
import model.UserDTO.DTOException;
import model.Validation;

public class TUI implements UI {

	private Ctrl controller;
	private Scanner scanner;

	public TUI(Ctrl controller) {
		this.controller = controller;
		scanner = new Scanner(System.in);
	}

	@Override
	public void run() {
		try {
			controller.initStorage();
		} catch (DALException e) {
			e.printStackTrace();
			System.out.println("Something went wrong when trying to load users from storage.");
		}
		mainMenu();
		scanner.close();
	}

	public void mainMenu() {
		program:
			while (true) {
				// gets input
				String choice = getInput("You are in the main menu, please choose what you want to do.\n"
						+ "The available commands are:\n"
						+ "create - creates a new user, ID and password is auto generated.\n"
						+ "list - prints a list of all the current users.\n"
						+ "edit - lets you edit current users.\n"
						+ "delete - deletes a user by ID.\n"
						+ "exit - terminates the program.").toLowerCase();
				// divides the flow
				switch (choice) {
				case "create":
					createUser();
					break;
				case "list":
					listUsers();
					break;
				case "edit":
					editUser();
					break;
				case "delete":
					delete();
					break;
				case "exit":
					break program;
				default:
					System.out.println("Invalid command.");
					break;
				}
			}
	}

	private void createUser() {
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		String input;
		String password;

		// gets ID
		input = getID("Choose an ID between 11 and 99, or type cancel to go to main menu.", hashMap);

		// gets user name
		if (!input.equals("cancel")) {
			input = getUserName("Choose a username between 2 and 20 characters, or type cancel to go to main menu.", hashMap);
		}

		// gets initials
		if (!input.equals("cancel")) {
			input = getIni("Choose initials between 2 and 4 characters, or type cancel to go to main menu.", hashMap);
		}

		// gets social security number
		if (!input.equals("cancel")) {
			input = getCpr("Type your social security number as 10 digits, no \"-\", or type cancel to go to main menu.", hashMap);
		}

		// gets role
		if (!input.equals("cancel")) {
			input = getRoles("Choose roles from admin, pharmacist, foreman or operator, type done to finish adding roles " +
					"or type cancel to go to main menu. You must add at least one role.", hashMap);
		}

		if (!input.equals("cancel")) {
			password = getPassword("", hashMap);
			try {
				// execute in logic
				controller.createUser(hashMap);
				// prints ID and generated password
				System.out.println("Your ID is: " + hashMap.get("ID") + ", and your password is: " + password);
			} catch (DALException e) {
				e.printStackTrace();
				System.out.println("Something went wrong, user might have been saved in storage.");
			} catch (DTOException e) {
				e.printStackTrace();
				System.out.println("Something went wrong, user wasn't created.");
			}
		}
	}

	private void listUsers() {
		try {
			System.out.println(controller.getUserList());
		} catch (DALException e) {
			e.printStackTrace();
			System.out.println("There are no users in the system.");
		}
	}

	private void editUser() {
		if(!controller.isUserListEmpty()) {
			HashMap<String, Object> hashMap = new HashMap<String, Object>();
			String input;
			// gets user to edit
			input = getExistingID("Please choose which user you want to edit by typing the ID, or type cancel to go to main menu.", hashMap);

			if (!input.equals("cancel")) {
				loop:
					while (true) {
						// gets input
						String choice = getInput("Please choose what you want to edit.\n"
								+ "The available commands are:\n"
								+ "name - lets you change the user name.\n"
								+ "ini - lets you change the initials.\n"
								+ "password - gives you a new password.\n"
								+ "roles - lets you change the roles\n"
								+ "cancel - takes you to main menu.").toLowerCase();
						// divides the flow
						switch (choice) {
						case "name":
							getUserName("Choose a username between 2 and 20 characters, or type cancel to go to main menu.", hashMap);
							break loop;
						case "ini":
							getIni("Choose initials between 2 and 4 characters, or type cancel to go to main menu.", hashMap);
							break loop;
						case "cpr":
							getCpr("Type new social security number as 10 digits, no \"-\", or type cancel to go to main menu.", hashMap);
							break loop;
						case "password":
							getPassword("", hashMap);
							//TODO der skal laves en besked her hvis bruger skal vælge nyt kodeord
							break loop;
						case "role":
							// TODO evt. to forskellige flows her, en der tilføjer og en der fjerner
							getRoles("Choose roles from admin, pharmacist, foreman or operator, type done to finish adding roles " +
									"or type cancel to go to main menu. You must add at least one role.", hashMap);
							break loop;
						case "cancel":
							break loop;
						default:
							System.out.println("Invalid command.");
							break;
						}
					}
			try {
				controller.editUser(hashMap);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Something went wrong, changes might have been saved in storage.");
			}
			}
		} else {
			System.out.println("The user list is empty.");
		}
	}

	private void delete() {
		if(!controller.isUserListEmpty()) {
			HashMap<String, Object> hashMap = new HashMap<String, Object>();
			String input;
			int ID;
			// gets user to delete
			input = getExistingID("Please choose which user you want to delete by typing the ID, or type cancel to go to main menu.", hashMap);

			// confirmation
			if(!input.equals("cancel")) {
				ID = Integer.parseInt(input);
				do {
					input = getInput("Are you sure you want to delete user with ID: " + ID + "?\n"
							+ "Type confirm or cancel.");
					if (!input.equals("confirm") && !input.equals("cancel")) {
						System.out.println("Invalid command.");
					}
				} while (!input.equals("confirm") && !input.equals("cancel"));
			}
			// executes if confirmed
			if (input.equals("confirm")) {
				try {
					controller.deleteUser(hashMap);
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("Something went wrong, user might have been deleted from storage.");
				}
			}
		} else {
			System.out.println("The user list is empty.");
		}
	}

	private String getInput(String message) {
		System.out.println(message);
		String input = scanner.nextLine();
		return input;
	}

	/**
	 * Generates a random password with listed characters and symbols
	 * @param length how many characters should the password be
	 * @return String
	 */
	public static String generatePassword(int length){
		String charactersCaps = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String characters = "abcdefghijklmnopqrstuvwxyz";
		String numbers = "0123456789";
		String symbols = "!@#$&*";
		String passwordCharacters = charactersCaps + characters + numbers + symbols;
		Random rnd = new Random();
		char[] password = new char[length];

		for (int i = 0; i < length; i++) {
			password[i] = passwordCharacters.charAt(rnd.nextInt(passwordCharacters.length()));
		}
		
		return new String(password);
	}

	private String getExistingID(String message, HashMap<String, Object> dataMap) {
		String input;
		do {
			input = getInput(message);
			if (Validation.isValidID(input)) {
				dataMap.put("ID", Integer.parseInt(input));
				if (!controller.exists(dataMap)) {
					System.out.println("User doesn't exist!");
				}
			}
		} while (!controller.exists(dataMap) && !input.equals("cancel"));
		return input;
	}

	private String getID(String message, HashMap<String, Object> dataMap) {
		String input;
		do {
			input = getInput(message);
			if (Validation.isValidID(input)) {
				dataMap.put("ID", Integer.parseInt(input));
				if (controller.exists(dataMap)) {
					System.out.println("That ID is taken.");
				}
			}
		} while ((!Validation.isValidID(input) || controller.exists(dataMap)) && !input.equals("cancel"));
		return input;
	}

	private String getUserName(String message, HashMap<String, Object> dataMap) {
		String input;
		do {
			input = getInput(message);
		} while (!Validation.isValidUserName(input) && !input.equals("cancel"));
		dataMap.put("userName", input);
		return input;
	}

	//TODO kan hentes fra et valideret navn
	private String getIni(String message, HashMap<String, Object> dataMap) {
		String input;
		do {
			input = getInput(message);
		} while (!Validation.isValidInitials(input) && !input.equals("cancel"));
		dataMap.put("ini", input.toUpperCase());
		return input;
	}

	private String getCpr(String message, HashMap<String, Object> dataMap) {
		String input;
		do {
			input = getInput(message);
		} while (!Validation.isValidCpr(input) && !input.equals("cancel"));
		dataMap.put("cpr", input);
		return input;
	}

	private String getPassword(String message, HashMap<String, Object> dataMap) {
		String input;
		do {
			input = generatePassword(10);
		} while(!Validation.isValidPassword(input) && !input.equals("cancel"));
		dataMap.put("password", input);
		return input;
	}

	private String getRoles(String message, HashMap<String, Object> dataMap) {
		ArrayList<String> chosenRoles = new ArrayList<>();
		String input;
		do {
			// gets input
			input = getInput(message + "\nChosen roles: " + chosenRoles.toString()).toLowerCase();
			// if its a valid role
			if (Validation.isValidRole(input)) {
				// if its not already added
				if (!chosenRoles.contains(input)) {
					chosenRoles.add(input);
				} else {
					System.out.println("Role already chosen.");
				}
			}
		} while ((!input.equals("done") && !input.equals("cancel")) || (input.equals("done") && chosenRoles.isEmpty()));
		// executes if user didn't type cancel
		if (input.equals("done")) {
			dataMap.put("roles", chosenRoles);
		}
		return input;
	}
}
