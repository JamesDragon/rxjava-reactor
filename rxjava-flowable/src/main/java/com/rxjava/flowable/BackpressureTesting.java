package com.rxjava.flowable;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.schedulers.Schedulers;

import java.util.concurrent.ThreadLocalRandom;

public class BackpressureTesting {
    public static void main(String[] args) throws InterruptedException {
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
                    Thread.sleep(100);
                    System.out.println("元素消费：" + element);
                });

        Thread.sleep(100000);
    }
}
