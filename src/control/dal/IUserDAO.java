package control.dal;
import java.util.HashMap;
import java.util.List;

import model.dto.UserDTO;

public interface IUserDAO {
	UserDTO getUser(int userId) throws DALException;
	List<UserDTO> getUserList() throws DALException;
	String createUser(HashMap<String, Object> hashMap) throws DALException;
	boolean updateUser(UserDTO user) throws DALException;
	boolean deleteUser(int userId) throws DALException;
	
	public class DALException extends Exception {
		/**
		 * 
		 */
		private static final long serialVersionUID = 7355418246336739229L;

		public DALException(String msg, Throwable e) {
			super(msg,e);
		}

		public DALException(String msg) {
			super(msg);
		}

	}

}