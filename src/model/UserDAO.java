package model;

import java.util.ArrayList;

public class UserDAO implements IDAL{

	private DTOList<UserDTO> users;

	public UserDAO() {
		users = new DTOList<>();
	}

	public UserDTO getUser(int userId){
		for (UserDTO user : users) {
			if (user.getUserID() == userId) {
				return user;
			}
		}
		return null;
	}

	public ArrayList<UserDTO> getUserList() throws DALException {
		if(users == null){
			throw new DALException("Userlist not instantiated!");
		}else {
			return users;
		}
	}

	public boolean isUserListEmpty(){
		return users.isEmpty();
	}

	public void createUser(UserDTO user) throws DALException {
		if(users.add(user)){

		} else {
			throw new DALException("User couldn't be created");
		}
	}

	public void updateUser(UserDTO user) throws DALException {
		deleteUser(user.getUserID());
		createUser(user);
	}

	public void deleteUser(int userId) throws DALException {
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getUserID() == userId) {
				if(users.remove(users.get(i))) {

				} else {
					throw new DALException("User couldn't be removed");
				}
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
	
	public void init() throws DALException {

	}
}