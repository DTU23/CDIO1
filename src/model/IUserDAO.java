package model;
import java.util.HashMap;
import java.util.List;

import model.DataPersistence.DALException;

public interface IUserDAO {
	
	UserDTO getUser(HashMap<String, Object> hashMap) throws DALException;
	
	List<UserDTO> getUserList() throws DALException;
	
	String createUser(HashMap<String, Object> hashMap) throws DALException;
	
	boolean updateUser(HashMap<String, Object> hashMap) throws DALException;
	
	boolean deleteUser(HashMap<String, Object> hashMap) throws DALException;
	
}