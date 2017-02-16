package model;

import java.util.ArrayList;
import model.IDataStorage.DALException;

public class UserDAO {

	private IDataStorage storage;
	private ArrayList<UserDTO> users;

	public UserDAO(IDataStorage storage) {
		this.storage = storage;
	}

	public UserDTO getUser(int userId) throws DALException {
		for (UserDTO user : users) {
			if (user.getUserID() == userId) {
				return user;
			}
		}
		return null;
	}

	public ArrayList<UserDTO> getUserList() throws DALException {
		return users;
	}

	public void createUser(UserDTO user) throws DALException {
		users.add(user);
		storage.write(users);
	}

	public void updateUser(UserDTO user) throws DALException {
		UserDTO temp = user; //TODO Is this an unecessary temp?
		deleteUser(user.getUserID());
		createUser(temp);
		storage.write(users);
	}

	public void deleteUser(int userId) throws DALException {
		for (UserDTO user : users) {
			if (user.getUserID() == userId) {
				users.remove(user);
			}
		}
		storage.write(users);
	}

	public boolean userExists(int userId){
		for (UserDTO user : users) {
			if (user.getUserID() == userId) {
				return true;
			}
		}
		return false;
	}

	public void init() throws DALException {
		users = storage.read();
	}

}