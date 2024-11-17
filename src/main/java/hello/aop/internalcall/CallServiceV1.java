package hello.aop.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 강의: 13. 프록시와 내부 호출 - 대안1 자기 자신 주입
 * 자기 자신을 의존관계를 주입 받아 해결한다.
 * 스프링 빈에는 프록시 객체가 등록되어 있으므로 자기 자신을 주입받아 호출하면 프록시 객체를 호출하는 것과 같은 효과를 낼 수 있다.
 */
@Slf4j
@Component
public class CallServiceV1 {

    private CallServiceV1 callServiceV1;

    /*
    * 자기 자신을 주입받는다.
    * 참고: 생성자 주입은 순환 사이클을 만들기 때문에 실패한다.
    * */
    @Autowired
    public void setCallServiceV1(CallServiceV1 callServiceV1) {
        this.callServiceV1 = callServiceV1;
    }

    public void external() {
        log.info("call external");
        internal(); // 내부 메서드 호출(this.internal())
    }

    public void internal() {
        log.info("call internal");
    }
}
