package az.finalproject.mstracking.config;

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
    public static final String QUEUE = "tracking-service-queue";
    public static final String EXCHANGE = "courier-location-exchange";

    @Bean
    public Queue trackingQueue() { return new Queue(QUEUE); }

    @Bean
    public TopicExchange locationExchange() { return new TopicExchange(EXCHANGE); }

    @Bean
    public Binding binding(Queue trackingQueue, TopicExchange locationExchange) {
        return BindingBuilder.bind(trackingQueue).to(locationExchange).with("courier.location");
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
