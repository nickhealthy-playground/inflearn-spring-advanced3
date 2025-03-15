package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * @Aspect 사용
 */
@Slf4j
@Aspect // 해당 애노테이션은 단순히 애스펙트 표식 -> 컴포넌트 스캔 대상이 되는 것은 아님
public class AspectV1 {

    /**
     * <pre>
     * 어드바이저: 어드바이스 + 포인트컷
     * 어드바이스: doLog 메서드
     * 포인트컷: execution(* hello.aop.order..*(..))
     * </pre>
     */
    // hello.aop.order 패키지와 그 하위 패키지(..)
    @Around("execution(* hello.aop.order..*(..))")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("[log] {}", joinPoint.getSignature()); // join point 시그니처
        return joinPoint.proceed();
    }
}
