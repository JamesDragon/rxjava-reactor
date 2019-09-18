package com.rxjava.observable;

import io.reactivex.Observable;

public class BasicOperationTesting {
    public static void main(String[] args) {
        //Observable的Filter过滤操作
        Observable.range(1, 10)
                .filter(i -> i % 2 == 0)
                .subscribe(System.out::println);

        System.out.println("===============================分割线==============================");

        //利用Just定义多个值，通过Map进行转换
        Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
                .map(x -> x + "-from-just")
                .subscribe(System.out::println);
        //或者
        Observable.fromArray(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
                .map(x -> x + "-from-array")
                .subscribe(System.out::println);

    }
}
