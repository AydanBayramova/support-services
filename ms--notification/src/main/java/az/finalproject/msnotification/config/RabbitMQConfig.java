package az.finalproject.msnotification.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String QUEUE = "notification-service-queue";
    public static final String EXCHANGE = "order-exchange";
    public static final String ROUTING_KEY = "order.#";

    @Bean
    public Queue notificationQueue() {
        return new Queue(QUEUE);
    }

    @Bean
    public TopicExchange orderExchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Binding binding(Queue notificationQueue, TopicExchange orderExchange) {
        return BindingBuilder
                .bind(notificationQueue)
                .to(orderExchange)
                .with(ROUTING_KEY);
    }

    @Bean
    public MessageConverter jsonMessageConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }
}