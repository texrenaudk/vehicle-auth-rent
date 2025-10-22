package com.renaudk.auth_service;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AuthServiceApplication {

	public static void main(String[] args) {
        Dotenv dotenv = Dotenv.configure()
                .directory("./auth-service/") // <-- LE CHEMIN CORRIGÉ
                .load();

        // Définit chaque clé-valeur comme une Propriété Système.
        dotenv.entries().forEach(entry -> {
            System.setProperty(entry.getKey(), entry.getValue());
        });

        SpringApplication.run(AuthServiceApplication.class, args);
	}

}
