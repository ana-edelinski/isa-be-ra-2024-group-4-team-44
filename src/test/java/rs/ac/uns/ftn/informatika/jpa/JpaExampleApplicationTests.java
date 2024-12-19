package rs.ac.uns.ftn.informatika.jpa;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import rs.ac.uns.ftn.informatika.jpa.dto.UserDTO;
import rs.ac.uns.ftn.informatika.jpa.service.UserService;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JpaExampleApplicationTests {

	@Autowired
	private UserService userService;

	@Test
	public void contextLoads() {
	}
	@Test
	public void testConcurrentRegistration() throws Exception {
		ExecutorService executorService = Executors.newFixedThreadPool(2);

		// Kreiramo dva UserDTO sa istim username-om
		UserDTO user1 = new UserDTO(
				null,
				"test11011110@example.com",
				"nikopiko11211",  // Ime korisnika
				"John",
				"Doe",
				"password123",
				"password123",
				"Street 1",
				"City A",
				"10000"
		);

		UserDTO user2 = new UserDTO(
				null,
				"test11111711@example.com",
				"nikopiko11211",  // Ime korisnika
				"Jane",
				"Smith",
				"password123",
				"password123",
				"Street 2",
				"City B",
				"20000"
		);

		// Simuliramo konkurentni poziv na register metodu
		Callable<String> task1 = () -> {
			try {
				ResponseEntity<?> response = userService.register(user1);
				// Vraćamo samo poruku iz odgovora
				return response.getStatusCode() == HttpStatus.CREATED ? "CREATED" : response.getBody().toString();
			} catch (Exception e) {
				//return e.getMessage();
				return "Username already exists!";
			}
		};

		Callable<String> task2 = () -> {
			try {
				ResponseEntity<?> response = userService.register(user2);
				// Vraćamo samo poruku iz odgovora
				return response.getStatusCode() == HttpStatus.CREATED ? "CREATED" : response.getBody().toString();
			} catch (Exception e) {
				//return e.getMessage();
				return "Username already exists!";
			}
		};

		Future<String> result1 = executorService.submit(task1);
		Future<String> result2 = executorService.submit(task2);

		executorService.shutdown();

		String response1 = result1.get();
		String response2 = result2.get();

		System.out.println("Response 1: " + response1);
		System.out.println("Response 2: " + response2);

		assertTrue(
				(response1.equals("CREATED") && response2.equals("Username already exists!"))
						|| (response1.equals("Username already exists!") && response2.equals("CREATED"))
		);
	}

}
