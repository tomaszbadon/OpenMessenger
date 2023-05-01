package net.bean.java.open.messenger.service;

import com.rabbitmq.http.client.Client;
import com.rabbitmq.http.client.domain.QueueInfo;
import com.rabbitmq.http.client.domain.UserPermissions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bean.java.open.messenger.config.RabbitMqConfig;
import net.bean.java.open.messenger.model.User;
import net.bean.java.open.messenger.util.UserQueueNameProvider;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.concurrent.TimeoutException;

@Service
@RequiredArgsConstructor
@Slf4j
public class RabbitMqManagementService implements MessagingManagementService {

    private final static String NO_PERMISSION = "^$";

    @Value("${application.rabbitmq.user}")
    private String userName;

    private final Client client;

    private final RabbitMqConfig.RabbitMqVirtualHost applicationVirtualHost;

    private final UserQueueNameProvider userQueueNameProvider;

    @Override
    public void createUser(String userName, String password) {
        client.createUser(userName, password.toCharArray(), List.of());
        log.info(MessageFormat.format("User: {0} has been created in RabbitMQ instance successfully", userName));
    }

    @Override
    public void createQueue(String queueName) throws IOException, TimeoutException {
        client.declareQueue(applicationVirtualHost.getName(), queueName, new QueueInfo(false, false, false));
        log.info(MessageFormat.format("The queue: {0} has been created", queueName));
    }

    @Override
    public void assignUserToApplicationVirtualHost(User user) {
        String queueName = userQueueNameProvider.createQueueName(user);
        client.updatePermissions(applicationVirtualHost.getName(), user.getUserName(), new UserPermissions(queueName, NO_PERMISSION, NO_PERMISSION));
        log.info(MessageFormat.format("The user: {1} has been assigned successfully to a VirtualHost: {0}"
                , applicationVirtualHost.getName(), user.getUserName()));
    }

    @Override
    public void deleteUser(String userName) {
        client.deleteUser(userName);
    }

    @Override
    public void deleteAllUsers() {
        client.getUsers().stream().filter(u -> !userName.equals(u.getName())).forEach(u -> {
            client.deleteUser(u.getName());
            log.info(MessageFormat.format("User: {0} has been deleted from RabbitMQ", u.getName()));
        });
    }

    @Override
    public void deleteAllQueues() {
        client.getQueues().stream().forEach(q -> client.deleteQueue(applicationVirtualHost.getName(), q.getName()));
    }


}
