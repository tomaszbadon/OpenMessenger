package net.bean.java.open.messenger.exception;

public class ExceptionConstants {

    public final static String INVALID_TOKEN = "The provided token has expired or it is invalid";

    public final static String CANNOT_FIND_USER_IN_REPOSITORY = "Cannot find user: \"{0}\" in the Repository";

    public final static String CANNOT_GET_USER_FROM_TOKEN = "Cannot get User from Token";

    public final static String USER_DOES_NOT_EXIST_IN_REPOSITORY = "The user: {0} does not exist in the Repository";

    public final static String PATCH_OPERATION_UNSUPPORTED = "The Patch operation: {0} is unsupported for path: {1}";

    public final static String INTERNAL_EXCEPTION = "Internal Exception occurred due to: {0}";

    public final static String CREATION_OF_THE_USER_WENT_WRONG = "The creation of the user: {0} went wrong due to exception";

    public final static String MESSAGE_NOT_FOUND = "The message with id: {0} was not found in the Repository";

    public final static String USER_DOES_NOT_HAVE_PERMISSION = "The current user does not have permission to get the resource";

    public final static String CANNOT_PROCESS_REQUEST = "The request cannot be processed due to an internal error";

}
