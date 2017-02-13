package control;

import dal.IUserDAO;
import dto.UserDTO;

import java.lang.*;
import java.util.HashMap;
import java.util.List;

import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Ctrl implements IUserDAO {
    @Override
    public UserDTO getUser(int userId) throws DALException {
        return null;
    }

    @Override
    public List<UserDTO> getUserList() throws DALException {
        return null;
    }

    @Override
    public String createUser(HashMap<String, String> hashMap) throws DALException {
        return "asdf";
    }

    @Override
    public boolean updateUser(UserDTO user) throws DALException {
        return true;
    }

    @Override
    public boolean deleteUser(int userId) throws DALException {
        return true;
    }

    public boolean exists(int userId) throws DALException{
        return false;
    }

    private void readFile(){

    }
}
