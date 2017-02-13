package control;

import control.dal.IUserDAO;
import model.dto.UserDTO;

import java.lang.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Ctrl implements IUserDAO {
    private final DataPersistence jsonPersistence;
    private final ArrayList<UserDTO> users;

    public Ctrl(){
        this.jsonPersistence = new JSONPersistence(System.getProperty("user.dir")+"/src/model/data.json");
        this.users = jsonPersistence.read();
    }

    @Override
    public UserDTO getUser(int userId){
        for (int i = 0; i < this.users.size(); i++){
            if(this.users.get(i).getUserId() == userId){
                return this.users.get(i);
            }
        }
        return null;
    }

    @Override
    public ArrayList<UserDTO> getUserList(){
        return this.users;
    }

    @Override
    public String createUser(HashMap<String, Object> hashMap){
        UserDTO user = new UserDTO();
        user.setUserId(Integer.parseInt(hashMap.get("ID").toString()));
        user.setIni(hashMap.get("ini").toString());
        user.setCpr(hashMap.get("cpr").toString());
        for (String role: (ArrayList<String>)hashMap.get("roles")) {
            user.addRole(role);
        }
        user.setUserName(hashMap.get("username").toString());
        return "asdf";
    }

    @Override
    public boolean updateUser(UserDTO user){
        users.remove(user.getUserId());
        users.add(user);
        return true;
    }

    @Override
    public boolean deleteUser(int userId){
        for (int i = 0; i < this.users.size(); i++){
            if(this.users.get(i).getUserId() == userId){
                this.users.remove(i);
                this.save();
                return true;
            }
        }
        return false;
    }

    public boolean editUser(HashMap<String, String> hashMap){
        UserDTO user = this.getUser(Integer.parseInt(hashMap.get("ID")));
        if (user == null){
            return false;
        }else{
            user.setCpr(hashMap.get("cpr"));
            user.setIni(hashMap.get("ini"));
            user.setPassword(hashMap.get("password"));
            user.setUserId(Integer.parseInt(hashMap.get("ID")));
            return true;
        }
    }

    public boolean exists(int userId){
        for (UserDTO user: users) {
            if(user.getUserId() == userId){
                return true;
            }
        }
        return false;
    }

    private void save(){
        jsonPersistence.write(this.users);
    }
}
