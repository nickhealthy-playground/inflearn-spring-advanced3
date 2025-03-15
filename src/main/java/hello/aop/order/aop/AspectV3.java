package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * 어드바이스 추가
 * - 로그 출력 기능 + 트랜잭션 적용 코드(학습용으로 로깅으로 대체)
 */
@Slf4j
@Aspect
public class AspectV3 {

    @Pointcut("execution(* hello.aop.order..*(..))")
    private void allOrder() {} // pointcut signature

    // 클래스 이름 패턴이 *Service(클래스, 인터페이스 모두 적용됨)
    @Pointcut("execution(* *..*Service.*(..))")
    private void allService() {}

    @Around("allOrder()")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("[log] {}", joinPoint.getSignature()); // join point 시그니처
        return joinPoint.proceed();
    }

    /**
     * <pre>
     * 포인트 컷의 조합
     * - &&(AND), ||(OR), !(NOT) 표현 가능
     *
     * hello.aop.order 패키지와 하위 패키지이면서, 클래스 이름 패턴이 *Service
     * - 결과적으로 hello.aop.order.OrderService 클래스만 해당 메서드 AOP가 적용됨
     * </pre>
     */
    @Around("allOrder() && allService()")
    public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            log.info("[트랜잭션 시작] {}", joinPoint.getSignature());
            Object result = joinPoint.proceed();
            log.info("[트랜잭션 커밋] {}", joinPoint.getSignature());
            return result;
        } catch (Exception e) {
            log.info("[트랜잭션 롤백] {}", joinPoint.getSignature());
            throw e;
        } finally {
            log.info("[리소스 릴리즈] {}", joinPoint.getSignature());
        }
    }
}
