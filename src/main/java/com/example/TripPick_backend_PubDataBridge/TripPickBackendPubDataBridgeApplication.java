package com.example.TripPick_backend_PubDataBridge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class TripPickBackendPubDataBridgeApplication {

	public static void main(String[] args) {
		SpringApplication.run(TripPickBackendPubDataBridgeApplication.class, args);
	}

}
