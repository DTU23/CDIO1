package view;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
		try {
			controller.initStorage();
		} catch (Exception e) {
			e.printStackTrace();
			//TODO håndtering?
		}
		mainMenu();
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
	scanner.close();
	}

	private void createUser() {
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		String input;
		String password;

		// gets ID
		input = getValidID("Choose an ID between 11 and 99, or type cancel to go to main menu.", hashMap);

		// gets user name
		if (!input.equals("cancel")) {
			input = getName("Choose a username between 2 and 20 characters, or type cancel to go to main menu.", hashMap);
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
			} catch (Exception e) {
				e.printStackTrace();
				//TODO håndtering?
			}
		}
	}

	private void listUsers() {
		if(controller.isUserListEmpty()) {
			System.out.println("There are no users in the system.");
		} else {
			System.out.println(controller.getUserList());
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
							getName("Choose a username between 2 and 20 characters, or type cancel to go to main menu.", hashMap);
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
				//TODO håndtering?
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
					//TODO håndtering?
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

	private boolean isPositiveInteger(String input) {
		try {
			long i = Long.parseLong(input);
			if (i >= 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

	private boolean isValidCpr(String input) {
		if (isPositiveInteger(input)) {
			int month = Integer.parseInt(input.substring(2, 4));
			// Checks if month is valid
			if (month > 0 && month < 13) {
				for (int i = 1900; i < 2100; i += 100) {
					int day = Integer.parseInt(input.substring(0, 2));
					int year = Integer.parseInt(i + input.substring(4, 6));
					// Creates a calendar object and sets year and month
					Calendar mycal = new GregorianCalendar(year, month, 1);
					// Get the number of days in that month
					int daysInMonth = mycal.getActualMaximum(Calendar.DAY_OF_MONTH);
					// Checks if day is valid
					if (day > 0 && day <= daysInMonth) {
						// check if full cpr is valid
						int CprProductSum = 0;
						int[] multiplyBy = {4, 3, 2, 7, 6, 5, 4, 3, 2, 1};
						for (int j = 0; j < input.length(); j++) {
							CprProductSum += Integer.parseInt(input.substring(j, j+1)) * multiplyBy[j];
						}
						if (CprProductSum % 11 == 0) {
							return true;
						} else {
							return false;
						}
					}
				}
				return false;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * Generates a random password with listed characters and symbols
	 * @param length how many characters should the password be
	 * @return String
	 */
	public String generatePassword(int length){
		String charactersCaps = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String characters = "abcdefghijklmnopqrstuvwxyz";
		String numbers = "0123456789";
		String symbols = "!@#$&*";
		String passwordCharacters = charactersCaps + characters + numbers + symbols;
		Random rnd = new Random();
		char[] password = new char[length];

		//do {
		for (int i = 0; i < length; i++) {
			password[i] = passwordCharacters.charAt(rnd.nextInt(passwordCharacters.length()));
		}
		//}while (!this.validatePassword(new String(password), hashMap));
		return new String(password);
	}

	/**
	 * Method can validate if a chosen password is allowed or not, based on the following requirements:
	 * minimum 2 symbols
	 * minimum 2 upper case characters
	 * minimum 2 lower case characters
	 * (username not present in password string)
	 * @param password
	 * @return
	 */
	private boolean isValidPassword(String password) {
		Pattern p = Pattern.compile("^(?=.*[A-Z].*[A-Z])(?!.*" +/* hashMap.get("userName").toString() +*/ ")(?=.*[!@#$&*])(?=.*[0-9].*[0-9])(?=.*[a-z].*[a-z].*[a-z]).{8,}$");
		Matcher m = p.matcher(password);
		return m.matches();
	}

	private String getExistingID(String message, HashMap<String, Object> dataMap) {
		String input;
		do {
			input = getInput(message);
			if (isPositiveInteger(input)) {
				dataMap.put("ID", Integer.parseInt(input));
				if (!controller.exists(dataMap)) {
					System.out.println("User doesn't exist!");
				}
			}
		} while (!controller.exists(dataMap) && !input.equals("cancel"));
		return input;
	}

	private String getValidID(String message, HashMap<String, Object> dataMap) {
		String input;
		int ID = 0;
		do {
			input = getInput(message);
			if (isPositiveInteger(input)) {
				ID = Integer.parseInt(input);
				dataMap.put("ID", ID);
				if (controller.exists(dataMap)) {
					System.out.println("That ID is taken.");
				}
			}
		} while ((ID < 11 || ID > 99 || controller.exists(dataMap)) && !input.equals("cancel"));
		return input;
	}

	//TODO kan/skal valideres
	private String getName(String message, HashMap<String, Object> dataMap) {
		String input;
		do {
			input = getInput(message);
			dataMap.put("userName", input);
		} while ((input.length() < 2 || input.length() > 20) && !input.equals("cancel"));
		return input;
	}

	//TODO kan hentes fra et valideret navn
	private String getIni(String message, HashMap<String, Object> dataMap) {
		String input;
		do {
			input = getInput(message);
			dataMap.put("ini", input.toUpperCase());
		} while ((input.length() < 2 || input.length() > 4) && !input.equals("cancel"));
		return input;
	}

	private String getCpr(String message, HashMap<String, Object> dataMap) {
		String input;
		do {
			input = getInput(message);
			dataMap.put("cpr", input);
		} while (!isValidCpr(input) && !input.equals("cancel"));
		return input;
	}

	private String getPassword(String message, HashMap<String, Object> dataMap) {
		String input;
		do {
			input = generatePassword(10);
			dataMap.put("password", input);
		} while(!isValidPassword(input) && !input.equals("cancel"));
		return input;
		//TODO besked m.m skal implementeres her hvis bruger skal vælge nyt kodeord
	}

	private String getRoles(String message, HashMap<String, Object> dataMap) {
		ArrayList<String> validRoles = new ArrayList<>();
		validRoles.add("admin");
		validRoles.add("pharmacist");
		validRoles.add("foreman");
		validRoles.add("operator");
		ArrayList<String> chosenRoles = new ArrayList<>();
		String input;
		do {
			// gets input
			input = getInput(message + "\nChosen roles: " + chosenRoles.toString()).toLowerCase();
			// if its a valid role
			if (validRoles.contains(input)) {
				// if its not already added
				if (!chosenRoles.contains(input)) {
					chosenRoles.add(input);
				} else {
					System.out.println("Role already chosen.");
				}
			}
			if(input.equals("done") && chosenRoles.isEmpty()) {
				continue;
			}
		} while (!input.equals("done") && !input.equals("cancel"));
		// executes if user didn't type cancel
		if (!input.equals("cancel")) {
			dataMap.put("roles", chosenRoles);
		}
		return input;
	}
}
