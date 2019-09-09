package com.rxjava.observable;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ObservableSchedulerTesting {
    public static void main(String[] args) throws InterruptedException {

//        //间隔一定时间执行订阅内容
//        Disposable disposable = Observable.interval(1, TimeUnit.SECONDS).subscribe(num -> {
//            int number = counter.incrementAndGet();
//            System.out.println(number);
//        }, Throwable::printStackTrace, () -> {
//            System.out.println("完成了");
//        });
//
//        Thread.sleep(10000);
//        disposable.dispose();
    }

    /**
     * 一次性的延迟执行订阅
     */
    public void testTime1() {
        //一次性的延迟执行订阅
        Observable.timer(10, TimeUnit.SECONDS).subscribe(s -> System.out.println("aaa"));
    }
}
