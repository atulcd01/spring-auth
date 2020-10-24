package com.auth.resource.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users")
public class UserProfile {
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @TableGenerator( name = "DbTableGenerator", table = "PK_GENERATOR", pkColumnName = "SEQ_NAME", pkColumnValue = "USER.id", valueColumnName = "SEQ_VALUE", initialValue = 1, allocationSize = 1 )	
	@GeneratedValue(strategy=GenerationType.TABLE,generator="DbTableGenerator")
    private Long id;

    private String username;

    private String password;
    
    private boolean enabled;
    
    private String firstname;
    private String lastname;
    private String email;
    
    private String accountids;
    
    @Transient
    private String passwordConfirm;

   
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAccountids() {
		return accountids;
	}

	public void setAccountids(String accountids) {
		this.accountids = accountids;
	}

   
}
