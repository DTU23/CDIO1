package model;

import java.io.*;
import java.util.ArrayList;
import model.DTOList;

public class FileStorage implements IDataStorage {

    private String path;

    public FileStorage() {
        this(System.getProperty("user.dir")+"/src/model/data.txt");
    }
    public FileStorage(String path) {
        this.path = path;
    }

    @Override
    public boolean write(ArrayList<UserDTO> users) throws DALException {
        FileOutputStream fOS = null;
        ObjectOutputStream oOS = null;

        try {
            // Serialize the collection of UserDTO's
            fOS = new FileOutputStream(path);
            oOS = new ObjectOutputStream(fOS);

            // Write every object to the file
            for (UserDTO user : users) {
                oOS.writeObject(user);
            }
        } catch (Exception e) {
            throw new DALException("Unable to write to file", e);
        }
        finally {
            if (oOS != null) {
                try {
                    oOS.close();
                } catch (IOException e) {
                    throw new DALException("Unable to close ObjectStream", e);
                }
            }
        }
        return true;
    }

    @Override
    public ArrayList<UserDTO> read() throws DALException {
        if(!fileExists()) {
            createFile();
        }

        DTOList<UserDTO> userList = new DTOList<>();
        FileInputStream fIS = null;
        ObjectInputStream oIS = null;

        try {
            // Deserialize the file back into the collection
            fIS = new FileInputStream(path);
            oIS = new ObjectInputStream(fIS);

            // Pull every object into the ArrayList
            try {
                while (true) {
                    UserDTO user = (UserDTO) oIS.readObject();
                    if (user instanceof UserDTO) {
                        userList.add(user);
                    } else {
                        throw new DALException("Wrong object in file");
                    }
                }
            } catch (Exception e) {
                // No problem - no more objects to import
            }
        } catch (Exception e){
            throw new DALException("Unable to read file", e);
        } finally {
            if (oIS != null){
                try {
                    oIS.close();
                } catch (IOException e) {
                    throw new DALException("Error closing pObjectStream!", e);
                }
            }
        }
        return userList;
    }

    public boolean fileExists() {
        return new File(path).exists();
    }

    public void createFile() throws DALException {
        PrintWriter writer = null;
        try{
            writer = new PrintWriter(path, "UTF-8");
            writer.println("");
        } catch (IOException e) {
            throw new DALException("Unable to create file", e);
        } finally {
            if(writer != null) {
                try {
                    writer.close();
                } catch (Exception e) {
                    throw new DALException("Unable to close writer", e);
                }
            }
        }
    }

}
