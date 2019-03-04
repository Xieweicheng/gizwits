package cn.mrxiexie.gizwits;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author mrxiexie
 * @date 3/2/19 7:29 PM
 */
public class A implements Runnable {

    private int i = 0;

    private Lock lock = new ReentrantLock();

    @Override
    public void run() {

        try {
            lock.lock();
            for (int i1 = 0; i1 < 100; i1++) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                i++;
            }
        }finally {
            lock.unlock();
        }
    }

    public int getI() {
        return i;
    }
}
