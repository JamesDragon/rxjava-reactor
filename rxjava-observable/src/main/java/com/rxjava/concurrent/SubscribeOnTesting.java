package com.rxjava.concurrent;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class SubscribeOnTesting {
    public static void main(String[] args) {
        Observable.just("1", "2", "3")
                .subscribeOn(Schedulers.computation())
                .subscribe(System.out::println);

    }
}
