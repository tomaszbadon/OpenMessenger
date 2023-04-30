package net.bean.java.open.messenger.service;

import net.bean.java.open.messenger.model.User;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public interface MessagingManagementService {

    void createUser(String userName, String password);

    void createQueue(String queueName) throws IOException, TimeoutException;

    void assignUserToApplicationVirtualHost(User user);

    void deleteUser(String userName);

    void deleteAllUsers();

    void deleteAllQueues();
}
