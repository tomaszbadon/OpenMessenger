package net.bean.java.open.messenger.rest.patch;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bean.java.open.messenger.rest.exception.InternalException;
import net.bean.java.open.messenger.rest.exception.UnsupportedPatchOperation;
import net.bean.java.open.messenger.rest.resource.patch.PatchOperation;
import net.bean.java.open.messenger.rest.resource.patch.PatchOperations;
import net.bean.java.open.messenger.rest.resource.patch.PatchPath;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Slf4j
public class PatchExecutor<S> {

    private final S patchService;

    public void execute(PatchOperations patchOperations, Object... objects) {

        List<MatchedMethod> methods = patchOperations
                .getOperationList()
                .stream()
                .map(this::findMethod)
                .collect(Collectors.toList());

        Optional<UnsupportedPatchOperation> exception = methods.stream()
                                                               .filter(m -> !m.isPresent())
                                                               .map(MatchedMethod::getCause)
                                                               .findAny();

        if(exception.isPresent()) {
            throw exception.get();
        } else {
            methods.forEach(m -> execute(m, objects));
        }
    }

    private Object execute(MatchedMethod method, Object... objects) {
        try {

            return method.getMethod().get().invoke(patchService, consolidateParams(method, objects));
        } catch (IllegalArgumentException | InvocationTargetException | IllegalAccessException e) {
            log.error(e.getMessage(), e);
            throw new InternalException(e);
        }
    }

    private Object[] consolidateParams(MatchedMethod method, Object... objects) {
        List<Object> params = new ArrayList<>();
        params.add(method.getPatchOperation());
        params.addAll(Arrays.asList(objects));
        return params.toArray();
    }

    private MatchedMethod findMethod(PatchOperation patchOperation) {
        return MatchedMethod.of(patchOperation, Stream.of(patchService.getClass().getMethods())
                                      .filter(method -> findMethodPredicate(method, patchOperation))
                                      .findFirst());
    }

    private boolean findMethodPredicate(Method method, PatchOperation operation) {
        PatchPath patchPath = method.getAnnotation(PatchPath.class);
        if(patchPath != null) {
            if(StringUtils.equals(patchPath.operation(), operation.getOp())) {
                Matcher matcher = Pattern.compile(patchPath.pathPattern()).matcher(operation.getPath());
                return matcher.matches();
            }
        }
        return false;
    }

    @RequiredArgsConstructor(staticName = "of")
    private static class MatchedMethod {

        @Getter
        private final PatchOperation patchOperation;

        @Getter
        private final Optional<Method> method;

        private boolean isPresent() {
            return method.isPresent();
        }

        private UnsupportedPatchOperation getCause() {
            return new UnsupportedPatchOperation(patchOperation);
        }
    }

}
