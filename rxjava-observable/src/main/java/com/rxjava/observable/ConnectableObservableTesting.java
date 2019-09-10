package com.rxjava.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.observables.ConnectableObservable;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.atomic.AtomicInteger;

public class ConnectableObservableTesting {
    public static void main(String[] args) throws InterruptedException {

        ExecutorService executorService = ForkJoinPool.commonPool();

        Observable<Integer> publish = Observable.<Integer>create(emitter -> {
            AtomicInteger counter = new AtomicInteger(0);
            Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10).forEach(num -> {
                executorService.submit(() -> {
                    emitter.onNext(num);
                    if (counter.incrementAndGet() > 10) {
                        executorService.shutdownNow();
                        emitter.onComplete();
                    }
                });
            });
        }).share();

        publish.subscribe(a -> {
            System.out.println("一：" + a);
            Thread.sleep(1000);
        }, Throwable::printStackTrace, () -> System.out.println("一完成了"));
        Thread.sleep(1000);
        publish.subscribe(a -> {
            System.out.println("壹：" + a);
            Thread.sleep(1000);
        }, Throwable::printStackTrace, () -> System.out.println("二完成了"));

        Thread.sleep(15000);

//        connectableObservable();

//        observableCache();
    }

    /**
     * {@link Observable#share()}封装了Observable.publish().refCount()的方法
     */
    static void share() throws InterruptedException {
        ExecutorService executorService = ForkJoinPool.commonPool();

        Observable<Integer> publish = Observable.<Integer>create(emitter -> {
            AtomicInteger counter = new AtomicInteger(0);
            Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10).forEach(num -> {
                //使订阅者异步执行
                executorService.submit(() -> {
                    //异步消费值
                    emitter.onNext(num);
                    if (counter.incrementAndGet() > 10) {
                        executorService.shutdownNow();
                        emitter.onComplete();
                    }
                });
            });
        }).share();

        publish.subscribe(a -> {
            System.out.println("一：" + a);
            Thread.sleep(1000);
        }, Throwable::printStackTrace, () -> System.out.println("一完成了"));
        Thread.sleep(1000);
        publish.subscribe(a -> {
            System.out.println("壹：" + a);
            Thread.sleep(1000);
        }, Throwable::printStackTrace, () -> System.out.println("壹完成了"));

        Thread.sleep(15000);
    }

    /**
     * 适用于消费途中再次增加消费者的场景，不过publish不会存储已经消费的元素
     */
    static void refCount() throws InterruptedException {
        ExecutorService executorService = ForkJoinPool.commonPool();

        Observable<Integer> publish = Observable.<Integer>create(emitter -> {
            AtomicInteger counter = new AtomicInteger(0);
            Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10).forEach(num -> {
                executorService.submit(() -> {
                    emitter.onNext(num);
                    if (counter.incrementAndGet() > 10) {
                        executorService.shutdownNow();
                        emitter.onComplete();
                    }
                });
            });
        }).publish().refCount();

        publish.subscribe(a -> {
            System.out.println("一：" + a);
            Thread.sleep(1000);
        }, Throwable::printStackTrace, () -> System.out.println("一完成了"));
        Thread.sleep(1000);
        publish.subscribe(a -> {
            System.out.println("壹：" + a);
            Thread.sleep(1000);
        }, Throwable::printStackTrace, () -> System.out.println("二完成了"));

        Thread.sleep(15000);
    }

    /**
     *
     */
    static void connectableObservable() {
        ConnectableObservable<Object> publish = Observable.create(emitter -> {
            BigInteger counter = BigInteger.ZERO;
            while (true) {
                counter = counter.add(BigInteger.ONE);
                emitter.onNext(counter);
            }
        }).publish();

        publish.subscribe(a -> System.out.println(a));
        publish.connect();
    }

    /**
     * 无限流情况下cache方式，可导致OOM
     * 缺点：保存数据，占用内存
     */
    static void observableCache() throws InterruptedException {
        Observable<Object> observable = Observable.create(emitter -> {
            BigInteger counter = BigInteger.ZERO;
            while (true) {
                Thread.sleep(500);
                counter = counter.add(BigInteger.ONE);
                emitter.onNext(counter);
            }
        }).cache();

        Disposable subscribe = observable.subscribe(a -> System.out.println("admin：" + a));
        Disposable subscribe1 = observable.subscribe(a -> System.out.println("jack：" + a));

        Thread.sleep(5000);

        subscribe.dispose();
    }
}
