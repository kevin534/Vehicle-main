package com.generation.fileupload.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.springframework.beans.factory.annotation.Autowired;

import com.generation.fileupload.config.CustomProperties;



@Entity
public class Veicolo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String name;	
	private String photo;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
	
	@Transient // non viene salvata su database
    public String getPhotosImagePath() {
        if (photo == null) return null;
        
//        return "/user-photos/" + id + "/" + photo;
        return "/" + CustomProperties.basepath + "/" + id + "/" + photo;
    }

	
	
}
