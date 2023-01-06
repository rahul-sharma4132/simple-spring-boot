package com.chitly.URLconverter;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

@Entity
public class URLForm {
	
	@Id
    @NotBlank(message = "URL is mandatory")
    private String URL;
    
	public URLForm() {}

    public URLForm(String URLname) {
        this.URL = URLname;
    }

    

    public String getURL() {
		return URL;
	}

	public void setURL(String uRL) {
		URL = uRL;
	}

	@Override
    public String toString() {
        return "URLForm{" + "URL=" + URL + '}';
    }
}
