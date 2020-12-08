package com.camelot.share.parallel;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.LongStream;

/**
 * <p>
 * Description:[ 并行流 ]
 * </p>
 *
 * @author shf
 * @version 1.0
 * @date Created on  2020/6/2 9:49
 */
public class ParallelDemo {

    private static Long END_VALUE = 1000_000_000L;

    public static void main(String[] args) {
        // 设置 ForkJoinPool 线程数
//        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "12");
//        localhostCPU();
        // 计算 0-10 亿 相加
//        test();
//        test2();
//        test3();
//        test4();
        forEachTest5();
    }

    /** 传统方式计算 */
    public static void  test(){
        Instant start = Instant.now();
        long sum = 0L;
        for (long i = 0; i <= END_VALUE; i ++){
            sum += i;
        }
        System.out.println(sum);
        Instant end = Instant.now();
        System.out.println("传统计算耗时：" + Duration.between(start, end).toMillis());
    }

    /** fork/join 计算 */
    public static void test2(){
        Instant start = Instant.now();
        ForkJoinPool pool = new ForkJoinPool();
        ForkJoinCalculate calculate = new ForkJoinCalculate(0, END_VALUE);
        Long sum = pool.invoke(calculate);
        System.out.println(sum);
        Instant end = Instant.now();
        System.out.println("分段计算耗时：" + Duration.between(start, end).toMillis());
    }

    /** stream 顺序流计算 */
    public static void test3(){
        Instant start = Instant.now();
        long sum = LongStream.rangeClosed(0, END_VALUE) // 获取一个流
                .reduce(0, Long::sum); // 求和
        System.out.println(sum);
        Instant end = Instant.now();
        System.out.println("顺序流计算耗时：" + Duration.between(start, end).toMillis());
    }

    /** stream 并行流计算 */
    public static void test4(){
        Instant start = Instant.now();
        long sum = LongStream.rangeClosed(0, END_VALUE)//.unordered() // 获取一个流
                .parallel() // 并行化
                .reduce(0, Long::sum); // 求和
        System.out.println(sum);
        Instant end = Instant.now();
        System.out.println("并行流计算耗时：" + Duration.between(start, end).toMillis());
    }

    /** 获取本机可用处理器数 */
    public static void localhostCPU(){
        // ForkJoinPool 中默认线程池大小通过获取本机逻辑内核数量 -1 得到线程数
        int num = Runtime.getRuntime().availableProcessors();
        System.out.println(num);
    }

    /***
     * forEach forEachOrdered 有序操作
     */
    public static void forEachTest5(){
        // forEach 并行情况不能保证流的有序性
        ArrayList<Long> list = new ArrayList<>();
        LongStream longStream = LongStream.rangeClosed(0, 100);
        LongStream.rangeClosed(0,100).forEach(item -> list.add(item));
        System.out.println("forEach:"+list);

        ArrayList<Long> list2 = new ArrayList<>();
        LongStream.rangeClosed(0,100).forEachOrdered(item -> list2.add(item));
        System.out.println("forEachOrdered:"+list2);
    }
}
