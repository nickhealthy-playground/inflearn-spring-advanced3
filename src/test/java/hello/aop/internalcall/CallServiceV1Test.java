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
// 스프링부트 2.6 이후부터 순환 참조를 기본적으로 금지하도록 정책이 변경되었다.
// 따라서 다음과 같이 순환 참조를 허용해야 정상 작동하게된다.
@SpringBootTest(properties = "spring.main.allow-circular-references=true")
class CallServiceV1Test {

    @Autowired
    CallServiceV1 callServiceV1;

    @Test
    void isAop() {
        log.info("callService AOP={}", callServiceV1.getClass());
        assertThat(AopUtils.isAopProxy(callServiceV1)).isTrue();
    }

    @Test
    void external() {
        callServiceV1.external();
    }

    @Test
    void internal() {
        callServiceV1.internal();
    }
}