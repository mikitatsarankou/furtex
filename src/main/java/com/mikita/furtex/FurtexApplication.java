package com.mikita.furtex;

import lombok.extern.java.Log;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@Log
public class FurtexApplication {

    public static void main(String[] args) {
		SpringApplication.run(FurtexApplication.class, args);
	}
}
