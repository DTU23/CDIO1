package integration_test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import control.Ctrl;	

public class TUITestDriver {

	private Ctrl controller;

	public TUITestDriver(Ctrl controller) {
		this.controller = controller;
	}

	public String mainMenu(String... commands) {
		String output = null;
		program:
			while (true) {
				// gets input
				String choice = commands[0].toLowerCase();
				// divides the flow
				switch (choice) {
				case "create":
					output = createUser(commands);
					break;
				case "list":
					output = listUsers();
					break;
				case "edit":
					output = editUser(commands);
					break;
				case "delete":
					output = delete(commands);
					break;
				case "exit":
					break program;
				default:
					return "Invalid command.";
				}
			}
		return output;
	}

	private String createUser(String[] commands) {
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		String input;
		String password;

		// gets ID
		input = getValidID(commands[1], hashMap);

		// gets user name
		if (!input.equals("cancel")) {
			input = getName(commands[2], hashMap);
		}

		// gets initials
		if (!input.equals("cancel")) {
			input = getIni(commands[3], hashMap);
		}

		// gets social security number
		if (!input.equals("cancel")) {
			input = getCpr(commands[4], hashMap);
		}

		// gets role
		if (!input.equals("cancel")) {
			input = getRoles(commands, 5, hashMap);
		}

		if (!input.equals("cancel")) {
			password = getPassword("", hashMap);
			try {
				// execute in logic
				controller.createUser(hashMap);
				// prints ID and generated password
				return "Your ID is: " + hashMap.get("ID") + ", and your password is: " + password;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	private String listUsers() {
		if(controller.isUserListEmpty()) {
			return "There are no users in the system.";
		} else {
			return controller.getUserList().toString();
		}
	}

	private String editUser(String[] commands) {
		if(!controller.isUserListEmpty()) {
			HashMap<String, Object> hashMap = new HashMap<String, Object>();
			String input;
			// gets user to edit
			input = getExistingID(commands[1], hashMap);

			if (!input.equals("cancel")) {
				loop:
					while (true) {
						// gets input
						String choice = commands[2].toLowerCase();
						// divides the flow
						switch (choice) {
						case "name":
							getName(commands[3], hashMap);
							break loop;
						case "ini":
							getIni(commands[3], hashMap);
							break loop;
						case "cpr":
							getCpr(commands[3], hashMap);
							break loop;
						case "password":
							getPassword("", hashMap);
							//TODO der skal laves en besked her hvis bruger skal vælge nyt kodeord
							break loop;
						case "role":
							// TODO evt. to forskellige flows her, en der tilføjer og en der fjerner
							getRoles(commands, 3, hashMap);
							break loop;
						case "cancel":
							break loop;
						default:
							return "Invalid command.";
						}
					}
			try {
				controller.editUser(hashMap);
			} catch (Exception e) {
				e.printStackTrace();
			}
			}
		} else {
			return "The user list is empty.";
		}
		return null;
	}

	private String delete(String[] commands) {
		if(!controller.isUserListEmpty()) {
			HashMap<String, Object> hashMap = new HashMap<String, Object>();
			String input;
			int ID;
			// gets user to delete
			input = getExistingID(commands[1], hashMap);

			// confirmation
			if(!input.equals("cancel")) {
				ID = Integer.parseInt(input);
				do {
					input = getInput(commands[2]);
					if (!input.equals("confirm") && !input.equals("cancel")) {
						return "Invalid command.";
					}
				} while (!input.equals("confirm") && !input.equals("cancel"));
			}
			// executes if confirmed
			if (input.equals("confirm")) {
				try {
					controller.deleteUser(hashMap);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			return "The user list is empty.";
		}
		return null;
	}

	private String getInput(String message) {
		return message;
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

	private String getExistingID(String message, HashMap<String, Object> hashMap) {
		String input;
		do {
			input = getInput(message);
			if (isPositiveInteger(input)) {
				hashMap.put("ID", Integer.parseInt(input));
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
			if (isPositiveInteger(input)) {
				ID = Integer.parseInt(input);
				dataMap.put("ID", ID);
				if (controller.exists(dataMap)) {
					return "That ID is taken.";
				}
			}
		} while ((ID < 11 || ID > 99 || controller.exists(dataMap)) && !input.equals("cancel"));
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
//		do {
			input = generatePassword(10);
			dataMap.put("password", input);
//		} while(!isValidPassword(input) && !input.equals("cancel"));
		return input;
		//TODO besked m.m skal implementeres her hvis bruger skal vælge nyt kodeord
	}

	private String getRoles(String[] message, int fromIndex, HashMap<String, Object> dataMap) {
		ArrayList<String> validRoles = new ArrayList<>();
		validRoles.add("admin");
		validRoles.add("pharmacist");
		validRoles.add("foreman");
		validRoles.add("operator");
		ArrayList<String> chosenRoles = new ArrayList<>();
		String input;
		do {
			// gets input
			input = getInput(message[fromIndex].toLowerCase());
			// if its a valid role
			if (validRoles.contains(input)) {
				// if its not already added
				if (!chosenRoles.contains(input)) {
					chosenRoles.add(input);
				} else {
					return "Role already chosen.";
				}
			}
			fromIndex++;
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
