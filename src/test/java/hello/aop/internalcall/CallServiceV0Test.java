package hello.aop.internalcall;

import hello.aop.internalcall.aop.CallLogAspect;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@Import(CallLogAspect.class)
@SpringBootTest
class CallServiceV0Test {

    @Autowired
    CallServiceV0 callServiceV0;

    @Test
    void isAop() {
        log.info("callService AOP={}", callServiceV0.getClass());
        assertThat(AopUtils.isAopProxy(callServiceV0)).isTrue();
    }

    @Test
    void external() {
        /*
        * 실행결과
        * aop = void hello.aop.internalcall.CallServiceV0.external()
        * call external
        * call internal -> 문제 발생
        * */
        callServiceV0.external();
    }

    @Test
    void internal() {
        callServiceV0.internal();
    }
}