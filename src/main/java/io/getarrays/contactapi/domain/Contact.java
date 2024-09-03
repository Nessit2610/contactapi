package io.getarrays.contactapi.domain;

import org.hibernate.annotations.UuidGenerator;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@JsonInclude(content = Include.NON_DEFAULT)
@Table(name="contacts")
public class Contact {
	
	@Id
	@UuidGenerator
	@Column(name ="id", unique= true, updatable = false)
	private String id;
	
	@Column(name="name")
	private String name;
	
	@Column(name = "email")
	private String email;
	
	@Column(name="title")
	private String title;
	
	@Column(name="phone")
	private String phone;
	
	@Column(name="address")
	private String address;
	
	@Column(name="status")
	private String status;
	
	@Column(name="photourl")
	private String photourl;
	public Contact() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Contact(String id, String name, String email, String title, String phone, String address, String status,
			String photourl) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.title = title;
		this.phone = phone;
		this.address = address;
		this.status = status;
		this.photourl = photourl;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPhotourl() {
		return photourl;
	}
	public void setPhotourl(String photourl) {
		this.photourl = photourl;
	}
	
	
}
