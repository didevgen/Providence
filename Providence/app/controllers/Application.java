package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.login;
import views.html.main;
import views.html.panel;
import views.html.swagger;

/**
 * Created by Admin on 22.03.2016.
 */
public class Application extends Controller {
    public  Result swagger() {
        return ok(swagger.render());
    }
}
