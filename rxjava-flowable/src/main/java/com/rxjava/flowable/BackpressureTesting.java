package com.rxjava.flowable;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.schedulers.Schedulers;

import java.util.concurrent.ThreadLocalRandom;

public class BackpressureTesting {
    public static void main(String[] args) throws InterruptedException {

        //设置buffer的缓存大小，默认128
//        System.setProperty("rx2.buffer-size","998");


//        simpleBackpressure();





        Thread.sleep(100000);
    }

    static void simpleBackpressure(){
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
