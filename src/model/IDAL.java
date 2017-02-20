package model;

import java.util.ArrayList;

import model.IDataStorage.DALException;

public interface IDAL {

	UserDTO getUser(int userId) throws DALException;

	ArrayList<UserDTO> getUserList() throws DALException;
	
	boolean isUserListEmpty();

	void createUser(UserDTO user) throws DALException;

	void updateUser(UserDTO user) throws DALException;

	void deleteUser(int userId) throws DALException;

	boolean userExists(int userId);
	
	void init() throws DALException;
}
