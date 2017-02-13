package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.*;

public class UserDTO implements Serializable{
	private static final long serialVersionUID = 4545864587995944260L;
	private int	userId;
	private String userName;
	private String password;
	private String cpr;
	private String ini;
	private ArrayList<String> roles;
	
	public UserDTO() throws DTOException{
		this(new HashMap<String, Object>());
	}

	public UserDTO(HashMap<String, Object> hashMap) throws DTOException{
		this.roles = new ArrayList<>();
		if (hashMap.containsKey("username")){
			this.userName = hashMap.get("username").toString();
		}else{
			throw new DTOException("Not username provided");
		}
		if(hashMap.containsKey("cpr")){
			this.setCpr(hashMap.get("cpr").toString());
		}else{
			throw new DTOException("No CPR provided");
		}
		if(hashMap.containsKey("password")){
			this.setPassword(hashMap.get("password").toString());
		}else{
			this.setPassword("asdgfwe8g932");
		}
		if(hashMap.containsKey("ini")){
			this.setIni(hashMap.get("ini").toString());
		}
		if(hashMap.containsKey("roles")){
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
	public String getPassword(){
		return this.password;
	}

	/**
	 * Sets new password for user
	 * @param password
	 */
	public void setPassword(String password){
		if (this.validatePassword(password)){
			this.password = password;
		}
	}

	/**
	 * Gets CPR number from user DTO.
	 * @return
	 */
	public String getCpr(){
		return this.cpr;
	}

	/**
	 * Sets CPR number from user DTO.
	 * @param cpr
	 */
	public void setCpr(String cpr){
		this.cpr = cpr;
	}

	/**
	 * Retrieves roles from user DTO.
	 * @return
	 */
	public ArrayList<String> getRoles() {
		return this.roles;
	}

	/**
	 * Sets roles on user DTO.
	 * @param roles
	 */
	public void setRoles(ArrayList<String> roles) {
		this.roles = roles;
	}

	/**
	 * Adds a role to user DTO.
	 * @param role
	 */
	public void addRole(String role){
		this.roles.add(role);
	}

	/**
	 * Removes a role from user DTO.
	 * @param role
	 * @return true if role existed, false if not
	 */
	public boolean removeRole(String role){
		return this.roles.remove(role);
	}

	private boolean validatePassword(String password){
		Pattern p = Pattern.compile("(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}");
		Matcher m = p.matcher(password);
		return m.matches();
	}

	@Override
	public String toString() {
		return "\n [\n\t userId=" + userId + "\n\t userName=" + userName + "\n\t ini=" + ini + "\n\t CPR=" + cpr + "\n\t roles=" + roles+"\n ]\n";
	}

	public class DTOException extends Exception{
		
		private static final long serialVersionUID = -7237020336150973814L;
		
		public DTOException(String msg, Throwable e){
			super(msg, e);
		}
		public DTOException(String msg){
			super(msg);
		}
	}
}
