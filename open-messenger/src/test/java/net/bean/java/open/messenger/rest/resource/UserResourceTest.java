package net.bean.java.open.messenger.rest.resource;

import net.bean.java.open.messenger.rest.model.user.NewUserInfo;
import net.bean.java.open.messenger.rest.model.user.UserInfo;
import net.bean.java.open.messenger.service.MessagingManagementService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class UserResourceTest {

    private final TestRestTemplate restTemplate;

    @MockBean
    private MessagingManagementService messagingManagementService;

    @Autowired
    public UserResourceTest(TestRestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Test
    void createUserTest() {
        NewUserInfo newUserInfo = new NewUserInfo();
        newUserInfo.setUserName("alfred.nobel");
        newUserInfo.setEmail("alfred.nobel@gmail.com");
        newUserInfo.setPassword("ValidPassword13@");
        newUserInfo.setFirstName("Alfred");
        newUserInfo.setLastName("Nobel");

        ResponseEntity<UserInfo> response = restTemplate.postForEntity("/api/users", newUserInfo, UserInfo.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

}
