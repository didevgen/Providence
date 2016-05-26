package controllers.auth;

import controllers.BaseController;
import dto.user._User;
import dto.user._UserSignIn;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import models.user.User;
import play.db.jpa.Transactional;
import play.mvc.Result;
import utils.CryptoHelper;


@Api(value = "/users", description = "Operations for authentication")
public class AuthenticationController extends BaseController {
    @ApiOperation(
            value = "SignIn",
            httpMethod = "POST",
            response = _User.class
    )
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(
                            name = "body",
                            dataType = "dto.user._UserSignIn",
                            required = true,
                            paramType = "body",
                            value = "User"
                    )
            }
    )
    @Transactional
    public Result loginUser() {
        return apply(() -> {
            _UserSignIn user = getRequiredDto(_UserSignIn.class);
            User u = new User();
            u.setLogin(user.getLogin());
            u.setPassword(CryptoHelper.md5Password(user.getPassword()));
            return ok(u);
        });
    }


    @ApiOperation(
            value = "Log out user",
            response = User.class,
            httpMethod = "GET"
    )
    public Result logOut() {
        return ok();
    }
}
