package com.example.mvc.revise.model;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name =  "customer")
public class Customer implements Serializable{

	private static final long serialVersionUID = 5126403996246391250L;
	
	@Id
	@Basic
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Basic
	@Column(name = "first_name")
	private String fName;
	
	@Column(name = "last_name")
	@Basic
	private String lName;
	
	@Basic
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_on")
	@CreationTimestamp
	private Calendar createdOn;

	@Basic
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_on")
	@UpdateTimestamp
	private Calendar updatedOn;
	
	@Basic
	@Column(name = "is_deleted")
	private Boolean isDeleted;

	@Basic
	@Column(name = "is_active")
	private Boolean isActive;
	
}
