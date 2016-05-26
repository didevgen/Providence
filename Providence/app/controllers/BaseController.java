package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import common.BaseException;
import common.CommonError;
import enums.Status;
import interfaces.ExceptionFunction;
import interfaces.IError;
import models.user.User;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import rest.ResponseHelper;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class BaseController extends Controller {

    protected static Result applyAuthorized(ExceptionFunction<User, Result> f) {
        return result(() -> {
            return f.apply(new User());
        });
    }

    private static Result result(Supplier<Result> supplier) {
        Result result;
        try {
            result = supplier.get();
        } catch (BaseException e) {
            result = e.getError().createResult();
        } catch (Throwable e) {
            result = ResponseHelper.internalServerError(e);
        }
        return result;
    }

    protected static Result apply(Supplier<Result> result) {
        return result(result);
    }

    protected static<T> Result ok(T object) {
        return ok(Json.toJson(object));
    }

    protected static<T> Result ok (Collection<T> collection) {
        JsonNode node = Json.toJson(collection);
        return ok(node);
    }

    protected static<T> T getRequiredDto(Class<T> instance) {
        try {
            return Json.fromJson(request().body().asJson(), instance);
        } catch (RuntimeException ex) {
            throw new BaseException(new CommonError(400,"Bad request"));
        }
    }

}
