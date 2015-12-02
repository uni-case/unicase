package tr.org.unicase.authentication.service.model;

import java.util.Collection;
import java.util.Date;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import tr.org.unicase.kernel.model.IEntity;

public class User implements UserDetails, IEntity {

	private Long id;

	private String name;

	private String surname;

	private String email;

	private String userName;

	private String role;

	private String clear;

	private String encrypted;
	
	private Date expireDate;

	private int status;

	public void setRole(String role) {
		this.role = role;
	}

	public String getRole() {
		return role;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getSurname() {
		return surname;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserName() {
		return userName;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setClear(String clear) {
		this.clear = clear;
	}

	public String getClear() {
		return clear;
	}

	public void setEncrypted(String encrypted) {
		this.encrypted = encrypted;
	}

	public String getEncrypted() {
		return encrypted;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getStatus() {
		return status;
	}
	
	public void setExpireDate(Date date) {
		this.expireDate = date;
	}
	
	public Date getExpireDate() {
		return expireDate;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public String getPassword() {
		return getEncrypted();
	}

	@Override
	public String getUsername() {
		return getUserName();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public int compareTo(IEntity arg0) {
		return 0;
	}

	@Override
	public Long getId() {
		return this.id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String getLock() {
		return null;
	}
	
	@Override
	public String toString() {
		return this.email;
	}

	@Override
	public void setLock(String lock) {
		
	}

	@Override
	public Boolean isLocked() {
		return false;
	}

	@Override
	public IEntity getCk_owner() {
		return null;
	}

}
