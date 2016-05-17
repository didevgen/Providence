package controllers;

import common.BaseException;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class BaseController extends Controller {

//    protected static Result applyAuthorized(ExceptionBiFunction<MobileUser, Language, Result> f) {
//        return applyAuthorized(f, true);
//    }

//    protected static Result applyOptionalAuthorized(ExceptionBiFunction<MobileUser, Language, Result> f) {
//        return applyAuthorized(f, false);
//    }

//    protected static Result applyAuthorized(ExceptionBiFunction<MobileUser, Language, Result> f, boolean autorizationRequired) {
//        return result(() -> {
//            // apply function
//            return f.apply(getUser(autorizationRequired), getLanguage());
//        });
//    }

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

    protected static Result apply(ExceptionFunction<Language, Result> f) {
        saveUser();
        return result(() -> f.apply(getLanguage()));
    }

    public static Language getLanguage() {
        Language l = LanguageService.getLanguage(getAcceptLanguage());
        if (l == null) {
            l = LanguageService.getLanguage(Config.DEFAULT_LANGUAGE);
        }
        return l;
    }

    private static String getAcceptLanguage() {
        String acceptLanguage = request().getHeader(Http.HeaderNames.ACCEPT_LANGUAGE);
        return StaticValidationHelper.isNullOrEmpty(acceptLanguage)
                ? Config.DEFAULT_LANGUAGE
                : acceptLanguage;
    }

    private static Optional<String> getAccountHeader() {
        String header = request().getHeader(Config.Headers.ACCOUNT);
        return StaticValidationHelper.isNullOrEmpty(header) ? Optional.<String>empty() : Optional.of(header);
    }

    /**
     * Convert list to play {@link Result}.
     * The response will contains dto.  Conversion provided in every dto separately
     * by calling {@link IConverted}.with({@code T}).
     *
     * @param list        list of entities to converse.
     * @param constructor dto constructor.
     * @param <T>
     * @param <D>         child of {@link IConverted}.
     * @return play {@link Result}.
     */
    public static <T, D extends IConverted<T>> Result ok(List<T> list, Supplier<D> constructor) {
        List<D> dtos = ConverterHelper.convert(list, constructor);
        return FavoriteResponseDecorator.ok(dtos);
    }

    public static <T, D extends IConverted<T>> Result ok(List<T> list, Supplier<D> constructor, Supplier<Long> total, long limit, long offset) {
        List<D> dtos = ConverterHelper.convert(list, constructor);
        return FavoriteResponseDecorator.ok(dtos, limit, offset, total.get());
    }

    public static <T, D extends IConverted<T>> Result ok(List<T> list, Supplier<D> constructor, Supplier<Long> total, Optional<Long> limit, Optional<Long> offset) {
        List<D> dtos = ConverterHelper.convert(list, constructor);
        // ignore total if no limit/offset
        return limit.isPresent() ? FavoriteResponseDecorator.ok(dtos, limit.get(), offset.get(), total.get())
                : FavoriteResponseDecorator.ok(dtos);
    }

    /**
     * Convert object to play {@link Result}.
     * The response will contains dto.  Conversion provided in every dto separately
     * by calling {@link IConverted}.with({@code T}).
     *
     * @param entity      entity to converse.
     * @param constructor dto constructor.
     * @param <T>
     * @param <D>         child of {@link IConverted}.
     * @return play {@link Result}.
     */
    public static <T, D extends IConverted<T>> Result ok(T entity, Supplier<D> constructor) {
        D dto = ConverterHelper.convert(entity, constructor);
        return FavoriteResponseDecorator.ok(dto);
    }

    public static <T extends UUIDEntity, D extends IConvertedDto<T>> Result ok(T entity, Supplier<D> constructor, Function<D, ? extends IDto> decorator) {
        D dto = ConverterHelper.convert(entity, constructor);
        return FavoriteResponseDecorator.ok((dto == null) ? dto
                : decorator.apply(dto));
    }

    public static <T extends UUIDEntity, D extends IConvertedDto<T>> Result ok(List<T> list, Supplier<D> constructor, Function<D, ? extends IDto> decorator) {
        if (decorator == null) {
            return ok(list, constructor);
        }
        List<IDto> result = list.stream().map(t -> decorator.apply(ConverterHelper.convert(t, constructor))).collect(Collectors.toList());
        return FavoriteResponseDecorator.ok(result);
    }

    public static <T> Result ok(Collection<T> c) {
        return FavoriteResponseDecorator.ok(c);
    }

    public static <T> Result ok(Collection<T> c, Supplier<Long> total, Optional<Long> limit, Optional<Long> offset) {
        // ignore total if no limit/offset
        return limit.isPresent() ? FavoriteResponseDecorator.ok(c, limit.get(), offset.get(), total.get())
                : FavoriteResponseDecorator.ok(c);
    }

    public static <T, D> Result ok(Collection<T> c, D parent, Supplier<Long> total, Optional<Long> limit, Optional<Long> offset) {
        // ignore total if no limit/offset
        return limit.isPresent() ? FavoriteResponseDecorator.ok(c, parent, limit.get(), offset.get(), total.get())
                : FavoriteResponseDecorator.ok(c, parent);
    }

    public static <T> Result ok(T c) {
        return FavoriteResponseDecorator.ok(c);
    }

    protected static <T extends IDto> T getRequiredDTO(Class<T> dtoTypeRef) throws BaseException {
        ServiceResult<T> serviceResult = getDTO(dtoTypeRef, null);
        return required(serviceResult);
    }

    private static <T extends IDto> T required(ServiceResult<T> serviceResult) {
        if (serviceResult.success()) {
            return required(serviceResult.getResult());
        } else {
            throw new BaseException(CommonError.INVALID_DATA);
        }
    }

    private static <T extends IDto> T required(T result) {
        if (result != null) {
            return validate(result);
        } else {
            throw new BaseException(CommonError.INVALID_DATA);
        }
    }

    private static <T extends IDto> T validate(T dto) throws BaseException {
        if (dto instanceof IValidated) {
            IError error = ((IValidated) dto).validate();
            if (error != null) {
                throw new BaseException(error);
            }
            return dto;
        }
        return dto;
    }

    protected static <T extends IDto> ServiceResult<T> getDTO(Class<T> dtoTypeRef) {
        return getDTO(dtoTypeRef, null);
    }

    protected static <T> ServiceResult<T> getDTO(Class<T> dtoTypeRef, List<Class<?>> validator) {
        Optional<T> dtoOptional = getJsonBody().map(n -> JsonHelper.fromJson(n, dtoTypeRef));

        if (!dtoOptional.isPresent())
            return ServiceResult.result(null);

        return ServiceResult.result(dtoOptional.get());
    }

    protected static <T extends IDto> ServiceResult<T> getDtoFromRequest(Class<T> dtoTypeRef) {
        return getDtoFromMap(request().queryString(), dtoTypeRef);
    }

    protected static <T extends IDto> T getDtoFromMultipart(Class<T> dtoTypeRef) {
        Http.MultipartFormData body = request().body().asMultipartFormData();
        if (body == null) {
            throw new BaseException(CommonError.FILE_REQUIRED);
        }
        Map<String, String[]> jsonBody = body.asFormUrlEncoded();
        T dto = null;
        if (!VH.isNullOrEmpty(jsonBody)) {
            Map.Entry<String, String[]> entry = jsonBody.entrySet().stream().findFirst().get();
            if (entry != null) {
                dto = JsonHelper.fromString(Util.arrayToString(entry.getValue()), dtoTypeRef);
            }
        }
        return required(dto);
    }

    protected static <T extends IDto> ServiceResult<T> getDtoFromMap(Map<String, String[]> map, Class<T> dtoTypeRef) {
        if (map == null) map = new HashMap<>();
        Map<String, String> params = new HashMap<>();
        map.entrySet().forEach(e -> params.put(e.getKey(), Util.arrayToString(e.getValue())));

        T dto = JsonHelper.fromMap(params, dtoTypeRef);

        return ServiceResult.result(dto);
    }

    protected static Optional<JsonNode> getJsonBody() {
        return Optional.of(Http.Context.current())
                .map(Http.Context::request)
                .map(Http.Request::body)
                .map(Http.RequestBody::asJson);
    }

    protected static Result result(IError restError) {
        return ResponseHelper.result(restError);
    }

    protected static void addHeader(String key, String value) {
        Http.Context.current().response().setHeader(key, value);
    }

    protected static Optional<String> header(String key) {
        return Optional.ofNullable(Http.Context.current())
                .map(Http.Context::request)
                .map(r -> r.getHeader(key));
    }

    protected static Optional<MobileUser> currentUser() {
        Optional<TokenImpl> token = tokenIdentity();
        if (!token.isPresent())
            return Optional.empty();

        MobileUser user = UserService.getUserByEmail(token.get().getUser().getLogin());
        return Optional.ofNullable(user);
    }

    protected static Optional<TokenImpl> tokenIdentity() {
        return Optional.ofNullable(request().getHeader(Config.Headers.AUTHORIZATION))
                .map(k -> TokenService.getInstance().find(k))
                .map(t -> TokenProvider.isValid(t) ? t : null);
    }

    protected static MobileUser getUser(boolean required) {
        //  check authorized user
        Optional<MobileUser> user = currentUser();
        if (required && !user.isPresent()) {
            throw new BaseException(CommonError.AUTHORIZATION_REQUIRED);
        }
        Http.Context.current().args.put(Config.VarialbleKey.USER, user.orElse(null));
        return user.orElse(null);
    }

    private static void saveUser() {
        getUser(false);
    }

    public static Result list(BiFunction<Optional<Long>, Optional<Long>, Result> function) {
        return applyLimitOffset(function);
    }

    protected static Result applyLimitOffset(BiFunction<Optional<Long>, Optional<Long>, Result> function) {
        Optional<Long> oLimit = getLimitOffset(Config.Parameters.LIMIT);
        Optional<Long> oOffset = getLimitOffset(Config.Parameters.OFFSET);
        if (oLimit.isPresent() || oOffset.isPresent()) {
            Long limit = oLimit.orElse(0L);
            Long offset = oOffset.orElse(0L);
            if (limit < 1) {
                limit = Config.MIN_LIMIT;
            }
            if (offset < 0) {
                offset = 0L;
            }
            oOffset = Optional.of(offset);
            oLimit = Optional.of(limit);
        }
        return function.apply(oLimit, oOffset);
    }

    private static Optional<Long> getLimitOffset(String name) {
        return Optional.ofNullable(request().queryString().get(name))
                .map(array -> array[0]).map(Long::valueOf);
    }

}
