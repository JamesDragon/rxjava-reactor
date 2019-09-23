package com.rxjava.observable;

import io.reactivex.Observable;

public class MergeObservable {
    public static void main(String[] args) {
//        mergeMethod();

        zipMethod();
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
}
