package controllers.auth;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import models.user.User;
import play.db.DB;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;


@Api(value = "/users", description = "Operations for authentication")
public class AuthenticationController extends Controller {
    @ApiOperation(
            value = "List all people",
            notes = "List all people using paging",
            response = User.class,
            httpMethod = "GET",
            responseContainer = "List"
    )
    @Transactional
    public  Result loginUser() {
        User user = new User();
        user.setLogin("login");
        user.setPassword("password");
        JPA.em().persist(user);
        return ok();
    }
}
