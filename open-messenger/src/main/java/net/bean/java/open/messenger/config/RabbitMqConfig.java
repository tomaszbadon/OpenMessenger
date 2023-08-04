package net.bean.java.open.messenger.config;

import com.rabbitmq.http.client.Client;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import net.bean.java.open.messenger.exception.InternalException;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

@Configuration
@Profile({"dev", "prod"})
public class RabbitMqConfig {

    @Value("${application.rabbitmq.user}")
    private String userName;

    @Value("${application.rabbitmq.password}")
    private String password;

    @Value("${application.rabbitmq.host}")
    private String host;

    @Value("${application.rabbitmq.api.url}")
    private String apiUrl;

    @Value("${application.rabbitmq.port}")
    private String port;

    @Value("${application.rabbitmq.virtualHost}")
    private String virtualHost;

    @Bean
    public CachingConnectionFactory connectionFactory() {
        CachingConnectionFactory factory = new CachingConnectionFactory("localhost");
        factory.setUsername(userName);
        factory.setPassword(password);
        factory.setVirtualHost(virtualHost);
        factory.setPort(Integer.parseInt(port));
        return factory;
    }

    @Bean
    public RabbitAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        return new RabbitTemplate(connectionFactory());
    }

    @Bean
    public Client client() {
        try {
            return new Client(new URL(apiUrl), userName, password);
        } catch (MalformedURLException | URISyntaxException e) {
            throw new InternalException(e);
        }
    }

    @Bean
    public RabbitMqVirtualHost virtualHost() {
        return new RabbitMqVirtualHost(virtualHost);
    }

    @Data
    @RequiredArgsConstructor
    public class RabbitMqVirtualHost {
        private final String name;
    }

}
