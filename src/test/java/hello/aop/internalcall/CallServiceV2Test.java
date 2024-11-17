package hello.aop.internalcall;

import hello.aop.internalcall.aop.CallLogAspect;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@Import(CallLogAspect.class)
@SpringBootTest
class CallServiceV2Test {

    @Autowired
    CallServiceV2 callServiceV2;

    @Test
    void isAop() {
        log.info("callService AOP={}", callServiceV2.getClass());
        assertThat(AopUtils.isAopProxy(callServiceV2)).isTrue();
    }

    @Test
    void external() {
        callServiceV2.external();
    }

    @Test
    void internal() {
        callServiceV2.internal();
    }
}