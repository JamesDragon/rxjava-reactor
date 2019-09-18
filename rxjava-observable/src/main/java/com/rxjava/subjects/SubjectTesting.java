package com.rxjava.subjects;

import io.reactivex.subjects.AsyncSubject;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.ReplaySubject;

import java.util.concurrent.TimeUnit;

public class SubjectTesting {
    public static void main(String[] args) {


//        behaviorSubject();

//        asyncSubject();

    }

    /**
     * 与cache拥有更丰富的API
     */
    static void replaySubject() {
        //类似Observable的cache方法，保存之前生产的元素，留给后续消费者消费
        ReplaySubject<Object> objectReplaySubject = ReplaySubject.create();

        //只保留最近的5个元素用于后续消费者消费
        ReplaySubject<Object> withSize = ReplaySubject.createWithSize(5);

        //只保留最近10秒内产生的元素用于后入的消费者消费
        ReplaySubject.createWithTime(10, TimeUnit.SECONDS, null);
    }

    /**
     * 从订阅前最近的一个元素开始消费
     */
    static void behaviorSubject() {
        BehaviorSubject<Object> behaviorSubject = BehaviorSubject.create();
        behaviorSubject.onNext(1);
        behaviorSubject.onNext(2);//从这里开始消费
        behaviorSubject.subscribe(value -> System.out.println("订阅的值：" + value));
        behaviorSubject.onNext(3);
        behaviorSubject.onComplete();
    }

    /**
     * 只消费完成前的最后一个元素
     */
    static void asyncSubject() {
        AsyncSubject<Object> asyncSubject = AsyncSubject.create();
        asyncSubject.onNext(1);
        asyncSubject.subscribe(x -> System.out.println(x));
        asyncSubject.onNext(2);
        asyncSubject.onComplete();
    }

}
