package com.renaudk.audit_service;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AuditServiceApplication {

	public static void main(String[] args) {


            Dotenv dotenv = Dotenv.configure()
                    .directory("./audit-service/") // <-- chemin que nous lui obligeons à prendre pour recuperer
                    .load();

            // on recupere les valeurs de notre fichier
            dotenv.entries().forEach(entry -> {
                System.setProperty(entry.getKey(), entry.getValue());
            });


        SpringApplication.run(AuditServiceApplication.class, args);
	}

}
