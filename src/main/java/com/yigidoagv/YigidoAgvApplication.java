package com.yigidoagv;

import com.yigidoagv.model.Role;
import com.yigidoagv.model.User;
import com.yigidoagv.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.Collections;

@SpringBootApplication
@EnableAsync
@RequiredArgsConstructor
public class YigidoAgvApplication implements CommandLineRunner {

	private final UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(YigidoAgvApplication.class, args);
	}

	@Override
	@Transactional
	public void run(String... args) {

		User user = userRepository.findByUsername("agv");

		if (user == null) {
			user = new User();
			user.setUsername("agv");
			user.setPassword("$2a$10$dqSAlXca8dh3HQCDrdjII.R.nkxtqFmoNLc.OrSSbOwhc9btijOMa");
			user.setAuthorities(Collections.singleton(Role.ROLE_ADMIN));
			userRepository.save(user);
		}

		User user1 = userRepository.findByUsername("furkan");

		if (user1 == null) {
			user1 = new User();
			user1.setUsername("furkan");
			user1.setPassword("$2a$10$dqSAlXca8dh3HQCDrdjII.R.nkxtqFmoNLc.OrSSbOwhc9btijOMa");
			user1.setAuthorities(Collections.singleton(Role.ROLE_ADMIN));
			userRepository.save(user1);
		}

	}
}
