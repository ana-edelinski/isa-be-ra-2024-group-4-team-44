package rs.ac.uns.ftn.informatika.jpa;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import rs.ac.uns.ftn.informatika.jpa.service.UserService;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableCaching
public class JpaExampleApplication {

	@Bean
	public ModelMapper getModelMapper() {
		return new ModelMapper();
	}

	@Autowired
	private UserService userService;
	
	public static void main(String[] args) {
		SpringApplication.run(JpaExampleApplication.class, args);
	}

//	@PostConstruct
//	public void testConcurrency() {
//		new Thread(() -> userService.simulateFollowWithDelay(1, 2)).start();
//		new Thread(() -> userService.simulateFollowWithDelay(3, 2)).start();
//	}

}
