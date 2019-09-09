package com.rxjava.observable;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observables.ConnectableObservable;

import java.math.BigInteger;

public class ConnectableObservableTesting {
    public static void main(String[] args) throws InterruptedException {

//        connectableObservable();

//        observableCache();
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
