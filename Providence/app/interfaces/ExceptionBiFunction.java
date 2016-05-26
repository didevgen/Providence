package interfaces;

import common.BaseException;

public interface ExceptionBiFunction<T, U, R>{

    R apply(T t, U u) throws BaseException;
}
