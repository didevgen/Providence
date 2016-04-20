package controllers.auth;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import models.user.User;
import play.mvc.Controller;
import play.mvc.Result;

@Api(value = "/users", description = "Operations for authentication")
public class AuthenticationController extends Controller {
    @ApiOperation(
            value = "List all people",
            notes = "List all people using paging",
            response = User.class,
            httpMethod = "POST",
            responseContainer = "List"
    )
    public  Result loginUser() {
        return ok();
    }
}
