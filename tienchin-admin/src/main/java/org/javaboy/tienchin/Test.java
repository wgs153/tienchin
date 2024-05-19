package org.javaboy.tienchin;

public class Test {
    int a = 1;

    public static void main(String[] args) {
        Test test = new Test();
        for (int i = 0; i < 20; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    test.test2();
                }
            });
            thread.start();
        }
    }

    public void test2() {
        synchronized (this) {
            System.out.println(Thread.currentThread().getName() + ":" + a++);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
