package net.bean.java.open.messenger.rest.util;

import com.google.common.hash.Hashing;
import net.bean.java.open.messenger.model.User;
import net.bean.java.open.messenger.rest.model.InputMessagePayload;
import net.bean.java.open.messenger.service.MessageService;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Random;

public class DummyMessageCreator {
    private final MessageService messageService;
    private final SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    private Date date = new Date();
    private final Random random = new Random();

    public DummyMessageCreator(MessageService messageService) {
        this.messageService = messageService;
    }

    public DummyMessageCreator withDate(String date) {
        date = format.format(date);
        return this;
    }

    public DummyMessageCreator withAdditionalDay() {
        Instant instant = date.toInstant().plus(1, ChronoUnit.DAYS);
        date = Date.from(instant);
        return this;
    }

    public DummyMessageCreator withAdditionalSeconds() {
        Instant instant = this.date.toInstant().plus(someRandomUnits(), ChronoUnit.SECONDS);
        this.date = Date.from(instant);
        return this;
    }
    public DummyMessageCreator createRead(int numberOfMessages, User sender, User recipient) {
        return createMessages(numberOfMessages, sender, recipient, true);
    }

    public DummyMessageCreator createUnread(int numberOfMessages, User sender, User recipient) {
        return createMessages(numberOfMessages, sender, recipient, false);
    }

    private DummyMessageCreator createMessages(int numberOfMessages, User sender, User recipient, boolean read) {
        for(int i=0 ; i < numberOfMessages; i++) {
            String message = createMessage(sender, recipient);
            InputMessagePayload inputMessagePayload = new InputMessagePayload();
            inputMessagePayload.setMessage(message);
            inputMessagePayload.setRecipient(recipient.getId());
            messageService.handleNewMessage(inputMessagePayload, date, sender, read);
            withAdditionalSeconds();
        }
        return this;
    }

    private String createMessage(User sender, User recipient) {
        String message = Hashing.sha256().hashInt(random.nextInt()).toString();
        return String.format("%s : sender: %s recipient %s at %s", message, sender.getUserName(), recipient.getUserName(), format.format(date));
    }

    private int someRandomUnits() {
        return random.nextInt(3, 130);
    }

}
