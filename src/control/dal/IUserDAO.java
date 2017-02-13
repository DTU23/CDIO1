package control.dal;
import java.util.HashMap;
import java.util.List;

import model.dto.UserDTO;

public interface IUserDAO {
	UserDTO getUser(HashMap<String, Object> hashMap) throws DALException;
	List<UserDTO> getUserList() throws DALException;
	String createUser(HashMap<String, Object> hashMap) throws DALException;
	boolean updateUser(HashMap<String, Object> hashMap) throws DALException;
	boolean deleteUser(HashMap<String, Object> hashMap) throws DALException;
	
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