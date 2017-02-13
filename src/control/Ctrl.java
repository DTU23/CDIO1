package control;

import java.util.ArrayList;
import java.util.HashMap;

import model.DataPersistence;
import model.IUserDAO;
import model.JSONPersistence;
import model.UserDTO;

public class Ctrl implements IUserDAO {
    private final DataPersistence dataPersistence;
    private final ArrayList<UserDTO> users;

    public Ctrl(){
        this.dataPersistence = new JSONPersistence(System.getProperty("user.dir")+"/src/model/data.json");
        this.users = dataPersistence.read();
    }

    @Override
    public UserDTO getUser(HashMap<String, Object> hashMap){
        for (int i = 0; i < this.users.size(); i++){
            if(this.users.get(i).getUserId() == Integer.parseInt(hashMap.get("ID").toString())){
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
        try {
            UserDTO user = new UserDTO(hashMap);
            dataPersistence.write(this.users);
            return user.getPassword();
        }catch (UserDTO.DTOException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
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

    @Override
    public boolean deleteUser(HashMap<String, Object> hashMap){
        for (int i = 0; i < this.users.size(); i++){
            if(this.users.get(i).getUserId() == Integer.parseInt(hashMap.get("ID").toString())){
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
            user.setCpr(hashMap.get("cpr").toString());
            user.setIni(hashMap.get("ini").toString());
            user.setPassword(hashMap.get("password").toString());
            user.setUserId(Integer.parseInt(hashMap.get("ID").toString()));
            return true;
        }
    }

    public boolean exists(HashMap<String, Object> hashMap){
        for (UserDTO user: users) {
            if(user.getUserId() == Integer.parseInt(hashMap.get("ID").toString())){
                return true;
            }
        }
        return false;
    }

    private void save(){
        dataPersistence.write(this.users);
    }
}
