package Client;

import java.io.Serializable;

public class User extends Userprint implements Serializable{
	private String username;
    private String password;
    public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	private String gender;
    private String fullname;
    private String email;
    private String phonenumber;

    public User(String username, String password, String gender, String fullname, String email, String phonenumber) {
        this.username = username;
        this.password = password;
        this.gender = gender;
        this.fullname = fullname;
        this.email = email;
        this.phonenumber = phonenumber;
    }

	@Override
	public void print() {
		//System.out.println("user info class");
	}
}
