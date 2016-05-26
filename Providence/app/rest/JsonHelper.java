package rest;

import com.fasterxml.jackson.databind.JsonNode;
import play.libs.Json;

/**
 * Created by Eugne on 26.05.2016.
 */
public class JsonHelper {

    public static JsonNode toJson(ResponseWrapper wrapper) {
        return Json.toJson(wrapper);
    }
}
