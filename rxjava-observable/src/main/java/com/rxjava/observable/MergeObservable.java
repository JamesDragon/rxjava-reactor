package com.rxjava.observable;

import io.reactivex.Observable;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class MergeObservable {
    public static void main(String[] args) throws InterruptedException {
//        mergeMethod();

//        zipMethod();

        combineLatest();
    }

    /**
     * 合并多个源 - 粗犷的操作
     * 原理就是利用fromArray后再进行flatMap操作
     */
    static void mergeMethod() {
        Observable<String> just1 = Observable.just("1");
        Observable<String> just2 = Observable.just("2");
        //1. 合并多个源
        Observable.merge(just1, just2).subscribe(just -> System.out.println(just));

        System.out.println("================================");

        //2. 只有两个源的话适用
        just1.mergeWith(just2).subscribe(System.out::println);
    }

    /**
     * 合并多个源并产生新的源 - 细致化的操作
     */
    static void zipMethod() {
        Observable<String> just1 = Observable.just("1");
        Observable<String> just2 = Observable.just("2");

        Observable.zip(just1, just2, (j1, j2) -> j1 + " = " + j2).subscribe(System.out::println);
    }

    /**
     * 寻找最近的一个元素进行合并
     */
    static void combineLatest() throws InterruptedException {
//        Observable.combineLatest(
//                Observable.fromArray(1, 2, 3, 4, 5),
//                Observable.fromArray("1", "2", "3", "4"), (item1, item2) -> item1 + ":" + item2
//        ).subscribe(System.out::println);

        Observable.combineLatest(
                Observable.interval(1, TimeUnit.SECONDS).map(x -> "Java-" + x),
                Observable.interval(2, TimeUnit.SECONDS).map(x -> "Observable-" + x), (item1, item2) -> item1 + ":" + item2
        ).subscribe(System.out::println);

        Thread.sleep(6000);
    }


}
