package com.shivamchikara.Nagasaki.City.Food;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class NagasakiCityFoodApplication {

	public static void main(String[] args) {
		SpringApplication.run(NagasakiCityFoodApplication.class, args);
	}

}
