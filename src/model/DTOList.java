package model;

import java.util.ArrayList;

/**
 * Simple class extension to modify arraylist tostring method
 * @param <e>
 */
public class DTOList<e> extends ArrayList<e> {
    @Override
    public String toString(){
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < this.size(); i++){
            result.append(this.get(i).toString()+ "\n");
        }
        return result.toString().substring(0, result.toString().length()-1);
    }
}