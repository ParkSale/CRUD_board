package com.example.demo.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER) //어노테이션을 어디에 쓸 것인가? 여기선 파라미터에 쓰겠다
@Retention(RetentionPolicy.RUNTIME) //어떤 시점까지 어노테이션이 영향을 미치게 할 것인가
public @interface LoginUser {
}
