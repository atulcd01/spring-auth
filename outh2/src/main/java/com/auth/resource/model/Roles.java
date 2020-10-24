package com.auth.resource.model;

import javax.persistence.*;

@Entity
@Table(name = "authorities")
public class Roles {
    @Id
    @TableGenerator( name = "DbTableGenerator", table = "PK_GENERATOR", pkColumnName = "SEQ_NAME", pkColumnValue = "AUTHORITIES.id", valueColumnName = "SEQ_VALUE", initialValue = 1, allocationSize = 1 )	
	@GeneratedValue(strategy=GenerationType.TABLE,generator="DbTableGenerator")
    private Long id;

    private String username;

    private String authority;

	public Roles(String username, String authority) {
		this.authority = authority;
		this.username = username;
	}

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

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}
    
    
   
   
}
