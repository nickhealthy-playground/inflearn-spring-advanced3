package hello.aop.order.aop;

import org.aspectj.lang.annotation.Pointcut;

/**
 * 포인트컷을 공용으로 사용하는 클래스
 */
public class Pointcuts {

    // hello.aop.order 패키지와 하위 패키지
    @Pointcut("execution(* hello.aop.order..*(..))")
    public void allOrder() {}

    // 타입 패턴이 *Service
    @Pointcut("execution(* *..*Service.*(..))")
    public void allService() {}

    // allOrder && allService
    @Pointcut("allOrder() && allService()")
    public void orderAndService() {}
}
