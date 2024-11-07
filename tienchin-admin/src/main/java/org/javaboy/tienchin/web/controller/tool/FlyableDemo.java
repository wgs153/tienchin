package org.javaboy.tienchin.web.controller.tool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author eqe
 * @Date 2024/9/22 11:38
 * @description:
 */
public class FlyableDemo {


    public static void main(String[] args) {

        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
                2,
                4,
                1,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(5),
                Executors.defaultThreadFactory(),
//                new ThreadPoolExecutor.AbortPolicy()
                new ThreadPoolExecutor.CallerRunsPolicy()


        );

        try {
            for (int i = 0; i < 100; i++) {
                threadPool.submit(
                        () -> {
                            try {
                                Thread.sleep(200);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                            System.out.println(Thread.currentThread().getName());
                        }
                );
            }
        } catch (RejectedExecutionException e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }

        System.out.println("00000000000000000000000");
        // 关闭线程池
        threadPool.shutdown();
        try {
            // 等待所有任务完成
            if (!threadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS)) {
                threadPool.shutdownNow();
            }
        } catch (InterruptedException e) {
            threadPool.shutdownNow();
            Thread.currentThread().interrupt();
        }


        System.out.println("主线程结束");
    }


}
