package model.dto;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserDTO implements Serializable{
	private static final long serialVersionUID = 4545864587995944260L;
	private int	userId;
	private String userName;
	private String password;
	private String cpr;
	private String ini;
	private ArrayList<String> roles;
	//TODO Add relevant fields
	
	public UserDTO() {
		this.roles = new ArrayList<>();
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
	public void setPassword(String password){
		this.password = password;
	}
	public String getCpr(){
		return this.cpr;
	}
	public void setCpr(String cpr){
		this.cpr = cpr;
	}

	public ArrayList<String> getRoles() {
		return this.roles;
	}
	public void setRoles(ArrayList<String> roles) {
		this.roles = roles;
	}
	
	public void addRole(String role){
		this.roles.add(role);
	}
	/**
	 * 
	 * @param role
	 * @return true if role existed, false if not
	 */
	public boolean removeRole(String role){
		return this.roles.remove(role);
	}

	@Override
	public String toString() {
		return "\n [\n\t userId=" + userId + "\n\t userName=" + userName + "\n\t ini=" + ini + "\n\t CPR=" + cpr + "\n\t roles=" + roles+"\n ]\n";
	}
}
