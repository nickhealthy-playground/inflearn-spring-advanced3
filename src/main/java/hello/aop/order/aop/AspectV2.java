package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * 포인트컷 분리
 * - 아래와 같은 방식을 사용하면 '재사용성'이 좋아진다.
 */
@Slf4j
@Aspect // 해당 애노테이션은 단순히 애스펙트 표식 -> 컴포넌트 스캔 대상이 되는 것은 아님
public class AspectV2 {



    /**
     * <pre>
     * 경로: execution(* hello.aop.order..*(..))
     * - hello.aop.order 패키지와 그 하위 패키지(..)
     * - 'pointcut expression'
     *
     * 메서드 이름과 파라미터를 합쳐서 포인트컷 시그니처(pointcut signature)라고 한다.
     *
     * 주의사항
     * - 메서드 반환 타입은 <u>void</u>로 설정
     * - 접근 제어자는 내부에서만 사용 시 private, 그 외 public 설정
     * </pre>
     */
    @Pointcut("execution(* hello.aop.order..*(..))")
    private void allOrder() {} // pointcut signature

    @Around("allOrder()")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("[log] {}", joinPoint.getSignature()); // join point 시그니처
        return joinPoint.proceed();
    }
}
