package net.leanix.springtest.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TestAspect {

    public TestAspect() {
        System.out.println("Initializing Aspect!");
    }

    @Before("execution(* net.leanix.springtest.rest.controller.TestController.sayHello2(*))")
    public void printBefore() {
        System.out.println("Before Advice!");
    }
}
