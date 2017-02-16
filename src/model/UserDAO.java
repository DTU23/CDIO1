package model;

import java.util.ArrayList;
import model.IDataStorage.DALException;

public class UserDAO {

	private IDataStorage storage;
	private ArrayList<UserDTO> users;

	public UserDAO(IDataStorage storage) throws DALException  {
		this.storage = storage;
		users = this.storage.read();
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
	}

	public void updateUser(UserDTO user) throws DALException {
		UserDTO temp = user; //TODO Is this an unecessary temp?
		deleteUser(user.getUserID());
		createUser(temp);
	}

	public void deleteUser(int userId) throws DALException {
		for (UserDTO user : users) {
			if (user.getUserID() == userId) {
				users.remove(user);
			}
		}
	}

	public boolean userExists(int userId){
		for (UserDTO user : users) {
			if (user.getUserID() == userId) {
				return true;
			}
		}
		return false;
	}

}

