package interfaces;

import enums.Status;
import play.mvc.Result;

/**
 * Created by Motrechko on 26.05.2015.
 */
public interface IError {
    Result createResult();

    int getCode();

    String getDescription();

    Status getStatus();
}
