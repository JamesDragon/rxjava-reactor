package com.rxjava.observable;

import io.reactivex.Observable;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class BasicOperationTesting {
    public static void main(String[] args) {
//        filterAndMap();

        flatMapTesting();
    }

    static void flatMapTesting() {
        //flatMap大多的应用场景是根据子元素再做一些额外的操作 - rxjava示例
        Observable.fromArray(1, 2, 3).flatMap(x -> Observable.range(x, 3)).subscribe(System.out::println);

        System.out.println("+++++++++++++++++++++++++++++++++++++++++");
        //Java8的Lambda表达式
        Arrays.asList(1, 2, 3).stream().flatMap(item -> Stream.of(item + "-admin")).forEach(System.out::println);

    }


    static void filterAndMap() {
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
