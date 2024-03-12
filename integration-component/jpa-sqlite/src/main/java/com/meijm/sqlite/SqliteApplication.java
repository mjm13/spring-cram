package com.meijm.sqlite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SqliteApplication {
	public static void main(String[] args) {
		SpringApplication.run(SqliteApplication.class, args);
	}

}
