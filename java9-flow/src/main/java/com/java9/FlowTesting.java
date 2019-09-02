package com.java9;

import com.java9.flow.MyDemoPublisher;
import com.java9.flow.MyDemoSubscriber;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.IntStream;

public class FlowTesting {
    public static void main(String[] args) {
        ExecutorService executorService = ForkJoinPool.commonPool();

        try (MyDemoPublisher<Integer> publisher = new MyDemoPublisher<>(executorService)) {
            demoSubscriber(publisher, "One");
            demoSubscriber(publisher, "Two");
            demoSubscriber(publisher, "Three");
            IntStream.range(1, 5).forEach(publisher::submit);
        } finally {
            executorService.shutdown();
            int shutdownDelayTime = 1;
            System.out.println("等待" + shutdownDelayTime + "秒后结束服务");
            try {
                executorService.awaitTermination(1, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                System.out.println("捕捉到executorService.awaitTermination方法的异常：" + e.getMessage());
            } finally {
                System.out.println("调用executorService.shutdownNow()结束服务");
                List<Runnable> runnableList = executorService.shutdownNow();
                System.out.println("还剩" + runnableList.size() + "个任务等待执行，服务已关闭");
            }
        }
    }

    static void demoSubscriber(MyDemoPublisher<Integer> publisher, String subscriberName) {
        MyDemoSubscriber<Integer> myDemoSubscriber = new MyDemoSubscriber<>(subscriberName, 4L);
        publisher.subscribe(myDemoSubscriber);
    }

}
