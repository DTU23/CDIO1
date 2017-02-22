package model;

import java.io.IOException;
import java.util.ArrayList;

import model.storage.IDataStorage;

public class PersistentUserDAO implements IDAL{

	private IDataStorage storage;
	private DTOList<UserDTO> users;

	public PersistentUserDAO(IDataStorage storage) {
		this.storage = storage;
		users = new DTOList<>();
	}

	public UserDTO getUser(int userId) {
		for (UserDTO user : users) {
			if (user.getUserID() == userId) {
				return user;
			}
		}
		return null;
	}

	public ArrayList<UserDTO> getUserList() throws DALException {
		if (users == null) {
			throw new DALException("Userlist not instantiated");
		} else {
			return users;
		}
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
		} catch (ClassNotFoundException e){
			throw new DALException("ClassNotFoundException", e);
		} catch (IOException e){
			throw new DALException("IOException", e);
		}
	}
}