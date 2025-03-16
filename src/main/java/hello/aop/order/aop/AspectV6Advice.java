package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

/**
 * 어드바이스 종류
 * 1. @Around: 메서드 호출 전후에 수행, 조인 포인트 실행 여부 선택, 반환 값 변환, 예외 변환 등 가능
 * 2. @Before: 조인 포인트 실행 이전에 실행(joinPoint.proceed())
 * 3. @AfterReturning: 조인 포인트가 정상 완료 후 실행(joinPoint.proceed())
 * 4. @AfterThrowing: 메서드가 예외를 던지는 경우 실행(catch)
 * 5. @After: 조인 포인트가 정상 또는 예외에 관계없이 실행(finally)
 */

/**
 * 참고 정보
 * - 모든 어드바이스는 `org.aspectj.lang.JoinPoint`를 첫 번째 파라미터에 사용 가능(생략 OK)
 * - 단, `@Around`는 `org.aspectj.lang.ProceedingJoinPoint` 사용(JoinPoint 하위 타입)
 */

/**
 * 순서
 * - 스프링 5.2.7 버전부터 동일한 @Aspect 안에서 동일한 조인포인트의 우선순위를 정했다.
 * - @Around, @Before, @After, @AfterReturning, @AfterThrowing
 * - 호출 순서와 리턴 순서는 반대로 동작
 * - 동일한 종류의 어드바이스가 2개 이상 있으면 순서가 보장되지 않으므로, 애스펙트를 분리하고 @Order를 적용
 */
@Slf4j
@Aspect
public class AspectV6Advice {

    @Around("hello.aop.order.aop.Pointcuts.orderAndService()")
    public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            // @Before
            log.info("[around][트랜잭션 시작] {}", joinPoint.getSignature());
            Object result = joinPoint.proceed();
            // @AfterReturning
            log.info("[around][트랜잭션 커밋] {}", joinPoint.getSignature());
            return result;
        } catch (Exception e) {
            // AfterThrowing
            log.info("[around][트랜잭션 롤백] {}", joinPoint.getSignature());
            throw e;
        } finally {
            // @After
            log.info("[around][리소스 릴리즈] {}", joinPoint.getSignature());
        }
    }

    @Before(value = "hello.aop.order.aop.Pointcuts.orderAndService()")
    public void doBefore(JoinPoint joinPoint) {
        log.info("[before] {}", joinPoint.getSignature());
    }

    @AfterReturning(value = "hello.aop.order.aop.Pointcuts.orderAndService()", returning = "result")
    public void doReturn(JoinPoint joinPoint, Object result) {
        log.info("[return] {} return={}", joinPoint.getSignature(), result);
    }

    @AfterThrowing(value = "hello.aop.order.aop.Pointcuts.orderAndService()", throwing = "ex")
    public void doThrowing(JoinPoint joinPoint, Exception ex) {
        log.info("[ex] {} message={}", joinPoint.getSignature(), ex.getMessage());
    }

    @After(value = "hello.aop.order.aop.Pointcuts.orderAndService()")
    public void doAfter(JoinPoint joinPoint) {
        log.info("[after] {}", joinPoint.getSignature());
    }
}