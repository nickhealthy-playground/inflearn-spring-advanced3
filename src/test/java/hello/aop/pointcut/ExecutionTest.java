package hello.aop.pointcut;

import hello.aop.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.*;

@Slf4j
public class ExecutionTest {

    /*
     * 포인트컷 표현식을 처리해주는 클래스
     * 여기에 포인트컷 표현식을 지정하면 된다.
     * */
    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
    Method helloMethod;

    @BeforeEach
    public void init() throws NoSuchMethodException {
        helloMethod = MemberServiceImpl.class.getMethod("hello", String.class);
    }

    @Test
    void printMethod() {
        // 포인트컷 지시자(Pointcut Designator)의 사용법이 아래와 같은 형식을 따른다.
        // public java.lang.String hello.aop.member.MemberServiceImpl.hello(java.lang.String)
        log.info("helloMethod = {}", helloMethod);
    }

    @Test
    @DisplayName("메서드와 정확하게 모든 내용이 매칭되는 표현식")
    void exactMatch() {
        // public java.lang.String hello.aop.member.MemberServiceImpl.hello(java.lang.String)
        pointcut.setExpression("execution(public String hello.aop.member.MemberServiceImpl.hello(String))");
        // pointcut.matchs(메서드, 대상 클래스)를 실행하면 지정한 포인트컷 표현식의 매칭 여부를 알 수 있다.
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("가장 많이 생략한 포인트컷")
    void allMatch() {
        /*
         * 접근제어자: 생략
         * 반환타입: *
         * 선언타입(패키지): 생략
         * 메서드이름: *
         * 파라미터: (..)
         * 예외: 없음
         * */
        pointcut.setExpression("execution(* *(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    /**
     * 메서드 매칭 관련 포인트컷
     */
    @Test
    @DisplayName("메서드 이름 매칭")
    void nameMatch() {
        /*
        * 반환타입: *
        * 메서드 이름: hello
        * 파라미터: 아무거나(0..*)
        * */
        pointcut.setExpression("execution(* hello(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("메서드 이름 패턴 매칭1")
    void nameMatchStar1() {
        /*
         * 반환타입: *
         * 메서드 이름: hel*
         * 파라미터: 아무거나(0..*)
         * */
        pointcut.setExpression("execution(* hel*(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("메서드 이름 패턴 매칭2")
    void nameMatchStar2() {
        /*
         * 반환타입: *
         * 메서드 이름: *el*
         * 파라미터: 아무거나(0..*)
         * */
        pointcut.setExpression("execution(* *el*(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("메서드 이름 패턴 매칭 실패")
    void nameMatchFalse() {
        pointcut.setExpression("execution(* nono(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
    }


    /**
     * 패키징 매칭 관련 포인트컷
     * 1. `hello.aop.member.*(1).*(2)`
     *  - (1): 타입
     *  - (2): 메서드 이름
     *
     * 2. 패키지에서 `.` , `..`
     *  - `.` : 정확하게 해당 위치의 패키지
     *  - `..` : 해당 위치의 패키지와 그 하위 패키지도 포함
     */
    @Test
    @DisplayName("패키지, 메서드 전체 매칭")
    void packageExactMatch1() {
        pointcut.setExpression("execution(* hello.aop.member.MemberServiceImpl.hello(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("패키지 전체 매칭")
    void packageExactMatch2() {
        pointcut.setExpression("execution(* hello.aop.member.*.*(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("패키지 매칭 실패")
    void packageExactMatchFalse() {
        // hello.aop.*는 hello.aop 패키지 안에 있는 클래스들을 매칭한다는 뜻이다.
        // 하위패키지까지를 의미하는 것이 아님
        pointcut.setExpression("execution(* hello.aop.*.*(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
    }

    @Test
    @DisplayName("하위 패키지까지 매칭1")
    void packageMatchSubPackcage1() {
        // .. 표현식이 하위 패키지까지 매칭한다는 뜻이다.
        // 즉 아래 표현식은 helo.aop.member 하위 패키지에 있는 클래스와 메서드 전부를 매칭시키는 표현식이다.
        pointcut.setExpression("execution(* hello.aop.member..*.*(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("하위 패키지까지 매칭2")
    void packageMatchSubPackcage2() {
        // 즉 아래 표현식은 helo.aop 하위 패키지에 있는 클래스와 메서드 전부를 매칭시키는 표현식이다.
        pointcut.setExpression("execution(* hello.aop..*.*(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }
}
