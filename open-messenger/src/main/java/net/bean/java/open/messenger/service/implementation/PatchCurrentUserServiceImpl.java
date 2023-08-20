package net.bean.java.open.messenger.service.implementation;

import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import net.bean.java.open.messenger.model.User;
import net.bean.java.open.messenger.rest.model.user.UserInfo;
import net.bean.java.open.messenger.rest.patch.PatchExecutor;
import net.bean.java.open.messenger.rest.resource.patch.PatchOperation;
import net.bean.java.open.messenger.rest.resource.patch.PatchOperations;
import net.bean.java.open.messenger.rest.resource.patch.PatchPath;
import net.bean.java.open.messenger.service.UserService;
import net.bean.java.open.messenger.util.HttpServletRequestUtil;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class PatchCurrentUserServiceImpl {

    private final UserService userService;
    private final CurrentUserServiceImpl currentUserService;

    public Try<UserInfo> updateUser(HttpServletRequest httpServletRequest, PatchOperations patchOperations) {
        Try<User> user = currentUserService.tryToGetUserFromToken(HttpServletRequestUtil.getToken(httpServletRequest));
        final PatchExecutor<PatchCurrentUserServiceImpl> patchExecutor = new PatchExecutor<>(this);
        patchExecutor.execute(patchOperations, user);
        return currentUserService.tryToGetUserInfoFromToken(httpServletRequest);
    }

    @PatchPath(operation = "replace", pathPattern = "/password")
    public void changePassword(PatchOperation operation, Try<User> user) {
        String password = operation.getValue();
        userService.changePassword(user.get().getId(), password);
    }

}
