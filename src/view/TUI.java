package view;

import java.util.HashMap;
import java.util.Scanner;

import control.Ctrl;

public class TUI implements UI {

	private Ctrl controller;
	private Scanner scanner;

	public TUI(Ctrl controller) {
		this.controller = controller;
		scanner = new Scanner(System.in);
	}

	@Override
	public void run() {
		mainMenu();
	}

	public void mainMenu() {
		program:
			while(true) {
				// gets input
				String choice = getInput("You are in the main menu, please choose what you want to do.\n"
						+ "The available commands are:\n"
						+ "create - creates a new user, ID and password is auto generated.\n"
						+ "list - prints a list of all the current users.\n"
						+ "edit - lets you edit current users.\n"
						+ "delete - deletes a user by ID.\n"
						+ "exit - terminates the program.");
				choice = choice.toLowerCase();
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
	scanner.close();
	}

	private void createUser() {
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		String input;
		String password;

		// gets ID
		input = getValidID("Choose an ID between 11 and 99, or type cancel to go to main menu.", hashMap);

		// gets user name
		if(!input.equals("cancel")) {
			input = getName("Choose a username between 2 and 20 characters, or type cancel to go to main menu.", hashMap);
		}

		// gets initials
		if(!input.equals("cancel")) {
			input = getIni("Choose initials between 2 and 4 characters, or type cancel to go to main menu.", hashMap);
		}

		// gets social security number
		if(!input.equals("cancel")) {
			input = getCpr("Type your social security number as 10 digits, no \"-\", or type cancel to go to main menu.", hashMap);
		}

		// gets role
		if(!input.equals("cancel")) {
			input = getRole("Choose a role from Admin, Pharmacist, Foreman or Operator, or type cancel to go to main menu.", hashMap);
		}

		if(!input.equals("cancel")) {
			// execute in logic
			password = controller.createUser(hashMap);
			// prints ID and generated password
			System.out.println("Your ID is: " + hashMap.get("ID") + ", and your password is: " + password);
		}
	}

	private void listUsers() {
		System.out.println(controller.getUserList());
	}

	private void editUser() {
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		String input;
		// gets user to edit
		input = getExistingID("Please choose which user you want to edit by typing the ID, or type cancel to go to main menu.", hashMap);

		if(!input.equals("cancel")) {
			loop:
				while(true) {
					// gets input 
					String choice = getInput("Please choose what you want to edit.\n"
							+ "The available commands are:\n"
							+ "name - lets you change the user name.\n"
							+ "ini - lets you change the initials.\n"
							+ "password - lets you change the password.\n"
							+ "role - lets you change the role\n"
							+ "cancel - takes you to main menu.");
					choice = choice.toLowerCase();
					// divides the flow
					switch (choice) {
					case "name":
						getName("Choose a username between 2 and 20 characters, or type cancel to go to main menu.", hashMap);
						break loop;
					case "ini":
						getIni("Choose initials between 2 and 4 characters, or type cancel to go to main menu.", hashMap);
						break loop;
					case "cpr":
						getCpr("Type new social security number as 10 digits, no \"-\", or type cancel to go to main menu.", hashMap);
						break loop;
					case "password":
						changePassword("TODO");
						//TODO
						break loop;
					case "role":
						getRole("Choose a role from Admin, Pharmacist, Foreman or Operater, or type cancel to go to main menu.", hashMap);
						break loop;
					case "cancel":
						break loop;
					default:
						System.out.println("Invalid command.");
						break;
					}
				}
		controller.editUser(hashMap);
		}
	}

	private void delete() {
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		String input;
		// gets user to delete
		input = getExistingID("Please choose which user you want to delete by typing the ID, or type cancel to go to main menu.", hashMap);

		// confirmation
		do {
			input = getInput("Are you sure you want to delete user with ID: " + Integer.parseInt(input) + "?\n"
					+ "Type confirm or cancel.");
			if(!input.equals("confirm") || !input.equals("cancel")) {
				System.out.println("Invalid command.");
			}
		} while(!input.equals("confirm") || !input.equals("cancel"));
		// executes if confirmed
		if(input.equals("confirm")) {
			controller.deleteUser(hashMap);
		}
	}

	private String getInput(String message) {
		System.out.println(message);
		String input = scanner.nextLine();
		return input;
	}

	private boolean isInteger(String input) {
		try {
			Integer.parseInt(input);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private String getExistingID(String message, HashMap<String, Object> hashMap) {
		String input;
		do {
			input = getInput(message);
			hashMap.put("ID", input);
			if(isInteger(input)) {
				if (!controller.exists(hashMap)) {
					System.out.println("User doesn't exist!");
				}
			}
		} while (!controller.exists(hashMap) && !input.equals("cancel"));
		return input;
	}

	private String getValidID(String message, HashMap<String, Object> dataMap) {
		String input;
		int ID = 0;
		do {
			input = getInput(message);
			dataMap.put("ID", input);
			if(isInteger(input)) {
				ID = Integer.parseInt(input);
			}
		} while ((ID < 11 || ID > 99) && !input.equals("cancel"));
		return input;
	}

	private String getName(String message, HashMap<String, Object> dataMap) {
		String input;
		do {
			input = getInput(message);
			dataMap.put("userName", input);
		} while ((input.length() < 2 || input.length() > 20) && !input.equals("cancel"));
		return input;
	}

	private String getIni(String message, HashMap<String, Object> dataMap) {
		String input;
		do {
			input = getInput(message);
			dataMap.put("ini", input);
		} while ((input.length() < 2 || input.length() > 4) && !input.equals("cancel"));
		return input;
	}

	private String getCpr(String message, HashMap<String, Object> dataMap) {
		String input;
		do {
			input = getInput(message);
			dataMap.put("cpr", input);
		} while (input.length() != 10 && !input.equals("cancel"));
		//TODO validering af cpr-nummer?
		return input;
	}

	private String changePassword(String message) {
		//TODO password validering
		return "";
	}

	private String getRole(String message, HashMap<String, Object> dataMap) {
		String input;
		do {
			input = getInput(message);
			dataMap.put("role", input);
		} while ((!input.equals("Admin") && !input.equals("Pharmacist") && !input.equals("Foreman") && !input.equals("Operator"))
				&& !input.equals("cancel"));
		//TODO flere roller?
		return input;
	}
}
