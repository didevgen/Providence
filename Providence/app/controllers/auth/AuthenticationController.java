package controllers.auth;

import controllers.BaseController;
import database.DB;
import dto.user._User;
import dto.user._UserSignIn;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import models.university.Faculty;
import models.university.University;
import models.user.Token;
import models.user.User;
import org.joda.time.DateTime;
import play.db.jpa.Transactional;
import play.mvc.Http;
import play.mvc.Result;
import utils.CryptoHelper;
import utils.Headers;

import java.util.Arrays;
import java.util.Date;


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
            Token token = new Token();
            token.setExpired(new Date(System.currentTimeMillis()));
            token.setAuthKey(CryptoHelper.getDefaultToken());
            u.getTokens().add(token);
            DB.save(u);
            Http.Context.current().response().setHeader(Headers.AUTH_HEADER, token.getAuthKey());
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
