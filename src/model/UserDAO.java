package model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import model.IDataStorage.DALException;

public class UserDAO {

	private IDataStorage storage;
	private DTOList<UserDTO> users;

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

	public void createUser(UserDTO user) throws DALException, IOException {
		users.add(user);
		storage.write(users);
	}

	public void updateUser(UserDTO user) throws IOException, DALException {
		UserDTO temp = user; //TODO Is this an unecessary temp?
		deleteUser(user.getUserID());
		createUser(temp);
		storage.write(users);
	}

	public void deleteUser(int userId) throws IOException, DALException {
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

	public void init() throws DALException, IOException, ClassNotFoundException {
		users = storage.read();
	}

}