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

    @Value("${spring.rabbitmq.username}")
    private String userName;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Value("${application.rabbitmq.api.url}")
    private String apiUrl;

    @Value("${spring.rabbitmq.virtual-host}")
    private String virtualHost;

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
