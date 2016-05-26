package rest;

import interfaces.IError;
import play.Logger;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;

import java.util.Collection;

public class ResponseHelper {

    private static final String DEFAULT_USER_MESSAGE = "Unhandled  exception.";

    public static Result internalServerError(Throwable e) {
        return internalServerError(e, e.getMessage());
    }

    public static Result internalServerError(Throwable e, String message) {
        Logger.error("Throwable response", e);

        String developerInfo = null;
        String[] values = Http.Context.current().request().headers().get("User-Agent");
        if (values != null && values.length == 1 &&
                (values[0].equalsIgnoreCase("true") || values[0].equalsIgnoreCase("1"))) {
            developerInfo = ErrorMessageHelper.getExceptionDetails(e);
        }

        MetadataWrapper metadata = new MetadataWrapper(
                Http.Status.INTERNAL_SERVER_ERROR,
                Http.Status.INTERNAL_SERVER_ERROR,
                message,
                developerInfo);

        return getResult(new ResponseWrapper(metadata));
    }

    public static Result internalServerError(String message) {
        MetadataWrapper metadata = new MetadataWrapper(
                Http.Status.INTERNAL_SERVER_ERROR,
                Http.Status.INTERNAL_SERVER_ERROR, DEFAULT_USER_MESSAGE, message);

        return getResult(new ResponseWrapper(metadata));
    }

    public static Result ok() {
        return status(Http.Status.OK, "ok");
    }

    public static Result ok(Object content) {
        return status(Http.Status.OK, content, Http.Status.OK, null);
    }

    public static Result ok(Collection content) {
        return status(Http.Status.OK, new ListResponseWrapper(content), Http.Status.OK, null);
    }

    public static Result ok(Collection content, Long limit, Long offset, Long totalCount) {
        return status(Http.Status.OK, new ListResponseWrapper(content, limit, offset, totalCount), Http.Status.OK, null);
    }

    public static<T, D> Result ok(Collection content, D parent, Long limit, Long offset, Long totalCount) {
        return status(Http.Status.OK, new ParentedListResponseWrapper<T, D>(content, parent, limit, offset, totalCount), Http.Status.OK, null);
    }

    public static <T, D> Result ok(Collection<T> content, D parent) {
        return status(Http.Status.OK, new ParentedListResponseWrapper<T, D>(content, parent), Http.Status.OK, null);
    }

    public static Result notFound() {
        return status(Http.Status.NOT_FOUND, "Entity not found");
    }

    public static Result forbidden() {
        return status(Http.Status.FORBIDDEN, "Access denied");
    }

    public static Result unauthorized() {
        return status(Http.Status.UNAUTHORIZED, "Unauthorized");
    }

    public static Result noContent() {
        return status(Http.Status.NO_CONTENT, "No content");
    }

    public static Result badRequest(String errorMessage) {
        MetadataWrapper metadata = new MetadataWrapper(
                Http.Status.BAD_REQUEST,
                Http.Status.BAD_REQUEST, errorMessage);

        return getResult(new ResponseWrapper(metadata));
    }

    public static Result status(int statusCode, String message) {
        MetadataWrapper metadata = new MetadataWrapper(statusCode, statusCode, message);
        return getResult(new ResponseWrapper(metadata));
    }

    public static Result status(int statusCode, Integer subStatus, String message) {
        MetadataWrapper metadata = new MetadataWrapper(statusCode, subStatus, message);
        return getResult(new ResponseWrapper(metadata));
    }

    public static Result result(IError restError) {
        return restError.createResult();
    }

    public static Result status(int statusCode, Object content, Integer subStatus, String errorMessage) {
        MetadataWrapper metadata = new MetadataWrapper(statusCode, subStatus, errorMessage);
        return getResult(new ResponseWrapper(metadata, content));
    }

    protected static Result getResult(ResponseWrapper response) {
        try {
            return Results.status(response.getMetadata().getCode(), JsonHelper.toJson(response));
        } catch (Exception e) {
            return internalServerError(e);
        }
    }
}
