package hello.aop.proxyvs;

import hello.aop.member.MemberService;
import hello.aop.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * 강의: 프록시 기술과 한계 - 타입 캐스팅
 * JDK 동적 프록시 한계 - 인터페이스 기반으로 프록시를 생성하는 JDK 동적 프록시는 구체 클래스로 타입 캐스팅이 불가능한 한계가 있다.
 */
@Slf4j
public class ProxyCastingTest {

    @Test
    void jdkProxy() {
        MemberServiceImpl target = new MemberServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.setProxyTargetClass(false);    // JDK 동적 프록시

        // 프록시를 인터페이스로 캐스팅 성공
        MemberService proxy = (MemberService) proxyFactory.getProxy();
        log.info("proxy class={}", proxy.getClass());

        /**
         * JDK 동적 프록시를 구현 클래스로 캐스팅 시도 실패, ClassCastException 예외 발생
         * - JDK 동적 프록시는 인터페이스를 기반으로 프록시를 상속해서 만들기 때문에 구체 클래스는 전혀 모른다.
         * - 따라서 구체 클래스로 타입 캐스팅을 시도할 때 에러가 발생한다.
         */
        assertThrows(ClassCastException.class, () -> {
            MemberServiceImpl castingMemberService = (MemberServiceImpl) proxy;
        });
    }

    @Test
    void cglibProxy() {
        MemberServiceImpl target = new MemberServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.setProxyTargetClass(true);    // CGLIB 동적 프록시

        // 프록시를 인터페이스로 캐스팅 성공
        MemberService proxy = (MemberService) proxyFactory.getProxy();
        log.info("proxy class={}", proxy.getClass());

        /**
         * CGLIB 프록시를 구현 클래스로 캐스팅 시도 성공
         * CGLIB 프록시는 구체 클래스를 기반으로 프록시를 생성한다.
         * 따라서 CGLIB 프록시는 구체 클래스로 캐스팅 하는 것은 물론이고, 부모 클래스인 인터페이스로도 캐스팅이 가능한 것이다.
         */
        MemberServiceImpl castingMemberService = (MemberServiceImpl) proxy;
    }

}
