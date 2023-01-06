package com.chitly.URLconverter;

import java.net.URI;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;

@Controller
public class URLMappingController {
	
	public UrlMappingService urlMappingService;
	
	public URLMappingController(UrlMappingService urlMappingService) {
		this.urlMappingService = urlMappingService;
	}
	
	@GetMapping("/")
    public String showForm(UrlMapping urlMapping) {
        return "index";
    }
	
	@PostMapping("/")
	public String convertURL(
			@Valid UrlMapping urlMapping, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "index";
        }
        
        model.addAttribute("urlMapping", urlMappingService.generateChit(urlMapping));
        return "index";
	}
	
	@GetMapping("/{chit}")
	public ResponseEntity<Void> redirectUsingChit(@PathVariable("chit") String chit) {
		String mappedUrl = urlMappingService.getUrlForChit(chit);
		
		if (!mappedUrl.startsWith("http")) {
			mappedUrl = "http://" + mappedUrl;
		}
		
		return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(mappedUrl)).build();
	}
    

}
