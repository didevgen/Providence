package common;

import interfaces.IError;

public class BaseException extends RuntimeException {

    private IError error;

    public BaseException(IError error) {
        this.error = error;
    }

    public BaseException(IError error, String message) {
        super(message);
        this.error = error;
    }

    public BaseException(IError error, String message, Throwable cause) {
        super(message, cause);
        this.error = error;
    }

    public BaseException(IError error, Throwable cause) {
        super(cause);
        this.error = error;
    }

    public IError getError() {
        return error;
    }

}
