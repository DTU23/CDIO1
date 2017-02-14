package model;

import java.io.*;
import java.util.ArrayList;

public class FileStorage implements IDataStorage {

    private String path;

    public FileStorage(String path) {
        this.path = path;
    }

    @Override
    public boolean write(ArrayList<UserDTO> users) {
        ObjectOutputStream oOS = null;

        try {
            FileOutputStream fOS = new FileOutputStream(path);
            oOS = new ObjectOutputStream(fOS);
            oOS.writeObject(users);
        } catch (FileNotFoundException e) {
            throw new DALException("Error locating file", e);
        } catch (IOException e) {
            throw new DALException("Error writing to disk", e);
        } finally {
            if (oOS!=null) {
                try {
                    oOS.close();
                } catch (IOException e) {
                    throw new DALException("Unable to close ObjectStream", e);
                }
            }
        }
    }

    @Override
    public ArrayList<UserDTO> read() throws DALException {
        UserDAO userStore = new UserDAO(path);
        ObjectInputStream oIS = null;
        try {

            FileInputStream fIS = new FileInputStream(path);
            oIS = new ObjectInputStream(fIS);
            Object inObj = oIS.readObject();

            if (inObj instanceof UserDAO){
                userStore = (UserDAO) inObj;
            } else {
                throw new DALException("Wrong object in file");
            }

        } catch (FileNotFoundException e) {
            //No problem - just returning empty userstore
        } catch (IOException e) {
            throw new DALException("Error while reading disk!", e);
        } catch (ClassNotFoundException e) {
            throw new DALException("Error while reading file - Class not found!", e);
        } finally {
            if (oIS!=null){
                try {
                    oIS.close();
                } catch (IOException e) {
                    throw new DALException("Error closing pObjectStream!", e);
                }
            }
        }

        return userStore;
    }

    public boolean fileExists() {
		return new File(path).exists();
	}

	public void createFile() {
		try{
			PrintWriter writer = new PrintWriter(path, "UTF-8");
			writer.println("");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
