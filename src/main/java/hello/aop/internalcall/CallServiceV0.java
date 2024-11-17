package hello.aop.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 강의: 13. 프록시와 내부 호출 - 문제
 * 대상 객체(target) 내부에서 메서드 호출 발생 시 프록시를 거치지 않고 대상 객체를 직접 호출하는 문제
 * 즉, 적용되어야 할 AOP가 적용되지 않고, 대상 객체의 메서드를 직접 호출하는 것과 같은 결과를 얻게 된다.
 */
@Slf4j
@Component
public class CallServiceV0 {

    public void external() {
        log.info("call external");
        internal(); // 내부 메서드 호출(this.internal())
    }

    public void internal() {
        log.info("call internal");
    }
}
