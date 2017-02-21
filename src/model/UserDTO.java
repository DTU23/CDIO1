package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class UserDTO implements Serializable {
    private static final long serialVersionUID = 4545864587995944260L;
    private int userID;
    private String userName;
    private String ini;
    private String cpr;
    private String password;
    private ArrayList<String> roles;
    
    public UserDTO() {}

    @SuppressWarnings("unchecked")
    public UserDTO(HashMap<String, Object> hashMap) throws DTOException {
        if(hashMap.containsKey("ID")){
            this.userID = (int) hashMap.get("ID");
        }else {
            throw new DTOException("No ID Provided!");
        }
        if (hashMap.containsKey("userName")) {
            this.userName = hashMap.get("userName").toString();
        } else {
            throw new DTOException("No user name provided");
        }
        if (hashMap.containsKey("ini")) {
            this.setIni(hashMap.get("ini").toString());
        } else {
            throw new DTOException("No initials provided");
        }
        if (hashMap.containsKey("cpr")) {
            this.setCpr(hashMap.get("cpr").toString());
        } else {
            throw new DTOException("No CPR provided");
        }
        if (hashMap.containsKey("password")) {
            this.setPassword(hashMap.get("password").toString());
        } else {
            throw new DTOException("No password provided");
        }
        if (hashMap.containsKey("roles")) {
            this.roles = (ArrayList<String>) hashMap.get("roles");
        } else {
            throw new DTOException("No role provided");
        }
    }

    public int getUserID() {
        return this.userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIni() {
        return ini;
    }

    public void setIni(String ini) {
        this.ini = ini;
    }

    public String getPassword() {
        return this.password;
    }

    /**
     * Sets new password for user
     * @param password as a string
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets CPR number from user DTO.
     * @return this cpr String
     */
    public String getCpr() {
        return this.cpr;
    }

    /**
     * Sets CPR number from user DTO.
     * @param cpr cpr-number as string
     */
    public void setCpr(String cpr) {
        this.cpr = cpr;
    }

    /**
     * Retrieves roles from user DTO.
     * @return list of roles
     */
    public ArrayList<String> getRoles() {
        return this.roles;
    }

    /**
     * Sets roles on user DTO.
     * @param roles arroylist of roles-strings
     */
    public void setRoles(ArrayList<String> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "userID = " + userID + ", password = " + password + ", userName = " + userName + ", ini = " + ini + ", cpr = " + cpr + ", roles = " + roles;
    }

    public class DTOException extends Exception {
    	
        private static final long serialVersionUID = -7237020336150973814L;

        public DTOException(String msg, Throwable e) {
            super(msg, e);
        }

        public DTOException(String msg) {
            super(msg);
        }
    }
}
