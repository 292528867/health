package com.wonders.xlab.healthcloud.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Jeffrey on 15/7/3.
 */
public class ReflectionUtils {

    public static Object copyNotNullProperty(Object dest, Object orig) {

        Field[] origFields = orig.getClass().getDeclaredFields();
        for (Field origField : origFields) {
            origField.setAccessible(true);
            try {
                Object propertyValue = origField.get(orig);
                if (null != propertyValue) {
                    // 属性名
                    String fieldName = origField.getName();
                    // 获取属性首字母
                    String firstLetter = fieldName.substring(0, 1).toUpperCase();
                    String setMethodName = "set" + firstLetter + origField.getName().substring(1);
                    Class<?> claszz = origField.getType();
                    System.out.println("claszz.getSimpleName() = " + claszz.getSimpleName() + "origFields.size()" + origFields.length);
                    if (claszz.getSimpleName().toString().equals("Double")) {
                        Method method = dest.getClass().getMethod(setMethodName, new Class[]{double.class});
                        method.invoke(dest, propertyValue);
                    } else if (claszz.getSimpleName().toString().equals("Integer")) {
                        Method method = dest.getClass().getMethod(setMethodName, new Class[]{int.class});
                        method.invoke(dest, propertyValue);
                    } else if (claszz.getSimpleName().toString().equals("Boolean")) {
                        Method method = dest.getClass().getMethod(setMethodName, new Class[]{boolean.class});
                        method.invoke(dest, propertyValue);
                    } else {
                        Method method = dest.getClass().getMethod(setMethodName, new Class[]{claszz});
                        method.invoke(dest, propertyValue);
                    }

                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } finally {
                origField.setAccessible(false);
            }
        }
        return dest;
    }

}
