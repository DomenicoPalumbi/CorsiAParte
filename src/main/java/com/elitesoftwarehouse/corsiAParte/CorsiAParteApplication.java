package com.elitesoftwarehouse.corsiAParte;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CorsiAParteApplication {

	public static void main(String[] args) {
		SpringApplication.run(CorsiAParteApplication.class, args);

	}

}
