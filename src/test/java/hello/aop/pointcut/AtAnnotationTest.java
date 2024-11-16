package hello.aop.pointcut;

import hello.aop.member.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

/**
 * @annotation: 메서드가 주어진 어노테이션을 가지고 있는 조인 포인트를 매칭
 * - 해당 어노테이션을 가지고 있는 조인포인트에 AOP를 적용한다.
 *
 * `@annotation(hello.aop.member.annotation.MethodAop)`
 *
 * 다음과 같은 메서드(조인 포인트)에 어노테이션이 있으면 매칭한다.
 * public class MemberServiceImpl {
 *      @MethodAop("test value")
 *      public String hello(String param) {
 *          return "ok";
 *      }
 * }
 */
@Slf4j
@Import(AtAnnotationTest.AtAnnotationAspect.class)
@SpringBootTest
public class AtAnnotationTest {

    @Autowired
    MemberService memberService;

    @Test
    void success() {
        /*
        * 실행결과
        * memberService Proxy=class hello.aop.member.MemberServiceImpl$$SpringCGLIB$$0
        * [@annotation] String hello.aop.member.MemberServiceImpl.hello(String)
        * */
        log.info("memberService Proxy={}", memberService.getClass());
        memberService.hello("helloA");
    }

    @Slf4j
    @Aspect
    static class AtAnnotationAspect {

        @Around("@annotation(hello.aop.member.annotation.MethodAop)")
        public Object doAtAnnotation(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[@annotation] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }
    }



}
