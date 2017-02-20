package control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import model.IDAL;
import model.IDataStorage;
import model.UserDTO;

/**
 * This controller class handles communication with the data-layer.
 * It also has methods for generating and verifying passwords
 */
public class Ctrl {
	private IDAL dao;

	public Ctrl(IDAL dao){
		this.dao = dao;
	}

	/**
	 * Returns user object from hashmap-key ID
	 * @param hashMap user-details organized in a hashmap
	 * @return User object
	 * @throws IDataStorage.DALException exception from data-layer
	 */
	public UserDTO getUser(HashMap<String, Object> hashMap)throws IDataStorage.DALException{
		return this.dao.getUser((int) hashMap.get("ID"));
	}

	/**
	 * Gets all users from DAO
	 * @return array list of user objects
	 */
	public ArrayList<UserDTO> getUserList() throws IDataStorage.DALException{
		return dao.getUserList();
	}

	/**
	 * Checks if any users exist
	 * @return true if user list is empty, false if not.
	 */
	public boolean isUserListEmpty(){
		return dao.isUserListEmpty();
	}

	/**
	 * Creates new user
	 * @param hashMap user-details organized in a hashmap
	 * @return randomly generated password for the new user
	 * @throws IDataStorage.DALException exception from data-layer
	 * @throws IDataStorage.DALException exception from userobject interactions
	 */
	public void createUser(HashMap<String, Object> hashMap) throws UserDTO.DTOException, IDataStorage.DALException{
		this.dao.createUser(new UserDTO(hashMap));
	}

	/**
	 * Deletes a user from the data persistence
	 * @param hashMap user-details organized in a hashmap
	 * @throws IDataStorage.DALException exception from data-layer
	 */
	public void deleteUser(HashMap<String, Object> hashMap) throws IDataStorage.DALException {
		this.dao.deleteUser((int) hashMap.get("ID"));
	}

	/**
	 * Edits a user. Takes ID for user being edited and a hashmap for which key to update.
	 * @param hashMap user-details organized in a hashmap
	 * @throws IDataStorage.DALException exception raised at data-layer
	 */
	public void editUser(HashMap<String, Object> hashMap) throws IDataStorage.DALException{
		UserDTO user = this.dao.getUser((int) hashMap.get("ID"));
		if(hashMap.containsKey("userName")){
			user.setUserName(hashMap.get("userName").toString());
		}
		if(hashMap.containsKey("ini")){
			user.setIni(hashMap.get("ini").toString());
		}
		if(hashMap.containsKey("cpr")){
			user.setCpr(hashMap.get("cpr").toString());
		}
		if(hashMap.containsKey("password")) {
			user.setPassword(hashMap.get("password").toString());
		}
		// TODO add exceptionhandling for the casting
		if(hashMap.containsKey("roles")){
			user.setRoles((ArrayList<String>) hashMap.get("roles"));
		}
		this.dao.updateUser(user);
	}

	/**
	 * Checks if user exists
	 * @param hashMap user-details organized in a hashmap
	 * @return boolean: true if user exists, false if not.
	 */
	public boolean exists(HashMap<String, Object> hashMap){
		return this.dao.userExists((int) hashMap.get("ID"));
	}

	/**
	 * Initializes datapersistence/DAO.
	 * @throws IDataStorage.DALException exception from data-layer
	 * @throws IOException exception from data-layer
	 * @throws ClassNotFoundException exception from data-layer
	 */
	public void initStorage() throws IDataStorage.DALException, IOException, ClassNotFoundException {
		this.dao.init();
	}
}
