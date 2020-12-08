package com.camelot.share.basic;

import java.util.*;
import java.util.stream.Stream;

/**
 * <p>
 * Description:[java stream 创建的四种方式,惰性求值]
 * </p>
 *
 * @author shf
 * @version 1.0
 */
public class BasicDemo {

    public static void main(String[] args) {
//        test();
//        test2();
//        test3();
//        test4();
        test5();
    }

    /**
     *     default Stream<E> stream() {
     *         return StreamSupport.stream(spliterator(), false);
     *     }
     *     通过 Collection 集合提供的接口创建
     * */
    private static void test() {
        List<String> list = new ArrayList<>();
        Stream<String> stream = list.stream();
        // parallel 将流转为并行流
        Stream<String> parallel = stream.parallel();
        Stream<String> parallelStream = list.parallelStream();
        // sequential 将并行流转为普通流
        Stream<String> sequential = parallelStream.sequential();
    }

    /**
     * 通过 Arrays 静态方法创建
     * 返回 IntStream LongStream 等
     */
    public static void test2(){
        Integer[] num = new Integer[23];
        Stream<Integer> stream1 = Arrays.stream(num);
    }

    /***
     * 通过 Stream 静态方法创建，实际调用的还是 Arrays
     * */
    public static void test3(){
        Stream<Integer> stream2 = Stream.of(1, 5, 7);
    }

    /***
     * 通过静态方法 Stream.iterate() 迭代 和 Stream.generate(), 创建无限流。
     * */
    public static void test4(){
        // 迭代
        Stream<Integer> stream3 = Stream.iterate(0, (x) -> x + 2).limit(2);
        stream3.forEach(System.out::println);

        System.out.println("-------------");

        // 生成流
        Stream<Double> stream4 = Stream.generate(Math::random).limit(4);
        stream4.forEach(System.out::println);
    }

    /***
     * 惰性求值
     */
    private static void test5(){
        // 中间操作不会做任何处理
        List<Student> students = Arrays.asList(new Student(1L, "大娃", 2, 10.1), new Student(2L, "二娃", 4, 40.1),
                new Student(3L, "三娃", 3, 30.1), new Student(4L, "大娃", 4, 20.1));
        Stream<Student> stream = students.stream().filter((e) -> {
            System.out.println("惰性求值");
            return e.getAge() > 2;
        });
        System.out.println("--------------------");

        // 终止操作，一次性执行全部功能， 称为 "惰性求值"
        stream.forEach(System.out::println);
    }

}
