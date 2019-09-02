package com.java9.flow;

import java.util.concurrent.Flow;

public class MyDemoSubscriber<T> implements Flow.Subscriber<T> {
    //订阅者的名称
    private String name;
    //Publisher与Subscriber的中间协调者
    private Flow.Subscription subscription;
    //缓冲区
    final long bufferSize;

    public String getName() {
        return name;
    }

    public Flow.Subscription getSubscription() {
        return subscription;
    }

    public MyDemoSubscriber(String name, long bufferSize) {
        this.name = name;
        this.bufferSize = bufferSize;
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        (this.subscription = subscription).request(bufferSize);
        System.out.println("开始Subscriber订阅:" + name);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onNext(T item) {
        System.out.printf("### 线程：%s - name:%s - item:%s  ###\r\n", Thread.currentThread().getName(), name, item);
        System.out.println(name + " - received : " + item);
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
    }

    @Override
    public void onComplete() {
        System.out.println("我已经完成任务了，onComplete操作");
    }
}
