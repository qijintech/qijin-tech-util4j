package tech.qijin.util4j.aop.util;


import tech.qijin.util4j.aop.exception.CasException;

/**
 * @author hanxiao
 */
public class CasAssert {


    /**
     * 用于cas的校验工具
     * 如果为false抛出CasException
     * @param condition
     */
    public static void isTrue(boolean condition){
        if (!condition){
            throw new CasException();
        }
    }
}
