package control;

import dto.UserDTO;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class JSONPersistence implements DataPersistence {
    private String filePath;
    public JSONPersistence(String filePath){
        this.filePath = filePath;
    }
    @Override
    public boolean write(ArrayList<UserDTO> users) {
        JSONObject obj = new JSONObject();
        for (int i = 0; i < users.size(); i++){
            obj.put("userId", users.get(i).getUserId());
            obj.put("userName", users.get(i).getUserName());
            obj.put("ini", users.get(i).getIni());
            obj.put("cpr", users.get(i).getCpr());
            obj.put("password", users.get(i).getPassword());
        }

        JSONArray roles = new JSONArray();
        roles.add("admin");
        obj.put("roles", roles);

        // try-with-resources statement based on post comment below :)
        try (FileWriter file = new FileWriter(this.filePath)) {
            file.write(obj.toJSONString());
            System.out.println("Successfully Copied JSON Object to File...");
            System.out.println("\nJSON Object: " + obj);
        }catch (IOException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public ArrayList<UserDTO> read() {
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader(this.filePath));

            JSONObject jsonObject = (JSONObject) obj;
            JSONArray users = (JSONArray) jsonObject.get("users");
            Iterator<String> usersIterator = users.iterator();
            ArrayList<UserDTO> userList = new ArrayList<>();

            for (Object user: users) {
                JSONObject jsonUser = (JSONObject) user;
                UserDTO userDTO = new UserDTO();
                userDTO.setIni(jsonUser.get("ini").toString());
                userDTO.setUserName(jsonUser.get("userName").toString());
                userDTO.setUserId((int)(long)jsonUser.get("userId"));
                userDTO.setPassword(jsonUser.get("password").toString());
                userDTO.setCpr(jsonUser.get("cpr").toString());

                JSONObject jsonRolesObject = (JSONObject) jsonUser;
                JSONArray roles = (JSONArray) jsonRolesObject.get("roles");
                for (Object role: roles) {
                    userDTO.addRole(role.toString());
                }
                userList.add(userDTO);
            }
            return userList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
