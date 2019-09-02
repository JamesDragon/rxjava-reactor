package com.java9;

import com.java9.shopping.Order;
import com.java9.shopping.OrderSubscriber;
import com.java9.shopping.Product;
import com.java9.shopping.Stock;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.*;
import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.LongBinaryOperator;

public class SpringReactiveTesting {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("com.java9");
        //订阅Bean
        Flow.Subscriber<Order> orderSubscriber = context.getBean(OrderSubscriber.class);

        Product product = new Product();
        Stock stock = context.getBean(Stock.class);
        //初始化产品和库存
        stock.store(product, 50);

        SubmissionPublisher<Order> publisher = new SubmissionPublisher<>();

        Order order = new Order();
        Order.OrderItem orderItem = new Order.OrderItem();
        orderItem.setProduct(product);
        orderItem.setAmount(10);
        order.setOrderItemList(Collections.singletonList(orderItem));
        //订阅服务
        publisher.subscribe(orderSubscriber);

        for (int i = 0; i < 10; i++) {
            //发布服务
            publisher.submit(order);
        }

        System.err.println(publisher.isSubscribed(orderSubscriber));
    }
}
