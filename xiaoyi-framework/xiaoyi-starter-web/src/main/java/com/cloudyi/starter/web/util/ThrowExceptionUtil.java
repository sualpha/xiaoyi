package com.cloudyi.starter.web.util;

import com.cloudyi.starter.web.exception.ServiceException;


public class ThrowExceptionUtil {

    /**
     * 如果参数为 false 抛出异常
     *
     * @param result true/false
     * @return ThrowExceptionFunction
     **/
    public static ThrowExceptionFunction isFalse(boolean result) {
        return (errorMessage) -> {
            if (!result) {
                throw new ServiceException(errorMessage);
            }
        };
    }

    @FunctionalInterface
    public interface ThrowExceptionFunction {

        /**
         * 抛出异常信息
         *
         * @param message 异常信息
         **/
        void throwMessage(String message);
    }
}
