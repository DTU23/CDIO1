package model;

import java.util.ArrayList;

/**
 * Simple class extension to modify arraylist tostring method
 * @param <e>
 */
public class DTOList<E> extends ArrayList<E> {
   
	private static final long serialVersionUID = 6503781544938620653L;

	@Override
    public String toString(){
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < this.size(); i++){
            result.append(this.get(i).toString()+ "\n");
        }
        return result.toString().substring(0, result.toString().length()-1);
    }
}