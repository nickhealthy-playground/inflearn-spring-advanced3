package hello.aop.proxyvs;

import hello.aop.member.MemberService;
import hello.aop.member.MemberServiceImpl;
import hello.aop.proxyvs.code.ProxyDIAspect;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Slf4j
// //@SpringBootTest(properties = "spring.aop.proxy-target-class=true") 설정 없이도 스프링 부트 2.0 이상부터 기본적으로 CGLIB 프록시를 사용하게 된다.
@Import(ProxyDIAspect.class)
/*
* Bean named 'memberServiceImpl' is expected to be of type 'hello.aop.member.MemberServiceImpl' but was actually of type 'jdk.proxy3.$Proxy56'
* JDK 동적 프록시, DI 예외 발생
* */
//@SpringBootTest(properties = "spring.aop.proxy-target-class=false")
//@SpringBootTest(properties = "spring.aop.proxy-target-class=true")
@SpringBootTest
public class ProxyDITest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberServiceImpl memberServiceImpl;

    @Test
    void go() {
        log.info("memberService={}", memberService.getClass());
        log.info("memberServiceImpl={}", memberServiceImpl.getClass());
        memberServiceImpl.hello("hello");
    }
}
