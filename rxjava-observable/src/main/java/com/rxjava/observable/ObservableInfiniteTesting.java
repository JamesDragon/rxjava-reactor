package com.rxjava.observable;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 响应式编程的设计原则：一个消费者应该只对应一个线程，RxJava虽然没有定义类似的限制
 * 但应该在设计的时候避免类似的设计
 */
public class ObservableInfiniteTesting {

    private static ExecutorService executorService = Executors.newFixedThreadPool(4);

    public static void main(String[] args) throws InterruptedException {
        rightObservableDesign();
    }

    /**
     * 正确的Observable设计
     */
    public static void rightObservableDesign() {
        List<Integer> list = new ArrayList<>(3);
        list.add(1);
        list.add(2);
        list.add(3);

        Observable.create(observableEmitter -> {
            AtomicInteger counter = new AtomicInteger(list.size());
            list.forEach(l -> {
                executorService.submit(() -> {
                    //一个线程一个消费者
                    observableEmitter.onNext("订阅者：" + l);
                    if (counter.decrementAndGet() == 0) {
                        executorService.shutdown();
                        observableEmitter.onComplete();
                    }
                });
            });
        }).subscribe(System.out::println);

        executorService.shutdownNow();
    }

    /**
     * 错误的消费者设计
     */
    public static void wrongObservableDesign() {
        List<Integer> list = new ArrayList<>(3);
        list.add(1);
        list.add(2);
        list.add(3);

        Observable.create(observableEmitter -> {
            AtomicInteger counter = new AtomicInteger(list.size());
            executorService.execute(() -> {
                list.forEach(l -> {
                    observableEmitter.onNext("订阅者：" + l);
                    if (counter.decrementAndGet() == 0) {
                        observableEmitter.onComplete();
                    }
                });

            });
        }).subscribe(System.out::println);

        executorService.shutdownNow();
    }

    /**
     * 简单的无限流test
     * @throws InterruptedException
     */
    public static void test1() throws InterruptedException {
        //无限流
        Observable<Object> observable2 = Observable.create(observableEmitter -> {
            Runnable runnable = () -> {
                BigInteger num = BigInteger.ZERO;
                //保证取消订阅后线程执行完毕(不再循环)
                while (!observableEmitter.isDisposed()) {
                    observableEmitter.onNext(num);
                    num = num.add(BigInteger.ONE);
                }
            };
            new Thread(runnable).start();
        });

        Disposable subscribe = observable2.subscribe(s -> System.out.println(s));
        Thread.sleep(1000);
        System.err.println("结束了");
        subscribe.dispose();
    }
}
