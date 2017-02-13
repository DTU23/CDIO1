package control;

import dal.IUserDAO;
import dto.UserDTO;

import java.lang.*;

import java.util.ArrayList;
import java.util.HashMap;

public class Ctrl implements IUserDAO {
    private DataPersistence jsonPersistence;
    private ArrayList<UserDTO> users;

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
    public String createUser(HashMap<String, String> hashMap){
        return "asdf";
    }

    @Override
    public boolean updateUser(UserDTO user){
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
        return true;
    }

    public boolean exists(int userId){
        for (int i = 0; i < this.users.size(); i++){
            if(this.users.get(i).getUserId() == userId){
                return true;
            }
        }
        return false;
    }

    private boolean save(){
        return jsonPersistence.write(this.users);
    }
}
