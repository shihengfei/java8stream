package com.camelot.share.basic;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * <p>
 * Description:[流的映射操作] 中间操作
 * </p>
 * 中间操作会产生另一个流。因此中间操作可以用来创建执行一系列动作的管道
 * 我们提供的映射函数会处理原始流中的每一个元素，而映射流中包含了所有经过我们映射函数处理后产生的新元素
 * @author shf
 * @version 1.0
 * @date Created on  2020/6/9 11:10
 */
public class UseMapDemo {

    public static void main(String[] args) {
//        test();
//        test2();
        test3();
    }

    /***
     * List里面有三个Integer类型的元素分别为1，2，3。
     * 现在的需求是分别让List里面的每个元素都放大两倍后，再求积
     */
    private static void test(){
        List<Integer> lists = new ArrayList<>();
        lists.add(1);
        lists.add(2);
        lists.add(3);
        // 并行流  Integer product = lists.parallelStream().reduce(1, (a, b) -> a *  (b * 2), (a, b) -> a * b);
        // 同样的需求使用map实现,此时放大2倍的操作由map操作实现
        Integer reduce = lists.stream().parallel().map(item -> item * 2).reduce(1, (a, b) -> a * b, (a, b) -> a * b);
        System.out.println(reduce);
    }

    /***
     * 复杂的 map 使用
     */
    private static void test2(){
        ArrayList<Student> list = new ArrayList<>();
        list.add(new Student(1L,"zhangsan",18,1000.1));
        list.add(new Student(2L,"lisi",16,3000.1));
        list.add(new Student(3L,"wangwu",20,4000.1));
        list.add(new Student(4L,"zhaoliu",17,2000.1));

        // 计算学生一共有多少钱
        Optional<Double> reduce = list.stream().map(Student::getMoney) // 通过映射操作，得到一个只有money的流
                .reduce(Double::sum); // 求和
        reduce.ifPresent(item -> System.out.println("学生一共有:"+item));
    }

    /***
     * 划分区域
     */
    private static void test3() {
        List<String> citys = Arrays.asList("雁塔 曲江", "曲江 长安","高新 雁塔", "未央 曲江", "雁塔 长安", "未央 西咸");

        // 使用 map 得到一个流的数组
        Stream<Stream<String>> streamStream = citys.stream().map(mCitys -> Arrays.stream(mCitys.split(" ")));
        streamStream.forEach(System.out::println); // 对象地址

        System.out.println();

        //流里面的元素还是一个数组
        citys.stream()
                .map(mCities -> Arrays.stream(mCities.split(" "))) // Stream<Stream<String>>  依然是一个流
                .forEach(cities ->cities.forEach(city-> System.out.print(city+" "))); // 继续操作流遍历，得到内部流，继续遍历得到结果

        System.out.println();

        // 使用flatMap()就把数组合并到映射流里面了，得到一个新流
        Stream<String> stringStream = citys.stream().flatMap(mCities -> Arrays.stream(mCities.split(" ")));
        stringStream.forEach(city-> System.out.print(city+" "));  // 结果

        System.out.println();

        //使用distinct()方法去重
        citys.stream().flatMap(mCities->Arrays.stream(mCities.split(" "))).distinct().forEach(city-> System.out.print(city+" ")); // 去重

    }
}
