package com.camelot.share.basic;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 * Description:[流的缩减操作]
 * 核心概念：终端操作，只返回一个值
 * 无状态，不干预，关联性
 * </p>
 *
 * @author shf
 * @version 1.0
 */
public class UseReduceDemo {

    public static void main(String[] args) {
        List<Integer> lists = new ArrayList<>();
        lists.add(1);
        lists.add(2);
        lists.add(3);
        lists.add(4);
        lists.add(5);
        lists.add(6);
        // 普通的缩减操作
//        reduceStream(lists);
        // 特殊的缩减操作
//        maxMinStream(lists);
        reduceThreeStream(lists);
    }

    /**
     * 流的缩减操作，两种实现，有初始值和无初始值
     */
    private static void reduceStream(List<Integer> lists) {
        // 1.reduce return Optional<T>
        Optional<Integer> sum = lists.stream().reduce(Integer::sum);
        sum.ifPresent(integer -> System.out.println("list的总和为:" + integer));

        // 2.reduce  return T
        // identity 初始值，计算中会用到
        Integer sum2 = lists.stream().reduce(0, Integer::sum);//21
        System.out.println("list的总和为:" + sum2);

        // 3.求积
        Optional<Integer> product = lists.stream().reduce((a, b) -> a * b);
        product.ifPresent(integer -> System.out.println("list的积为:" + integer));

        Integer product2 = lists.stream().reduce(0, (a, b) -> a * b);
        System.out.println("list的积为:" + product2);//720
    }

    /***
     * 流的第三种缩减操作
     * 第一个参数，identity 初始值，返回类型
     * 第二个参数，accumulator 具体业务操作
     * 第三个参数，combiner 并行流的合并,需要考虑并发导致的问题
     *
     */
    private static void reduceThreeStream(List<Integer> lists){
        Map<String,Integer> Result_ = lists.stream().parallel()  // 顺序流的情况不会执行 combiner
                // <U> U  reduce(U identity,BiFunction<U, ? super T, U> accumulator,BinaryOperator<U> combiner);
         .reduce(new HashMap<>(),
            (map, item) -> {
//                System.out.println("BiFunction");
                map.put(item+"key:",item);
//                System.out.println("item: " + item);
                return map;
            },
            // 用于并行操作，将操作最后的数据合并
            (accumulatorMap, item) -> {
                System.out.println("BinaryOperator");
                accumulatorMap.putAll(item);
                System.out.println("item: " + item);
                return accumulatorMap;
            });
        System.out.println("Result_: "+Result_);
    }

    /***
     *    累加器部分（水平向右）
     *         accumulator
     * -----------------------------›
     * thread-1:   1 * 1 * 2   =   2    |    合并器方向（竖直向下）
     * thread-2:   1 * 2 * 2   =   4    |         combiner
     * thread-3:   1 * 3 * 2   =   6    |   因此最终的答案是2  *  4  *  6  =   48
     *                                  ˇ
     * 注：水平方向最前面的1就是identity的值 =  a *  (b * 2) 中的 a ， b为遍历的数据
     */
    private static void reduceFourStream() {
        // List里面有三个Integer类型的元素分别为1，2，3。现在的需求是分别让List里面的每个元素都放大两倍后，再求积
        List<Integer> lists = new ArrayList<>();
        lists.add(1);
        lists.add(2);
        lists.add(3);

        Integer product = lists.parallelStream().reduce(1, (a, b) -> a *  (b * 2), (a, b) -> a * b);
        System.out.println("product:" + product);//48
    }

    /**
     * 特殊的缩减操作
     */
    private static void maxMinStream(List<Integer> lists){
        // 最小值
        Optional<Integer> min = lists.stream().min(Integer::compareTo);
        min.ifPresent(integer -> System.out.println("lists的最小值为：" + integer));

        // 最大值
        Optional<Integer> max = lists.stream().max(Integer::compareTo);
        max.ifPresent(integer -> System.out.println("lists的最大值为：" + integer));
    }

}
