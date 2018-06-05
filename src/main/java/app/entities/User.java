package app.entities;

import java.util.HashMap;

import app.singletons.UserType;

public class User {

	private String name;
	private HashMap<Integer, String> email;
	private String password;
	private HashMap<Integer, Institution> institution;
	private int lastEmailKey;
	private int lastInstitutionKey;
	private UserType userType;

	public User(String name, String email, String password, Institution institution){
    	this.name 		 		= name;
    	this.email 		 		= new HashMap<Integer, String>();
    	this.email.put(1, email);
    	this.password    		= password;
    	this.institution 		= new HashMap<Integer, Institution>();
    	this.institution.put(1, institution);
    	this.lastEmailKey		= this.email.size()+1;
    	this.lastInstitutionKey = this.institution.size()+1;
    }
	
    public User(String name, HashMap<Integer, String> email, String password, HashMap<Integer, Institution> institution){
    	this.name 		 		= name;
    	this.email 		 		= email;
    	this.password    		= password;
    	this.institution 		= institution;
    	this.lastEmailKey		= this.email.size()+1;
    	this.lastInstitutionKey = this.institution.size()+1;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public HashMap<Integer, String> getEmail() {
		return email;
	}

	public void setEmail(HashMap<Integer, String> email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public HashMap<Integer, Institution> getInstitution() {
		return institution;
	}

	public void setInstitution(HashMap<Integer, Institution> institution) {
		this.institution = institution;
	}

	public void addEmail(String newEmail) {
		email.put(lastEmailKey, newEmail);
	}
	
	public void removeEmail(int key) {
		email.remove(key);
	}
	
	public void addInstitution(Institution newInstitution) {
		institution.put(lastInstitutionKey, newInstitution);
	}
	
	public void removeInstitution(int key) {
		institution.remove(key);
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}
	
}