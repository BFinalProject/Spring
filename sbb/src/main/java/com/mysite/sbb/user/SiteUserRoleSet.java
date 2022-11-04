package com.mysite.sbb.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.Data;

@Entity
@Data
public class SiteUserRoleSet {

	@Id
	private String username;

	@OneToOne(mappedBy = "siteUserRoleSet")
	private SiteUser siteUser;
	
	@Column(columnDefinition = "Integer", nullable = false)
	private Integer roleset = -1;

}
