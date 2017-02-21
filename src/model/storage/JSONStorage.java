package model.storage;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import model.DTOList;
import model.UserDTO;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JSONStorage implements IDataStorage {
    private String filePath;

    /**
     * Constructor overload for dafault filepath
     */
    public JSONStorage() {
        this(System.getProperty("user.dir")+"/src/model/storage/data.json");
    }

    /**
     * Constructor implements datapersistence by getting filepath passed.
     * @param filePath path to which the json file will be read/written
     */
    public JSONStorage(String filePath){
        this.filePath = filePath;
    }

    /**
     * Method writes data to persistent json-file
     * @param users arraylist of userDTO objects
     */
    @Override
    @SuppressWarnings("unchecked")
    public void write(ArrayList<UserDTO> users) throws IOException{
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
            user.put("userID", users.get(i).getUserID());
            JSONArray roles = new JSONArray();
            roles.addAll(users.get(i).getRoles());
            user.put("roles", roles);
            usersArray.add(user);
        }
        usersJSON.put("users", usersArray);

        FileWriter file = new FileWriter(this.filePath);
        file.write(gson.toJson(usersJSON));
        System.out.println("Successfully Copied JSON Object to File...");
        System.out.println("\nJSON Object: \n" + gson.toJson(usersJSON));
    }

    /**
     * Method reads data from json file to memory
     * @return ArrayList<UserDTO>
     */
    @Override
    @SuppressWarnings("unchecked")
    public DTOList<UserDTO> read() throws IOException {
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader(this.filePath));

            JSONObject jsonObject = (JSONObject) obj;
            JSONArray users = (JSONArray) jsonObject.get("users");

            DTOList<UserDTO> userList = new DTOList<>();

            for (Object user: users) {
                JSONObject jsonUser = (JSONObject) user;
                UserDTO userDTO = new UserDTO();
                userDTO.setIni(jsonUser.get("ini").toString());
                userDTO.setUserName(jsonUser.get("userName").toString());
                userDTO.setUserID((int)(long)jsonUser.get("userID"));
                userDTO.setPassword(jsonUser.get("password").toString());
                userDTO.setCpr(jsonUser.get("cpr").toString());
                ArrayList<String> roles = new ArrayList<>();
                roles.addAll((ArrayList<String>) jsonUser.get("roles"));
                userDTO.setRoles(roles);
                userList.add(userDTO);
            }
            return userList;
        } catch (Exception e) {
            throw new IOException("", e);
        }
    }
}
