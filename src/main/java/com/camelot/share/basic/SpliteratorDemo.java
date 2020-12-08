package com.camelot.share.basic;

import java.util.Arrays;
import java.util.List;
import java.util.Spliterator;

/**
 * <p>
 * Description:[迭代器]
 * </p>
 *
 * @author shf
 * @version 1.0
 */
public class SpliteratorDemo {
    public static void main(String[] args) {
//        test();
        split();
    }

    private static void test(){
        List<String> lists = Arrays.asList("A", "B", "C", "D");

        Spliterator<String> spliterator = lists.stream().spliterator();
        // 调用 tryAdvance 遍历
        System.out.println("tryAdvance：--------------");
        while (spliterator.tryAdvance(System.out::println));

        System.out.println("forEachRemaining：--------------");
        lists.stream().spliterator().forEachRemaining(System.out::println);
    }

    /***
     * 将流截断
     */
    private static void split() {
        List<String> lists = Arrays.asList("A", "B", "C", "D");
        // 获取迭代器
        Spliterator<String> spliterator = lists.stream().parallel().spliterator();
        // 分割得到另一个
        Spliterator<String> spliterator2 = spliterator.trySplit();
        if (spliterator2 != null){
            Spliterator<String> spliterator21 = spliterator2.trySplit();
            if (spliterator21 != null) {
                System.out.println("spliterator21:-----");
                spliterator21.forEachRemaining(System.out::println);
            }
            System.out.println("spliterator2:-----");
            spliterator2.forEachRemaining(System.out::println);
        }

        System.out.println("spliterator:------------------");
        // 原来的
        spliterator.forEachRemaining(System.out::println);
    }
}
