package interfaces;

import common.BaseException;

/**
 * Created by Smeyan on 28.10.2015.
 */
public interface ExceptionFunction<T, R> {

    R apply(T t) throws BaseException;
}
