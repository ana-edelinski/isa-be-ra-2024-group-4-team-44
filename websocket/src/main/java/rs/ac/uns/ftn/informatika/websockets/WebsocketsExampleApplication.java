package rs.ac.uns.ftn.informatika.websockets;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

// Primer Spring aplikacije koja koristi WebSockets i STOMP (Simple Text Oriented Messaging Protocol)
// Detaljnije na https://docs.spring.io/spring-framework/docs/5.0.0.BUILD-SNAPSHOT/spring-framework-reference/html/websocket.html
// WebSockets i Security: https://docs.spring.io/spring-security/site/docs/4.0.x/reference/html/websocket.html
@SpringBootApplication
public class WebsocketsExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebsocketsExampleApplication.class, args);
	}


	@Value("${myqueue}")
	String queue;

	@Value("${myqueue2}")
	String queue2;

	@Value("${myexchange}")
	String exchange;

	@Value("${routingkey}")
	String routingkey;


	@Bean
	Queue queue() {
		return new Queue(queue, true);
	}

	@Bean
	Queue queue2() {
		return new Queue(queue2, true);
	}

	@Bean
	DirectExchange exchange() {
		return new DirectExchange(exchange);
	}

	@Bean
	Binding binding(Queue queue2, DirectExchange exchange) {
		return BindingBuilder.bind(queue2).to(exchange).with(routingkey);
	}

	/*
	 * Registrujemo bean koji ce sluziti za konekciju na RabbitMQ gde se mi u
	 * primeru kacimo u lokalu.
	 */
	@Bean
	public ConnectionFactory connectionFactory() {
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory("localhost");
		return connectionFactory;
	}
}
