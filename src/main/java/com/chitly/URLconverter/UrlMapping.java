package com.chitly.URLconverter;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

@Entity
public class UrlMapping {
	
    @NotBlank(message = "URL is mandatory")
    private String targetUrl;

	@Id
    private String chit;
    
	public String getTargetUrl() {
		return targetUrl;
	}

	public void setTargetUrl(String targetUrl) {
		this.targetUrl = targetUrl;
	}
	
	public String getChit() {
		return chit;
	}

	public void setChit(String chit) {
		this.chit = chit;
	}

	@Override
    public String toString() {
        return "URLForm{" + "targetUrl=" + targetUrl + ", chit=" + chit + '}';
    }
}
