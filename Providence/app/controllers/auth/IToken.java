package controllers.auth;

import java.util.Date;

/**
 * Created by Smeyan on 04.12.2015.
 */
public interface IToken {

    String getKey();

    Date getExpired();

    Date getUpdatedDate();

}
