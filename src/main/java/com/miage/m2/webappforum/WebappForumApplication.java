package com.miage.m2.webappforum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;

@SpringBootApplication
public class WebappForumApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebappForumApplication.class, args);
	}
}
