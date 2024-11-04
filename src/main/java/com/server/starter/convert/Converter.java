package com.server.starter.convert;

import org.springframework.cglib.beans.BeanCopier;

public class Converter {

    public static <S, T> T convert(S source, Class<T> targetClass) {
        try {
            T target = targetClass.getDeclaredConstructor().newInstance();
            BeanCopier copier = BeanCopier.create(source.getClass(), targetClass, false);
            copier.copy(source, target, null);
            return target;
        } catch (Exception e) {
            throw new RuntimeException("Conversion error", e);
        }
    }

    public static <S, T> T convert(S source, T target) {
        try {
            BeanCopier copier = BeanCopier.create(source.getClass(), target.getClass(), false);
            copier.copy(source, target, null);
            return target;
        } catch (Exception e) {
            throw new RuntimeException("Conversion error", e);
        }
    }
}
