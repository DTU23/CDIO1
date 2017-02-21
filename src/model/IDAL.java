package model;

import java.util.ArrayList;

public interface IDAL {

	UserDTO getUser(int userId) throws DALException;

	ArrayList<UserDTO> getUserList() throws DALException;
	
	boolean isUserListEmpty();

	void createUser(UserDTO user) throws DALException;

	void updateUser(UserDTO user) throws DALException;

	void deleteUser(int userId) throws DALException;

	boolean userExists(int userId);
	
	void init() throws DALException;
    
	class DALException extends Exception {
  		
  		private static final long serialVersionUID = 7355418246336739229L;

  		public DALException(String msg, Throwable e) {
  			super(msg,e);
  		}

  		public DALException(String msg) {
  			super(msg);
  		}
  	}
}
