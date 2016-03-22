package controllers;

import models.User;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.login;
import views.html.main;
import views.html.panel;

/**
 * Created by Admin on 22.03.2016.
 */
public class Application extends Controller {
    public Result main(String any) {
        return ok(main.render());
    }

    public Result index() {
        User user = new User();
        user.setLogin("didevgen");
        user.setPassword("password");
        return ok(login.render());
    }

    public Result application() {
        return ok(panel.render());
    }
}
