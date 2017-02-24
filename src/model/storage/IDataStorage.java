package model.storage;

import model.DTOList;
import model.UserDTO;

import java.io.IOException;
import java.util.ArrayList;

/**
 *
 */
public interface IDataStorage {
    /**
     * Updates memory to persistent data. Returns false on error
     * @param users array-list of userDTO objects
     */
    void write(ArrayList<UserDTO> users) throws IOException;

    /**
     * Reads data input to memory
     * @return ArrayList<UserDTO>
     */
    DTOList<UserDTO> read() throws IOException, ClassNotFoundException;
}
