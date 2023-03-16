package net.bean.java.open.messenger.rest.exception;

public class ExceptionConstants {

    public final static String INVALID_TOKEN = "The provided token has expired or it is invalid";

    public final static String CANNOT_FIND_USER_IN_REPOSITORY = "Cannot find user: \"{0}\" in Repository";

    public final static String CANNOT_GET_USER_FROM_TOKEN = "Cannot get User from Token";

    public final static String USER_DOES_NOT_EXIST_IN_REPOSITORY = "The user: {0} does not exist in Repository";

    public final static String PATCH_OPERATION_UNSUPPORTED = "The Patch operation: {0} is unsupported for path: {1}";

    public final static String INTERNAL_EXCEPTION = "Internal Exception occurred due to: {0}";

}
