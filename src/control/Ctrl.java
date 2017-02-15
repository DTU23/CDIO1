package control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.IDataStorage;
import model.JSONStorage;
import model.UserDAO;
import model.UserDTO;

public class Ctrl {
    private final IDataStorage dataPersistence;
    private final ArrayList<UserDTO> users;

    public Ctrl(UserDAO dao){
        this.dataPersistence = new JSONStorage(System.getProperty("user.dir")+"/src/model/data.json");
        this.users = dataPersistence.read();
    }

    public UserDTO getUser(HashMap<String, Object> hashMap){
        for (int i = 0; i < this.users.size(); i++){
            if(this.users.get(i).getUserID() == Integer.parseInt(hashMap.get("ID").toString())){
                return this.users.get(i);
            }
        }
        return null;
    }

    public ArrayList<UserDTO> getUserList(){
        return this.users;
    }

    public String createUser(HashMap<String, Object> hashMap){
        try {
            UserDTO user = new UserDTO(hashMap);
            this.users.add(user);
            this.save();
            return user.getPassword();
        }catch (UserDTO.DTOException e){
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateUser(HashMap<String, Object> hashMap){
        users.remove(getUser(hashMap));
        try {
            users.add(new UserDTO(hashMap));
            return true;
        }catch (UserDTO.DTOException e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteUser(HashMap<String, Object> hashMap){
        for (int i = 0; i < this.users.size(); i++){
            if(this.users.get(i).getUserID() == Integer.parseInt(hashMap.get("ID").toString())){
                this.users.remove(i);
                this.save();
                return true;
            }
        }
        return false;
    }

    public boolean editUser(HashMap<String, Object> hashMap){
        UserDTO user = this.getUser(hashMap);
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
            return true;
        }
    }

    public boolean exists(HashMap<String, Object> hashMap){
        for (UserDTO user: users) {
            if(user.getUserID() == Integer.parseInt(hashMap.get("ID").toString())){
                return true;
            }
        }
        return false;
    }

    public String changePassword(){
        return generatePassword(10);
    }


    /**
     * Generates a valid password for current user
     * @param length
     * @return
     */
    private String generatePassword(int length) {
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

    private boolean validatePassword(String password, HashMap<String, Object> hashMap) {
        Pattern p = Pattern.compile("^(?=.*[A-Z].*[A-Z])(?!.*" + hashMap.get("userName").toString() + ")(?=.*[!@#$&*])(?=.*[0-9].*[0-9])(?=.*[a-z].*[a-z].*[a-z]).{8,}$");
        Matcher m = p.matcher(password);
        return m.matches();
    }

    private void save(){
        dataPersistence.write(this.users);
    }
}
