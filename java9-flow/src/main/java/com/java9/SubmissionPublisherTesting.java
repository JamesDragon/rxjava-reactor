package com.java9;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.SubmissionPublisher;
import java.util.stream.LongStream;

public class SubmissionPublisherTesting {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<Void> completableFuture = null;
        try (SubmissionPublisher<Long> publisher = new SubmissionPublisher<>()) {
            System.out.println("缓冲区：" + publisher.getMaxBufferCapacity());
            //订阅者的消费行为,consume内部是一个订阅者对象
            completableFuture = publisher.consume(System.out::println);

            //发布的元素
            LongStream.range(1, 10).forEach(publisher::submit);
        } finally {
            //阻塞等待所有元素消费完成
            completableFuture.get();
        }

    }
}
