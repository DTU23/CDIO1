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
		try {
			storage.write(users);
		}catch (IOException e){
			throw new DALException("IOException", e);
		}
	}

	public void updateUser(UserDTO user) throws DALException {
		deleteUser(user.getUserID());
		createUser(user);
		try {
			storage.write(users);
		}catch (IOException e){
			throw new DALException("IOException", e);
		}
	}

	public void deleteUser(int userId) throws DALException {
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getUserID() == userId) {
				users.remove(users.get(i));
			}
		}
		try {
			storage.write(users);
		}catch (IOException e){
			throw new DALException("IOException", e);
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

	public void init() throws DALException {
		try {
			users = storage.read();
		}catch (ClassNotFoundException e){
			throw new DALException("ClassNotFoundException", e);
		}catch (IOException e){
			throw new DALException("IOEcetion", e);
		}
	}

}