package org.javaboy.tienchin;
//import ndsec.test.Base;
//import ndsec.test.performance.task.asymmetricCryptoTask;
//import ndsec.test.performance.task.asymmetricSignAndVerifyTask;
//import ndsec.test.performance.task.generateRandomTask;
//import ndsec.test.performance.task.symmetricCryptoTask;
//import org.testng.Assert;
//import org.testng.annotations.Test;
//import org.testng.asserts.SoftAssert;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

//public class SDFPerformanceTest extends Base {
public class SDFPerformanceTest {
//    public static Random rd = new Random();
//    public static final int THREAD_SIZE = Runtime.getRuntime().availableProcessors();
//
//    @Test(dataProvider = "generateRandomData")
//    public static void generateRandomDataPerformanceTest(int dataLen) {
//        SoftAssert softAssert = new SoftAssert();
//        ExecutorService threadPool = Executors.newWorkStealingPool(THREAD_SIZE);
//
//        AtomicInteger rightSum = new AtomicInteger(0);
//        AtomicInteger sum = new AtomicInteger(0);
//
//        Future<Boolean>[] futures = new Future[REQUEST_SIZE];
//        long[] end = new long[1];
//        long start = System.currentTimeMillis();
//
//        for (int i = 0; i < REQUEST_SIZE; i++) {
//            futures[i] = threadPool.submit(new generateRandomTask(ndSecProvider, "RND", dataLen, (Boolean rt) -> {
//                int num = sum.incrementAndGet();
//                if (num == REQUEST_SIZE) {
//                    end[0] = System.currentTimeMillis();
//                }
//
//                if (rt.equals(Boolean.TRUE)) {
//                    rightSum.incrementAndGet();
//                }
//            }));
//        }
//
//        for (int i = 0; i < REQUEST_SIZE; i++) {
//            try {
//                softAssert.assertTrue(futures[i].get());
//            } catch (Exception e) {
//                e.printStackTrace();
//                Assert.fail();
//            }
//        }
//        perfLog(dataLen, rightSum.get(), end, start, "RND", "generateRandomDataTest");
//        softAssert.assertAll();
//    }
//
//    @Test(dataProvider = "symmetricPerformanceData")
//    public static void symmetricCryptoPerformanceTest(String algId, int dataLen, int index) {
//        SoftAssert softAssert = new SoftAssert();
//        ExecutorService threadPool = Executors.newWorkStealingPool(THREAD_SIZE);
//        AtomicInteger rightSum = new AtomicInteger(0);
//        AtomicInteger sum = new AtomicInteger(0);
//        Future<Boolean>[] futures = new Future[REQUEST_SIZE];
//        long[] end = new long[1];
//        long start = System.currentTimeMillis();
//
//        for (int i = 0; i < REQUEST_SIZE; i++) {
//            futures[i] = threadPool.submit(new symmetricCryptoTask(ndSecProvider, algId, dataLen, index, (Boolean rt) -> {
//                int num = sum.incrementAndGet();
//                if (num == REQUEST_SIZE) {
//                    end[0] = System.currentTimeMillis();
//                }
//                if (rt.equals(Boolean.TRUE))
//                    rightSum.incrementAndGet();
//            }));
//        }
//        for (int i = 0; i < REQUEST_SIZE; i++) {
//            try {
//                softAssert.assertTrue(futures[i].get());
//                if (futures[i].get() != Boolean.TRUE) {
//                    System.out.println(futures[i].get());
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//                Assert.fail();
//            }
//        }
//        perfLog(dataLen, rightSum.get(), end, start, algId, "symmetricCryptoPerformanceTest");
//        softAssert.assertAll();
//    }
//
//    @Test(dataProvider = "asymmetricEncryptAndDecryptPerformanceData")
//    public static void asymmetricEncryptAndDecryptPerformanceTest(String algId, int dataLen, int index, String pwd) {
//        SoftAssert softAssert = new SoftAssert();
//        ExecutorService threadPool = Executors.newWorkStealingPool(THREAD_SIZE);
//        AtomicInteger rightSum = new AtomicInteger(0);
//        AtomicInteger sum = new AtomicInteger(0);
//        Future<Boolean>[] futures = new Future[REQUEST_SIZE];
//        long[] end = new long[1];
//        long start = System.currentTimeMillis();
//        for (int i = 0; i < REQUEST_SIZE; i++) {
//            futures[i] = threadPool.submit(new asymmetricCryptoTask(ndSecProvider, algId, dataLen, index, pwd, (Boolean rt) -> {
//                int num = sum.incrementAndGet();
//                if (num == REQUEST_SIZE) {
//                    end[0] = System.currentTimeMillis();
//                }
//                if (rt.equals(Boolean.TRUE))
//                    rightSum.incrementAndGet();
//            }));
//        }
//
//        for (int i = 0; i < REQUEST_SIZE; i++) {
//            try {
//                softAssert.assertTrue(futures[i].get());
//            } catch (Exception e) {
//                e.printStackTrace();
//                Assert.fail();
//            }
//        }
//        perfLog(dataLen, rightSum.get(), end, start, algId, "asymmetricEncryptAndDecryptPerformanceTest");
//        softAssert.assertAll();
//    }
//
//    @Test(dataProvider = "SM2AndSM3SignAndVerifyPerformanceData")
//    public static void SM2AndSM3SignAndVerifyPerformanceTest(int dataLen, String algorithm, int index, String pwd) {
//        SoftAssert softAssert = new SoftAssert();
//        ExecutorService threadPool = Executors.newWorkStealingPool(THREAD_SIZE);
//        AtomicInteger rightSum = new AtomicInteger(0);
//        AtomicInteger sum = new AtomicInteger(0);
//        Future<Boolean>[] futures = new Future[REQUEST_SIZE];
//        long[] end = new long[1];
//        long start = System.currentTimeMillis();
//        for (int i = 0; i < REQUEST_SIZE; i++) {
//            futures[i] = threadPool.submit(new asymmetricSignAndVerifyTask(ndSecProvider, dataLen, algorithm, index, pwd, (Boolean rt) -> {
//                int num = sum.incrementAndGet();
//                if (num == REQUEST_SIZE) {
//                    end[0] = System.currentTimeMillis();
//                }
//                if (rt.equals(Boolean.TRUE))
//                    rightSum.incrementAndGet();
//            }));
//        }
//        for (int i = 0; i < REQUEST_SIZE; i++) {
//            try {
//                softAssert.assertTrue(futures[i].get());
//            } catch (Exception e) {
//                e.printStackTrace();
//                Assert.fail();
//            }
//        }
//        perfLog(dataLen, rightSum.get(), end, start, algorithm, "SM2AndSM3SignAndVerifyPerformanceTest");
//        softAssert.assertAll();
//    }
}
