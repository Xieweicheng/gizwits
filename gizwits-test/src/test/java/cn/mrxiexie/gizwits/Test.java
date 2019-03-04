package cn.mrxiexie.gizwits;

import java.util.*;

/**
 * @author mrxiexie
 * @date 3/2/19 7:29 PM
 */
public class Test {

    static List<Integer> integers = new ArrayList<>();

    static LinkedList linkedList = new LinkedList();

    static {
        for (int i = 0; i < 1000; i++) {
            integers.add(i);
        }
    }

    public static void main(String[] args) throws InterruptedException {

        A a = new A();
        Thread add = new Thread(a);
        Thread remove = new Thread(a);

        add.start();
//        add.start();
        remove.start();
        add.join();
        remove.join();
        System.out.println(a.getI());
    }

}
