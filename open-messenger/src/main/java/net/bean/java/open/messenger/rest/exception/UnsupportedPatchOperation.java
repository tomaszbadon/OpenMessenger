package net.bean.java.open.messenger.rest.exception;

import net.bean.java.open.messenger.rest.resource.patch.PatchOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.text.MessageFormat;

import static net.bean.java.open.messenger.exception.ExceptionConstants.PATCH_OPERATION_UNSUPPORTED;

public class UnsupportedPatchOperation extends ResponseStatusException {

    public UnsupportedPatchOperation(PatchOperation op) {
        super(HttpStatus.BAD_REQUEST, MessageFormat.format(PATCH_OPERATION_UNSUPPORTED, op.getOp(), op.getPath()));
    }

}
