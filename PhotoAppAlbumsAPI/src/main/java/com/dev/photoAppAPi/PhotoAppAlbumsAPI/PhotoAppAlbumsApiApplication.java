package com.dev.photoAppAPi.PhotoAppAlbumsAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class PhotoAppAlbumsApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(PhotoAppAlbumsApiApplication.class, args);
	}

}
