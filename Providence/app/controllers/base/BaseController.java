package controllers.base;

import models.user.User;
import play.mvc.Controller;
import utils.Headers;

import java.util.function.Function;

public class BaseController extends Controller{

    public void applyAuthorized() {
        String key = request().headers().get(Headers.AUTH_HEADER)[0];
    }
}
