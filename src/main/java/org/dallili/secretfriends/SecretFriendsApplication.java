package org.dallili.secretfriends;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SecretFriendsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecretFriendsApplication.class, args);
	}

}
