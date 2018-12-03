package com.roger.core.utils;

import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

public class DateFieldReflectUtil {

    /**
     * 设置Date类型字段值
     *
     * @param target the target object from which to get the field
     * @param field  the field to set
     * @param value  java.util.Date
     */
    public static void setFieldDateValue(Object target, Field field, Object value) {

        if (!java.util.Date.class.isAssignableFrom(field.getType())) {
            throw new RuntimeException(target.getClass().getName() + "." + field.getName()
                    + " : field type is not Date, can not convertToDate");
        }

        if (!field.isAccessible()) {
            ReflectionUtils.makeAccessible(field);
        }
        try {
            if (value == null) {
                field.set(target, null);
                return;
            }

            if (!(value instanceof java.util.Date)) {
                throw new RuntimeException(value + " : is not Date type value , can not convertToDate to field "
                        + target.getClass().getName() + "." + field.getName());
            }

            if (field.getType().equals(java.sql.Date.class)) {
                field.set(target, DateUtil.toSQLDate((java.util.Date) value));
                return;
            }

            if (field.getType().equals(java.sql.Timestamp.class)) {
                field.set(target, DateUtil.toTimestamp((java.util.Date) value));
                return;
            }

            field.set(target, value);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("field can't set value to class " + target.getClass().getName());
        }
    }
}
