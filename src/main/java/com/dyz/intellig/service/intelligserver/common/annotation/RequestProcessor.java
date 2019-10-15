package com.dyz.intellig.service.intelligserver.common.annotation;

import org.springframework.stereotype.Service;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by daiyongzhi on 2019/10/14.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Service
public @interface RequestProcessor {
    int msgCode();
}
