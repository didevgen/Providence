package dto.providers;

import com.fasterxml.jackson.databind.JsonNode;
import play.libs.Json;
import rest.JsonHelper;
import rest.ResponseHelper;

/**
 * Created by Eugne on 26.05.2016.
 */
public class DtoProvider<T>{

    public T getFromDto(Class<T> clazz,JsonNode json) {
        return Json.fromJson(json,clazz);
    }
}
