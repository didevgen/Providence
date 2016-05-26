package interfaces;

import common.BaseException;

public interface ExceptionThirdFunction<A, B, C, R> {

    R apply(A a, B b, C c) throws BaseException;
}
