package com.titan.tune;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TuneApplication {

	public static void main(String[] args) {
		SpringApplication.run(TuneApplication.class, args);
	}

}
