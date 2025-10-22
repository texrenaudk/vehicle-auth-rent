package com.renaudk.user_service;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@SpringBootApplication
public class UserServiceApplication {

	public static void main(String[] args) {

        Dotenv dotenv = Dotenv.configure()
                .directory("./user-service/") // chemin forcé pour arriver à nos variables
                .load();

        // recupere chaque element definis dans notre env
        dotenv.entries().forEach(entry -> {
            System.setProperty(entry.getKey(), entry.getValue());
        });
		SpringApplication.run(UserServiceApplication.class, args);
	}

}
