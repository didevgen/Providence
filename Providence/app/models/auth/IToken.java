package models.auth;

import org.joda.time.DateTime;

public interface IToken {

    String getKey();

    DateTime getExpired();

    DateTime getUpdatedDate();

}
