package com.rxjava.observable;


import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.operators.observable.ObservableCreate;

import java.math.BigInteger;
import java.util.Collections;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadLocalRandom;

public class ObservableTesting {
    public static void main(String[] args) throws InterruptedException {
//        Observable<Object> observable = Observable.create(new ObservableOnSubscribe<Object>() {
//            @Override
//            public void subscribe(ObservableEmitter<Object> observableEmitter) throws Exception {
//                observableEmitter.onNext(ThreadLocalRandom.current().nextLong(1000000000));
//                observableEmitter.onComplete();
//            }
//            //紧跟缓存
//        }).cache();
//        //通过内部类衔接
//
//        observable.subscribe(consumer -> {
//            System.out.println(consumer);
//        });
//
//        observable.subscribe(consumer -> {
//            System.out.println(consumer);
//        });
//
//
        //1~10个有效的元素
//        Observable.just(1,2,3).subscribe();
//      //处理来自于操作的返回值
//        Observable.fromArray();
//        Observable.fromFuture(new FutureTask<>(() -> ""));
//        Observable.fromIterable(Collections.singleton(1));
//
//        //延迟生成Observable对象，订阅时才会创建
//        Observable.defer(() -> {
//           return Observable.fromArray(1,2,3);
//        }).subscribe();
//
//        //从1开始产生六个数
        Observable.range(1,6);
//        //空的Observable对象
//        Observable.empty();
//        //用于测试
//        Observable.never();
//
//        Observable.error(new RuntimeException());




    }
}