package com.example.mvc.revise.dto;

import java.util.Calendar;

import org.springframework.web.multipart.MultipartFile;

public class Spittle {

    private String id;
    private String message;
    private Calendar postedOn;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private MultipartFile profilePicture;

    

	public MultipartFile getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(MultipartFile profilePicture) {
		this.profilePicture = profilePicture;
	}

	public Spittle(String id, String message, Calendar postedOn) {
        this.id = id;
        this.message = message;
        this.postedOn = postedOn;
    }

    public Spittle() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Calendar getPostedOn() {
        return postedOn;
    }

    public void setPostedOn(Calendar postedOn) {
        this.postedOn = postedOn;
    }

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

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

	@Override
	public String toString() {
		return "Spittle [id=" + id + ", message=" + message + ", postedOn=" + postedOn + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", username=" + username + ", password=" + password + "]";
	}
    
    	
}
