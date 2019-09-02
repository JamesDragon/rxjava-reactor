package com.java9.flow;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Flow;
import java.util.concurrent.Future;

public class MyDemoPublisher<T> implements Flow.Publisher<T>, AutoCloseable {

    private final ExecutorService executorService;
    private CopyOnWriteArrayList<MyDemoSubscription> subscriptionList = new CopyOnWriteArrayList<>();

    public MyDemoPublisher(ExecutorService executorService) {
        this.executorService = executorService;
    }

    //迭代元素
    public void submit(T item) {
        subscriptionList.forEach(s -> {
            s.future = executorService.submit(() -> {
                s.subscriber.onNext(item);
            });
        });
    }

    //元素消费成功
    public void close() {
        subscriptionList.forEach(s -> {
            s.future = executorService.submit(() -> s.subscriber.onComplete());
        });
    }

    @Override
    public void subscribe(Flow.Subscriber<? super T> subscriber) {
        subscriber.onSubscribe(new MyDemoSubscription<>(subscriber, executorService));
        subscriptionList.add(new MyDemoSubscription<>(subscriber, executorService));
    }

    static class MyDemoSubscription<T> implements Flow.Subscription {

        private final Flow.Subscriber<? super T> subscriber;
        private final ExecutorService executorService;
        private Future<?> future;
        private T item;
        private boolean complete;

        public MyDemoSubscription(Flow.Subscriber<? super T> subscriber, ExecutorService executorService) {
            this.subscriber = subscriber;
            this.executorService = executorService;
        }

        @Override
        public void request(long n) {
            if (n != 0 && !complete) {
                if (n < 0) {
                    //抛出异常
                    executorService.execute(() -> subscriber.onError(new IllegalStateException()));
                } else {
                    //发送元素
                    future = executorService.submit(() -> subscriber.onNext(item));
                }
            } else {
                subscriber.onComplete();
            }
        }

        @Override
        public void cancel() {
            complete = true;
            if (future != null && future.isCancelled()) {
                //直接中断正在执行的任务
                future.cancel(true);
            }
        }
    }
}
