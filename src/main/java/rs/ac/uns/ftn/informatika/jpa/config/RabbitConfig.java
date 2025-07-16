package rs.ac.uns.ftn.informatika.jpa.config;


import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitConfig {
    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public FanoutExchange advertisingExchange() {
        return new FanoutExchange("advertising.exchange");
    }

}
