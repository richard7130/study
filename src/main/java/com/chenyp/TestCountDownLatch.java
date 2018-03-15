package com.chenyp;

import org.apache.log4j.Logger;

import java.util.concurrent.CountDownLatch;

/**
 * @author chenyuepeng
 * @create 2018-03-14 11:00
 * @since: 1.0.0
 * @desc CountDownLatch测试
 **/
public class TestCountDownLatch {


    private static final Logger logger = Logger.getLogger(TestCountDownLatch.class);

    /**
     * 一个同步辅助类，在完成一组正在其他线程中执行的操作之前，它允许一个或多个线程一直等待。
     * 用给定的计数 初始化 CountDownLatch。由于调用了 countDown() 方法，所以在当前计数到达零之前，
     * await 方法会一直受阻塞。到达0之后，会释放所有等待的线程，await 的所有后续调用都将立即返回。
     * 最常见的使用场景: 等待其他线程处理完才继续当前线程。
     */
    public static void main(String[] args) {
        final int count = 10; // 计数次数
        final CountDownLatch latch = new CountDownLatch(count);
        for (int i = 0; i < count; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        // do anything
                        logger.info("线程" + Thread.currentThread().getId());
                    } catch (Throwable e) {
                        // whatever
                    } finally {
                        // 很关键, 无论上面程序是否异常必须执行countDown,否则await无法释放
                        latch.countDown();
                    }
                }
            }).start();
        }
        try {
            // 10个线程countDown()都执行之后才会释放当前线程,程序才能继续往后执行
            latch.await();
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
        }
        logger.info("Finish");
    }
}
