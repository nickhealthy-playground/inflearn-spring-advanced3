package hello.aop.example.service;

import hello.aop.example.aop.TimeTraceAspect;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(TimeTraceAspect.class)
class OrderServiceTest {

    @Autowired
    OrderService orderService;

    @Autowired
    AnnotationConfigApplicationContext ac;

    @Test
    void success() {
        orderService.createOrder("맥북");
        orderService.cancelOrder(1L);
    }
}