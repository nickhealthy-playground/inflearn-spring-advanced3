package hello.aop.pointcut;

import hello.aop.order.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

/**
 * bean: 스프링 전용 포인트컷 지시자, 빈의 이름으로 지정한다.
 * - 스프링 빈의 이름으로 AOP 적용 여부를 지정한다.
 * - * 같은 패턴을 사용할 수 있다.
 */
@Slf4j
@Import(BeanTest.BeanAspect.class)
@SpringBootTest
public class BeanTest {

    @Autowired
    OrderService orderService;

    @Test
    void success() {
        /*
        * 실행결과
        * [bean] void hello.aop.order.OrderService.orderItem(String)
        * [orderService] 실행
        * [bean] String hello.aop.order.OrderRepository.save(String)
        * [orderRepository] 실행
        * */
        orderService.orderItem("itemA");
    }

    @Aspect
    static class BeanAspect {
        @Around("bean(orderService) || bean(*Repository)")
        public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[bean] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }
    }

}
