package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.regex.*;

public class UserDTO implements Serializable {
    private static final long serialVersionUID = 4545864587995944260L;
    private int userId;
    private String userName;
    private String password;
    private String cpr;
    private String ini;
    private ArrayList<String> roles = new ArrayList<String>();

    public UserDTO() throws DTOException {

    }

    public UserDTO(HashMap<String, Object> hashMap) throws DTOException {
        this.roles = new ArrayList<>();
        if (hashMap.containsKey("userName")) {
            this.userName = hashMap.get("userName").toString();
        } else {
            throw new DTOException("No username provided");
        }
        if (hashMap.containsKey("cpr")) {
            this.setCpr(hashMap.get("cpr").toString());
        } else {
            throw new DTOException("No CPR provided");
        }
        if (hashMap.containsKey("password")) {
            this.setPassword(hashMap.get("password").toString());
        } else {
            this.setPassword(this.generatePassword(10));
        }
        if (hashMap.containsKey("ini")) {
            this.setIni(hashMap.get("ini").toString());
        }
        // TODO add exceptionhandling for the casting
        if (hashMap.containsKey("roles")) {
            roles.addAll((ArrayList<String>) hashMap.get("roles"));
        }
    }

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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
     *
     * @param password
     */
    public void setPassword(String password) {
        if (this.validatePassword(password)) {
            this.password = password;
        }
    }

    /**
     * Gets CPR number from user DTO.
     *
     * @return
     */
    public String getCpr() {
        return this.cpr;
    }

    /**
     * Sets CPR number from user DTO.
     *
     * @param cpr
     */
    public void setCpr(String cpr) {
        this.cpr = cpr;
    }

    /**
     * Retrieves roles from user DTO.
     *
     * @return
     */
    public ArrayList<String> getRoles() {
        return this.roles;
    }

    /**
     * Sets roles on user DTO.
     *
     * @param roles
     */
    public void setRoles(ArrayList<String> roles) {
        this.roles = roles;
    }

    /**
     * Adds a role to user DTO.
     *
     * @param role
     */
    public void addRole(String role) {
        this.roles.add(role);
    }

    /**
     * Removes a role from user DTO.
     *
     * @param role
     * @return true if role existed, false if not
     */
    public boolean removeRole(String role) {
        return this.roles.remove(role);
    }

    /**
     * Generates a valid password for current user
     * @param length
     * @return
     */
    private String generatePassword(int length) {
        String charactersCaps = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String characters = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";
        String symbols = "!@#$&*";
        String passwordCharacters = charactersCaps + characters + numbers + symbols;
        Random rnd = new Random();
        char[] password = new char[length];

        do {
            for (int i = 0; i < length; i++) {
                password[i] = passwordCharacters.charAt(rnd.nextInt(passwordCharacters.length()));
            }
        }while (!this.validatePassword(new String(password)));
        return new String(password);
    }

    private boolean validatePassword(String password) {
        Pattern p = Pattern.compile("^(?=.*[A-Z].*[A-Z])(?!.*" + this.userName + ")(?=.*[!@#$&*])(?=.*[0-9].*[0-9])(?=.*[a-z].*[a-z].*[a-z]).{8,}$");
        Matcher m = p.matcher(password);
        return m.matches();
    }

    @Override
    public String toString() {
        return "userID=" + userId + " password=" + password + " userName=" + userName + " ini=" + ini + " cpr=" + cpr + " roles=" + roles;
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
