package net.bean.java.open.messenger.interceptor;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;

public class UserIdPathVariableVerificationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if(handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            int index = index(handlerMethod.getMethod().getParameterAnnotations());
        }
        System.out.println("Hello World");
        return true;
    }

    private int index(Annotation[][] annotations) {
        for(int i=0 ; i<annotations.length ; i++) {
            for(Annotation annotation : annotations[i]) {
                if(annotation instanceof PathVariable) {
                    System.out.println(annotation);
                }
            }
        }
        return 0;
    }

}
