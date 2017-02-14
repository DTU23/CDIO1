package model;

import java.util.HashMap;
import java.util.List;

import model.IDataStorage.DALException;

public class UserDAO {

	private IDataStorage storage;
	private List<UserDTO> users;

	public UserDAO(IDataStorage storage) {
		this.storage = storage;
		this.storage.read();
	}

	public UserDTO getUser(int userId) throws DALException {
		for (UserDTO user : users) {
			if (user.getUserId() == userId) {
				return user;
			}
		}
		return null;
	}

	public List<UserDTO> getUserList() throws DALException {
		return users;
	}

	public void createUser(UserDTO user) throws DALException {
		users.add(user);
	}

	public void updateUser(UserDTO user) throws DALException {
		UserDTO temp = user;
		deleteUser(user.getUserId());
		createUser(temp);
	}

	public void deleteUser(int userId) throws DALException {
		for (UserDTO user : users) {
			if (user.getUserId() == userId) {
				users.remove(user);
			}
		}
	}

	public boolean userExists(int userId) throws DALException {
		for (UserDTO user : users) {
			if (user.getUserId() == userId) {
				return true;
			}
		}
		return false;
	}

}

