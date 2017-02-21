package model;

import java.util.ArrayList;

import model.storage.IDataStorage.DALException;

public class UserDAO implements IDAL{

	private DTOList<UserDTO> users;

	public UserDAO() {
		users = new DTOList<>();
	}

	public UserDTO getUser(int userId) throws DALException {
		for (UserDTO user : users) {
			if (user.getUserID() == userId) {
				return user;
			}
		}
		return null;
	}

	public ArrayList<UserDTO> getUserList() {
		return users;
	}

	public boolean isUserListEmpty(){
		return users.isEmpty();
	}

	public void createUser(UserDTO user) throws DALException {
		users.add(user);
	}

	public void updateUser(UserDTO user) throws DALException {
		deleteUser(user.getUserID());
		createUser(user);
	}

	public void deleteUser(int userId) throws DALException {
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getUserID() == userId) {
				users.remove(users.get(i));
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
	
	public void init() throws DALException {}
}