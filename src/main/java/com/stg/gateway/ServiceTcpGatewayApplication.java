package com.stg.gateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class ServiceTcpGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceTcpGatewayApplication.class, args);
		log.info("stg is started.");
	}

}
