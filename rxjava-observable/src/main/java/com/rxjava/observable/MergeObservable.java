package com.rxjava.observable;

import io.reactivex.Observable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class MergeObservable {
    public static void main(String[] args) throws InterruptedException {
//        mergeMethod();

//        zipMethod();

//        combineLatest();

//        withLatestFrom();

//        amb();

//        reduce();

//        collect();

        distinct();
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

    /**
     * A.withLatestForm(B,(t1,t2) -> t1+t2)的方式声明，A会与B最近的一个元素相结合
     */
    static void withLatestFrom() {
        Observable<Integer> observable1 = Observable.fromArray(1, 2, 3, 4, 5);
        //取最近的元素
        Observable<String> observable2 = Observable.fromArray("苹果", "梨子", "西瓜");

        observable1.withLatestFrom(observable2, (t1, t2) -> t1 + t2).subscribe(System.out::println);
    }

    /**
     * 只消费优先进入的队列
     */
    static void amb() {
        Observable.ambArray(
                Observable.fromArray(1, 2, 3, 4, 5).delay(2, TimeUnit.SECONDS),
                Observable.fromArray("苹果", "梨子", "西瓜"))
                .subscribe(System.out::println);
    }

    /**
     * 聚合操作
     */
    static void reduce() {
        Observable.range(1, 5).reduceWith(ArrayList::new, (list, item) -> {
            list.add(item);
            return list;
        }).subscribe(item -> item.forEach(System.out::println));
    }

    /**
     * 不用返回的归集操作
     */
    static void collect() {
        Observable.range(1, 5).collect(ArrayList::new, (list, item) -> list.add(item)).subscribe(System.out::println);
    }

    /**
     * 元素的去重操作
     */
    static void distinct() {
        Observable.just(1, 2, 2, 3, 4).distinct().subscribe(System.out::println);
        System.out.println("======================================");
        Observable.just(
                LocalDate.of(2019, 1, 10),
                LocalDate.of(2019, 2, 10),
                LocalDate.of(2019, 1, 10)
        ).distinct(LocalDate::getMonth).subscribe(System.out::println);
    }

}
