package com.sekarre.springdataandhibernatecourseinterceptors.config;

import com.sekarre.springdataandhibernatecourseinterceptors.domain.CreditCard;
import com.sekarre.springdataandhibernatecourseinterceptors.interceptors.EncryptionInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Constructor;
import java.util.Map;

@RequiredArgsConstructor
//@Configuration
public class InterceptorRegistration implements HibernatePropertiesCustomizer {

//    private final EncryptionInterceptor encryptionInterceptor;

    @Override
    public void customize(Map<String, Object> hibernateProperties) {
//        hibernateProperties.put("hibernate.session_factory.interceptor", encryptionInterceptor);
    }
}
