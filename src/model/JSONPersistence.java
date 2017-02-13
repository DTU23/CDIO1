package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class JSONPersistence implements DataPersistence {
    private String filePath;

    /**
     * Constructor implements datapersistence by getting filepath passed.
     * @param filePath
     */
    public JSONPersistence(String filePath){
        this.filePath = filePath;
    }

    /**
     * Method writes data to persistent json-file
     * @param users
     * @return boolean Error
     */
    @Override
    public boolean write(ArrayList<UserDTO> users) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JSONObject usersJSON = new JSONObject();
        JSONArray usersArray = new JSONArray();
        // Get all users from input and create json object from it.
        for (int i = 0; i < users.size(); i++){
            JSONObject user = new JSONObject();
            user.put("password", users.get(i).getPassword());
            user.put("cpr", users.get(i).getCpr());
            user.put("ini", users.get(i).getIni());
            user.put("userName", users.get(i).getUserName());
            user.put("userId", users.get(i).getUserId());
            JSONArray roles = new JSONArray();
            for (String role: users.get(i).getRoles()) {
                roles.add(role);
            }
            user.put("roles", roles);
            usersArray.add(user);
        }
        usersJSON.put("users", usersArray);

        // try-with-resources statement based on post comment below :)
        try (FileWriter file = new FileWriter(this.filePath)) {
            file.write(gson.toJson(usersJSON));
            System.out.println("Successfully Copied JSON Object to File...");
            System.out.println("\nJSON Object: \n" + gson.toJson(usersJSON));
        }catch (IOException e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Method reads data from json file to memory
     * @return ArrayList<UserDTO>
     */
    @Override
    public ArrayList<UserDTO> read() {
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader(this.filePath));

            JSONObject jsonObject = (JSONObject) obj;
            JSONArray users = (JSONArray) jsonObject.get("users");

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
