package az.finalproject.mspayment.config;

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
    public static final String QUEUE = "payment-service-queue";
    public static final String EXCHANGE = "order-exchange";

    @Bean
    public Queue paymentQueue() {
        return new Queue(QUEUE);
    }

    @Bean
    public TopicExchange orderExchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Binding binding(Queue paymentQueue, TopicExchange orderExchange) {
        return BindingBuilder.bind(paymentQueue).to(orderExchange).with("order.delivered");
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}