package me.example;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    private static ExecutorService executorService = Executors.newFixedThreadPool(2);

    private static void simulateMemoryLeak() {
        ThreadLocal<byte[]> threadLocal = new ThreadLocal<>();

        // 10MB 할당
        threadLocal.set(new byte[1024 * 1024 * 10]);

        // set null
        //threadLocal.set(null);

        // remove
        threadLocal.remove();
    }

    public static void main(String[] args) {
        Runtime runtime = Runtime.getRuntime();
        List<GarbageCollectorMXBean> gcBeans = ManagementFactory.getGarbageCollectorMXBeans();

        for (int i = 0; i < 100; i++) {
            executorService.submit(() -> {
                simulateMemoryLeak();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        executorService.shutdown();

        try {
            if (!executorService.awaitTermination(1, java.util.concurrent.TimeUnit.MINUTES)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }
        System.out.println(getGcCount(gcBeans));
        // 최종 메모리 사용량 측정
        long finalMemoryUsage = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("Final memory usage: " + finalMemoryUsage);
    }

    private static long getGcCount(List<GarbageCollectorMXBean> gcBeans) {
        return gcBeans.stream().mapToLong(GarbageCollectorMXBean::getCollectionCount).sum();
    }
}
