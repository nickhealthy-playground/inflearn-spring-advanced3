package hello.aop.example.service;

import org.springframework.stereotype.Service;

@Service(value = "OrderService2")
public class OrderService {
    
    public void createOrder(String itemName) {
        System.out.println("주문 생성: " + itemName);
        // 비즈니스 로직...
    }
    
    public void cancelOrder(Long orderId) {
        System.out.println("주문 취소: " + orderId);
        // 비즈니스 로직...
    }
}