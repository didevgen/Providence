package common;

import enums.Status;
import interfaces.IError;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results;
import rest.JsonHelper;

/**
 * Created by Eugne on 26.05.2016.
 */
public class CommonError implements IError {

    private int code;
    private String description;
    private Status status;

    public CommonError(Status status) {
        this.code=status.getStatusCode();
        this.description = status.getReasonPhrase();
    }

    public CommonError(int code, String description) {
        this.code = code;
        this.description = description;
    }

    @Override
    public Result createResult() {
        return Results.status(code, description);
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public Status getStatus() {
        return status;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
