package com.rxjava.flowable;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.schedulers.Schedulers;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class BackpressureTesting {
    public static void main(String[] args) throws InterruptedException {

        //设置buffer的缓存大小，默认128
//        System.setProperty("rx2.buffer-size","998");


//        simpleBackpressure();

//        onBackpressureXXX();

        FlowableGenerate();

        Thread.sleep(100000);
    }

    static void FlowableGenerate() {
        //没有状态控制
//        Flowable.generate(emitter -> emitter.onNext(ThreadLocalRandom.current().nextInt(1, 100000)))
//                .subscribeOn(Schedulers.computation())
//                .doOnNext(send -> System.out.println("发送的元素：" + send))
//                .observeOn(Schedulers.io())
//                .subscribe(i -> {
//                    TimeUnit.MILLISECONDS.sleep(100);
//                    System.out.println("接收的元素：" + i);
//                });


        //存在状态控制
        Flowable.generate(() -> new AtomicInteger(500), (state, emitter) -> {
            int current = state.decrementAndGet();
            emitter.onNext(current);
            if (current == -50){
                emitter.onComplete();
            }
        }).doOnNext(send -> System.out.println("发送的元素：" + send))
//                .onBackpressureBuffer()   //追加背压策略
                .observeOn(Schedulers.io())
                .subscribe(i -> {
                    TimeUnit.MILLISECONDS.sleep(50);
                    System.out.println("接收的元素：" + i);
                });

    }


    static void onBackpressureXXX() {
        Flowable.interval(1, TimeUnit.MILLISECONDS)
                .onBackpressureBuffer()
                .observeOn(Schedulers.computation())
                .subscribe(e -> System.out.println("元素：" + e));
    }

    static void simpleBackpressure() {
        Flowable<Object> objectFlowable = Flowable.create(new FlowableOnSubscribe<Object>() {
            @Override
            public void subscribe(FlowableEmitter<Object> emitter) throws Exception {
                for (int i = 0; i < 10000; i++) {
                    emitter.onNext(i);
                }
            }
        }, BackpressureStrategy.BUFFER);

        objectFlowable.doOnNext(e -> System.out.println("元素数：" + e))
                .observeOn(Schedulers.computation())
                .subscribe(element -> {
                    Thread.sleep(20);
                    System.out.println("元素消费：" + element);
                });

    }
}
