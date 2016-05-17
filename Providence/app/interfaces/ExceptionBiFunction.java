package interfaces;

import common.BaseException;

/**
 * Created by Smeyan on 28.10.2015.
 */
public interface ExceptionBiFunction<T, U, R>{

    R apply(T t, U u) throws BaseException;
}
