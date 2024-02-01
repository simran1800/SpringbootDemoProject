package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @Column(name = "name")
  private String name;

  @Column(name = "age")
  private int age;

  @Column(name = "gender")
  private String gender;
  
  @Column(name = "city")
  private String city;

  public User() {

  }

public User(long id, String name, int age, String gender, String city) {
	
	this.name = name;
	this.age = age;
	this.gender = gender;
	this.city = city;
}

public long getId() {
	return id;
}


public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public int getAge() {
	return age;
}

public void setAge(int age) {
	this.age = age;
}

public String getGender() {
	return gender;
}

public void setGender(String gender) {
	this.gender = gender;
}

public String getCity() {
	return city;
}

public void setCity(String city) {
	this.city = city;
}

@Override
public String toString() {
	return "User [id=" + id + ", name=" + name + ", age=" + age + ", gender=" + gender + ", city=" + city + "]";
}

  

  
}
