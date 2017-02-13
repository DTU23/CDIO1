package control;

import com.sun.xml.internal.fastinfoset.util.CharArray;
import dal.IUserDAO;
import dto.UserDTO;

import java.lang.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

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
        UserDTO user = new UserDTO();
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
                System.out.println(this.generatePswd(10));
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

    static char[] generatePswd(int len){
        System.out.println("Your Password ");
        String charsCaps="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String Chars="abcdefghijklmnopqrstuvwxyz";
        String nums="0123456789";
        String symbols="!@#$%^&*()_+-=.,/';:?><~*/-+";
        String passSymbols=charsCaps + Chars + nums +symbols;
        Random random = new Random();
        char[] password=new char[len];
        int index=0;
        for(int i=0; i<len;i++){
            password[i]=passSymbols.charAt(random.nextInt(passSymbols.length()));
        }
        return password;
    }
}
