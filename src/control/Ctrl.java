package control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.IDataStorage;
import model.UserDAO;
import model.UserDTO;

public class Ctrl {
    UserDAO dao;

    public Ctrl(UserDAO dao_const){
        this.dao = dao_const;
    }

    public UserDTO getUser(HashMap<String, Object> hashMap)throws IDataStorage.DALException{
        return this.dao.getUser(Integer.parseInt(hashMap.get("ID").toString()));
    }

    public ArrayList<UserDTO> getUserList(){
        return dao.getUserList();
    }

    public boolean isUserListEmpty(){
        return dao.isUserListEmpty();
    }

    public String createUser(HashMap<String, Object> hashMap)throws IDataStorage.DALException{
        try {
            hashMap.put("password", this.generatePassword(10));
            this.dao.createUser(new UserDTO(hashMap));
            return this.dao.getUser(Integer.parseInt(hashMap.get("ID").toString())).getPassword();
        }catch (UserDTO.DTOException e){
            e.printStackTrace();
        }
        return null;
    }

    public void updateUser(HashMap<String, Object> hashMap)throws IDataStorage.DALException{
        try{
            this.dao.updateUser(new UserDTO(hashMap));
        }catch (UserDTO.DTOException e){
            throw new IDataStorage.DALException(e.getMessage());
        }
    }

    public void deleteUser(HashMap<String, Object> hashMap)throws IDataStorage.DALException{
        this.dao.deleteUser(Integer.parseInt(hashMap.get("ID").toString()));
    }

    public boolean editUser(HashMap<String, Object> hashMap)throws IDataStorage.DALException{
        UserDTO user = this.dao.getUser(Integer.parseInt(hashMap.get("ID").toString()));
        if (user == null){
            return false;
        }else{
            if (hashMap.containsKey("cpr")){
                user.setCpr(hashMap.get("cpr").toString());
            }
            if (hashMap.containsKey("ini")){
                user.setIni(hashMap.get("ini").toString());
            }
            if (hashMap.containsKey("password")){
                user.setPassword(hashMap.get("password").toString());
            }
            if(hashMap.containsKey("ID")){
                user.setUserID(Integer.parseInt(hashMap.get("ID").toString()));
            }
            this.dao.updateUser(user);
            return true;
        }
    }

    public boolean exists(HashMap<String, Object> hashMap){
        return this.dao.userExists(Integer.parseInt(hashMap.get("ID").toString()));
    }

    public String changePassword(){
        return generatePassword(10);
    }


    /**
     * Generates a valid password for current user
     * @param length
     * @return
     */
    private String generatePassword(int length){
        String charactersCaps = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String characters = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";
        String symbols = "!@#$&*";
        String passwordCharacters = charactersCaps + characters + numbers + symbols;
        Random rnd = new Random();
        char[] password = new char[length];

        /*do {
            for (int i = 0; i < length; i++) {
                password[i] = passwordCharacters.charAt(rnd.nextInt(passwordCharacters.length()));
            }
        }while (!this.validatePassword(new String(password), hashMap));*/
        return new String(password);
    }

    private boolean validatePassword(String password, HashMap<String, Object> hashMap)throws IDataStorage.DALException {
        Pattern p = Pattern.compile("^(?=.*[A-Z].*[A-Z])(?!.*" + hashMap.get("userName").toString() + ")(?=.*[!@#$&*])(?=.*[0-9].*[0-9])(?=.*[a-z].*[a-z].*[a-z]).{8,}$");
        Matcher m = p.matcher(password);
        return m.matches();
    }

    public void initStorage() throws IDataStorage.DALException {
        dao.init();
    }
}
