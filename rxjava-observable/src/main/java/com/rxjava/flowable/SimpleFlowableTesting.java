package com.rxjava.flowable;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

public class SimpleFlowableTesting {
    public static void main(String[] args) throws InterruptedException {
        Flowable.range(1, 999)
                .map(Customer::new)
                .observeOn(Schedulers.io())
                .subscribe(e -> {
                    Thread.sleep(20);
                    System.out.println("我是ID元素：" + e.id + "-我是线程：" + Thread.currentThread().getId());
                });

        Thread.sleep(100000);
    }

    static class Customer {
        private Integer id;

        public Customer(Integer id) {
            this.id = id;
            System.out.println("构造ID:" + id + "线程:" + Thread.currentThread().getId());
        }
    }
}
