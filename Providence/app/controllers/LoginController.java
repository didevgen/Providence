package controllers;

import models.User;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.login;

import static play.mvc.Controller.session;
import static play.mvc.Results.redirect;

/**
 * Controller method for manipulating user data
 */
@Transactional
public class LoginController extends Controller {

    public Result getLoginForm() {
        return ok(login.render(false));
    }

    public Result submitLoginForm()  {
        return ok();
    }

    public Result logout() {
        session().clear();
        return redirect("/");
    }
}