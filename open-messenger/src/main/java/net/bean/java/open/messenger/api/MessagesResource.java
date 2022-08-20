package net.bean.java.open.messenger.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bean.java.open.messenger.data.dto.MessageDTO;
import net.bean.java.open.messenger.data.mapper.MessageMapper;
import net.bean.java.open.messenger.data.jpa.model.User;
import net.bean.java.open.messenger.service.MessageService;
import net.bean.java.open.messenger.service.UserService;
import net.bean.java.open.messenger.util.UserRequestUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/messages-api")
@Slf4j
public class MessagesResource {
    private final MessageService messageService;
    private final UserService userService;
    private final MessageMapper mapper;

    @GetMapping("/conversation/{userId}")
    public ResponseEntity<List<MessageDTO>> getContacts(HttpServletRequest request, @PathVariable Optional<Long> userId, Optional<Integer> page) {
        User curentUser = userService.getUser(UserRequestUtil.getUserFromHttpServletRequest(request)).get();
        User secondUser = userService.getUser(userId.orElseThrow(() -> new RuntimeException("Parameter userId cannot be null")))
                                     .orElseThrow(() -> new RuntimeException("Cannot find user by id: " + userId.orElseGet(() -> 0L)));

        List<MessageDTO> messages = messageService.getConversationBetweenUsers(curentUser.getId(), secondUser.getId(), page)
                                                  .stream()
                                                  .map(m -> mapper.mapEntityToDto(m))
                                                  .collect(Collectors.toList());
        return ResponseEntity.ok().body(messages);
    }


}
