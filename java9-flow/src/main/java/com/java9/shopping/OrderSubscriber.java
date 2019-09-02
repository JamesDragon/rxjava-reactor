package com.java9.shopping;

import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Flow;
import java.util.concurrent.ForkJoinPool;

@Component
public class OrderSubscriber implements Flow.Subscriber<Order> {

    private ExecutorService executorService = ForkJoinPool.commonPool();
    private Flow.Subscription subscription;
    private Stock stock;

    public OrderSubscriber(Stock stock) {
        this.stock = stock;
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        System.out.println("**** 调用subscription ****");
        subscription.request(3);
        this.subscription = subscription;
    }

    @Override
    public void onNext(Order item) {
        executorService.execute(() -> {
            item.getOrderItemList().forEach(orderItem -> {
                try {
                    stock.remove(orderItem.getProduct(), orderItem.getAmount());
                    System.out.println("扣除库存：" + orderItem.getAmount());
                } catch (IllegalAccessException e) {
                    System.err.println("商品库存不足");
                }
            });

            subscription.request(1);
        });
    }

    @Override
    public void onError(Throwable throwable) {
        System.out.println("异常啦");
    }

    @Override
    public void onComplete() {
        System.out.println("结束任务");
    }
}
