package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.login;
import views.html.main;
import views.html.panel;
import views.html.swagger;

public class Application extends Controller {
    public  Result swagger() {
        return ok(swagger.render());
    }
}
