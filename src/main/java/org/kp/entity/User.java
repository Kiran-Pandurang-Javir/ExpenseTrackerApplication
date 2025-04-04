package org.kp.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "user" )
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;
    
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "active", nullable = false)
    private Boolean active = true;

    public User(){

    }
    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password=password;
        this.active = true;
    }

    public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        active = true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public boolean isActive() {
        return active;

    }
    public void setActive(boolean active){
    this.active = active;
    }
}

