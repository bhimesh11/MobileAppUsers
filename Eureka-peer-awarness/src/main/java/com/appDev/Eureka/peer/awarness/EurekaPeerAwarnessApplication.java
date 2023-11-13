package com.appDev.Eureka.peer.awarness;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaPeerAwarnessApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaPeerAwarnessApplication.class, args);
	}

}
