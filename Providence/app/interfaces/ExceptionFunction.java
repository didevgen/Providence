package interfaces;

import common.BaseException;

public interface ExceptionFunction<T, R> {

    R apply(T t) throws BaseException;
}
