package control;

import model.dto.UserDTO;

import java.util.ArrayList;

/**
 *
 */
public interface DataPersistence {
    /**
     * Updates memory to persistent data. Returns false on error
     * @param users
     * @return boolean Error
     */
    boolean write(ArrayList<UserDTO> users);

    /**
     * Reads data input to memory
     * @return ArrayList<UserDTO>
     */
    ArrayList<UserDTO> read();
}
