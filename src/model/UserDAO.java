package model;

import java.io.IOException;
import java.util.ArrayList;
import model.IDataStorage.DALException;

public class UserDAO {

	private IDataStorage storage;
	private ArrayList<UserDTO> users;

	public UserDAO(IDataStorage storage) {
		this.storage = storage;
		users = new DTOList<UserDTO>();
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
		storage.write(users);
	}

	public void updateUser(UserDTO user) throws DALException {
		UserDTO temp = user; //TODO Is this an unecessary temp?
		deleteUser(user.getUserID());
		createUser(temp);
		storage.write(users);
	}

	public void deleteUser(int userId) throws DALException {
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getUserID() == userId) {
				users.remove(users.get(i));
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

	public void init() throws DALException, IOException, ClassNotFoundException {
		users = storage.read();
	}

}