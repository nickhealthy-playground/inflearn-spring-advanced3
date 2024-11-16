package hello.aop.pointcut;

import hello.aop.member.MemberService;
import hello.aop.member.annotation.ClassAop;
import hello.aop.member.annotation.MethodAop;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

/**
 * 강의: 매개변수 전달
 * 다음은 포인트컷 표현식을 사용해서 어드바이스에 매개변수를 전달할 수 있다.
 * - this, target, args, @target, @within, @annotation, @args
 */
@Slf4j
@Import(ParameterTest.ParameterAspect.class)
@SpringBootTest
public class ParameterTest {

    @Autowired
    MemberService memberService;

    @Test
    void success() {
        log.info("memberService Proxy={}", memberService.getClass());
        memberService.hello("helloA");
    }

    @Slf4j
    @Aspect
    static class ParameterAspect {

        @Pointcut("execution(* hello.aop.member..*.*(..))")
        private void allMember() {
        }

        @Around("allMember()")
        // joinPoint.getArgs()[0]와 같이 매개변수를 전달 받는다.
        public Object logArgs1(ProceedingJoinPoint joinPoint) throws Throwable {
            Object args1 = joinPoint.getArgs()[0];
            log.info("[logArgs1]{}, args={}", joinPoint.getSignature(), args1);
            return joinPoint.proceed();
        }

        @Around("allMember() && args(arg, ..))")
        // args(arg,..)와 같이 매개변수를 전달 받는다.
        public Object logArgs2(ProceedingJoinPoint joinPoint, Object arg) throws Throwable {
            log.info("[logArgs2]{}, arg={}", joinPoint.getSignature(), arg);
            return joinPoint.proceed();
        }

        @Before("allMember() && args(arg, ..))")
        // @Before를 사용한 축약 버전이다. 추가로 타입을 String으로 제한했다.
        public void logArgs3(String arg) {
            log.info("[logArgs3] arg={}", arg);
        }

        @Before("allMember() && this(obj))")
        // 프록시 객체를 전달 받는다.
        public void thisArgs(JoinPoint joinPoint, MemberService obj) {
            log.info("[thisArgs]{}, obj={}", joinPoint.getSignature(), obj.getClass());
        }

        @Before("allMember() && target(obj))")
        // 실제 객체를 전달 받는다.
        public void targetArgs(JoinPoint joinPoint, MemberService obj) {
            log.info("[targetArgs]{}, obj={}", joinPoint.getSignature(), obj.getClass());
        }

        @Before("allMember() && @target(annotation))")
        // 타입의 애노테이션을 전달 받는다.
        public void atTarget(JoinPoint joinPoint, ClassAop annotation) {
            log.info("[@target]{}, obj={}", joinPoint.getSignature(), annotation);
        }

        @Before("allMember() && @within(annotation))")
        // 타입의 애노테이션을 전달 받는다.
        public void atWithin(JoinPoint joinPoint, ClassAop annotation) {
            log.info("[@within]{}, obj={}", joinPoint.getSignature(), annotation);
        }

        @Before("allMember() && @annotation(annotation))")
        // 메서드의 애노테이션을 전달 받는다. 여기서는 `annotation.value()` 로 해당 애노테이션의 값을 출력하는 모습을 확인할 수 있다.
        public void atAnnotation(JoinPoint joinPoint, MethodAop annotation) {
            log.info("[@annotation]{}, obj={}", joinPoint.getSignature(), annotation.value());
        }
    }
}
