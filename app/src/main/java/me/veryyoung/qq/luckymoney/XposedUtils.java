package me.veryyoung.qq.luckymoney;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static de.robv.android.xposed.XposedHelpers.getParameterTypes;


/**
 * Created by veryyoung on 2017/1/7.
 */


public class XposedUtils {


    public static Object findResultByMethodNameAndReturnTypeAndParams(Object targetObject, String methodName, String returnType, Object... params) throws InvocationTargetException, IllegalAccessException {
        return findMethodByNameAndReturnType(targetObject.getClass(), methodName, returnType, getParameterTypes(params)).invoke(targetObject, params);
    }


    public static Method findMethodByNameAndReturnType(Class<?> targetObject, String methodName, String returnType, Class<?>... params) {
        for (Method method : targetObject.getDeclaredMethods()) {
            if (method.getReturnType().getName().equals(returnType) && method.getName().equals(methodName)) {
                Class[] parameterTypes = method.getParameterTypes();
                if (params.length != parameterTypes.length) {
                    continue;
                }
                for (int i = 0; i < params.length; i++) {
                    if (params[i] != parameterTypes[i]) {
                        break;
                    }
                }
                method.setAccessible(true);
                return method;
            }
        }
        throw new NoSuchMethodError();
    }
}
