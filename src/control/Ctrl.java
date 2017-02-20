package control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.IDataStorage;
import model.UserDAO;
import model.UserDTO;

/**
 * This controller class handles communication with the data-layer.
 * It also has methods for generating and verifying passwords
 */
public class Ctrl {
    private UserDAO dao;
    
    public Ctrl(UserDAO dao_const){
        this.dao = dao_const;
    }

    /**
     * Returns user object from hashmap-key ID
     * @param hashMap user-details organized in a hashmap
     * @return User object
     * @throws IDataStorage.DALException exception from data-layer
     */
    public UserDTO getUser(HashMap<String, Object> hashMap)throws IDataStorage.DALException{
        return this.dao.getUser(Integer.parseInt(hashMap.get("ID").toString()));
    }

    /**
     * Gets all users from DAO
     * @return array list of user objects
     */
    public ArrayList<UserDTO> getUserList(){
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
    public String createUser(HashMap<String, Object> hashMap)throws UserDTO.DTOException, IDataStorage.DALException{
        this.dao.createUser(new UserDTO(hashMap));
        return this.dao.getUser(Integer.parseInt(hashMap.get("ID").toString())).getPassword();
    }

    /**
     * Deletes a user from the data persistence
     * @param hashMap user-details organized in a hashmap
     * @throws IDataStorage.DALException exception from data-layer
     */
    public void deleteUser(HashMap<String, Object> hashMap) throws IDataStorage.DALException {
        this.dao.deleteUser(Integer.parseInt(hashMap.get("ID").toString()));
    }

    /**
     * Edits a user. Takes ID for user being edited and a hashmap for which key to update.
     * @param hashMap user-details organized in a hashmap
     * @throws IDataStorage.DALException exception raised at data-layer
     */
    public void editUser(HashMap<String, Object> hashMap) throws IDataStorage.DALException{
        UserDTO user = this.dao.getUser(Integer.parseInt(hashMap.get("ID").toString()));
        if(hashMap.containsKey("cpr")){
            user.setCpr(hashMap.get("cpr").toString());
        }
        if(hashMap.containsKey("ini")){
            user.setIni(hashMap.get("ini").toString());
        }
        if(hashMap.containsKey("password")) {
            user.setPassword(hashMap.get("password").toString());
        }
        if(hashMap.containsKey("userName")){
            user.setUserName(hashMap.get("userName").toString());
        }
        if (hashMap.containsKey("password")){
            user.setPassword(hashMap.get("password").toString());
        }
        if(hashMap.containsKey("roles")){
            user.setRoles(new ArrayList<String>());
            for (String role: (ArrayList<String>)hashMap.get("roles")) {
                user.addRole(role);
            }
        }
        this.dao.updateUser(user);
    }

    /**
     * Checks if user exists
     * @param hashMap user-details organized in a hashmap
     * @return boolean: true if user exists, false if not.
     */
    public boolean exists(HashMap<String, Object> hashMap){
        return this.dao.userExists(Integer.parseInt(hashMap.get("ID").toString()));
    }

    /**
     * Method can validate if a chosen password is allowed or not, based on the following requirements:
     * minimum 2 symbols
     * minimum 2 uppcase characters
     * minimum 2 lower case characters
     * username not present in password string
     * @param password password string to be validated
     * @param hashMap user-details organized in a hashmap
     * @return true if password is allowed, false if not.
     */
    private boolean validatePassword(String password, HashMap<String, Object> hashMap){
        Pattern p = Pattern.compile("^(?=.*[A-Z].*[A-Z])(?!.*" + hashMap.get("userName").toString() + ")(?=.*[!@#$&*])(?=.*[0-9].*[0-9])(?=.*[a-z].*[a-z].*[a-z]).{8,}$");
        Matcher m = p.matcher(password);
        return m.matches();
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
