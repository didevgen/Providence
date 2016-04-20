package controllers.landing;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.landing;

public class LandingController extends Controller {

    public  Result landing() {
        return ok(landing.render());
    }


}
