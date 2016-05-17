package interfaces;

import common.BaseException;

/**
 * Created by Smeyan on 21.12.2015.
 */
public interface ExceptionThirdFunction<A, B, C, R> {

    R apply(A a, B b, C c) throws BaseException;
}
