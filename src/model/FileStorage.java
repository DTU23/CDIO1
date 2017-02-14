package model;

import java.util.ArrayList;

/**
 * Created by Christian on 14/02/2017.
 */
public class FileStorage implements IDataStorage {

    @Override
    public boolean write(ArrayList<UserDTO> users) {
        return false;
    }

    @Override
    public ArrayList<UserDTO> read() {
        return null;
    }
}
