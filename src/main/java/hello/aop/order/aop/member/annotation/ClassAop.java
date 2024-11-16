package hello.aop.order.aop.member.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 강의: 11. 예제 만들기
 * 실습 목표: 커스텀한 애노테이션 기반으로 포인트컷을 적용해본다.
 */
@Target(ElementType.TYPE)           // 어노테이션 대상자 지정: ElementType.TYPE - CLASS에 붙일 수 있는 어노테이션
@Retention(RetentionPolicy.RUNTIME) // 어노테이션 적용 범위 지정: RetentionPolicy.RUNTIME - 실행 시점에도 적용되는 어노테이션
public @interface ClassAop {
}
