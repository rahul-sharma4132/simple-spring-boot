package com.chitly.URLconverter;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class UrlMappingService {
	
	public URLRepository urlRepository;
	
	public UrlMappingService(URLRepository urlRepo) {
		this.urlRepository = urlRepo;
	}
	
	public UrlMapping generateChit(UrlMapping originalUrl) {
		originalUrl.setChit(createChit(originalUrl.getTargetUrl()));
		
		UrlMapping newUrlMapping = urlRepository.save(originalUrl);
		return newUrlMapping;
	}
	
	public String getUrlForChit(String chit) {
		Optional<UrlMapping> maybeFoundMapping = urlRepository.findById(chit);
		
		if (maybeFoundMapping.isPresent()) {
			UrlMapping urlMapping = maybeFoundMapping.get();
			
			return urlMapping.getTargetUrl();
		} else {
			return "/";
		}
	}

	private String createChit(String targetUrl) {
		try {
			UUID uuid = UUID.nameUUIDFromBytes(targetUrl.getBytes("utf-8"));
			return uuid.toString().substring(0, 5);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
