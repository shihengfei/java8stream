package com.camelot.share.basic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

/**
 * <p>
 * Description:[收集，终止操作]
 * </p>
 *
 * @author shf
 * @version 1.0
 * @date Created on  2020/6/9 15:51
 */
public class UseCollectDemo {

//    public static void main(String[] args) {
////        test();
////        test2();
////        test3();
//        test4();
//    }



    /***
     * 收集操作, 收集操作会关闭流
     */
    private static void test(){
        List<Integer> stream = Arrays.asList(1, 3, 5, 7, 9, 5, 3, 6, 8, 2, 11, 36, 44, 85, 66, 55, 7);
        List<Integer> list = stream.stream().collect(Collectors.toList());
        System.out.println("转list:"+list);
        System.out.println();

        Set<Integer> set = stream.stream().collect(Collectors.toSet());
        System.out.println("转set:"+set);
        System.out.println();

        Map<Integer, Integer> map = stream.stream().distinct().collect(Collectors.toMap(r -> r, r -> r));
        System.out.println("转map:"+map);
        System.out.println();

        Long count = stream.stream().collect(Collectors.counting());
        System.out.println("集合size:"+count);
        System.out.println();

        Double averaging = stream.stream().collect(Collectors.averagingInt(i -> i + i));
        System.out.println("求平均值 :"+averaging);
        System.out.println();

        IntSummaryStatistics sum = stream.stream().collect(Collectors.summarizingInt(item -> item));
        System.out.println("求和 :"+sum);
        System.out.println();

        // 字符
        List<String> stream2 = Arrays.asList("张三","李四","王五","大哥","二弟");
        String joinStr = stream2.stream().collect(Collectors.joining(",", "(", ")"));
        System.out.println("连接流中每个字符串 :"+joinStr);

    }

    /***
     * 收集 分组 操作
     */
    private static void test2(){
        List<Student> students = Arrays.asList(new Student(1L, "大娃", 2, 10.1), new Student(2L, "二娃", 4, 40.1),
                new Student(3L, "三娃", 3, 30.1), new Student(4L, "大娃", 4, 20.1));

        // 通过指定字段分组
        Map<Integer, List<Student>> group1 = students.stream().collect(Collectors.groupingBy(Student::getAge));
        System.out.println(group1);

        // 多次分组
        Map<String, Map<String, List<Student>>> groups = students.stream().collect(Collectors.groupingBy(Student::getName, Collectors.groupingBy((e) -> {
            if (e.getMoney() < 25) {
                // key
                return "穷";
            } else {
                return "土豪";
            }
        })));
        System.out.println(groups);
    }

    /***
     * 去重 collectingAndThen 使用，将另一个收集器的结果转化
     */
    private static void test3(){
        List<Student> students = Arrays.asList(new Student(1L, "大娃", 2, 10.1), new Student(2L, "二娃", 4, 40.1),
                new Student(3L, "三娃", 3, 30.1), new Student(4L, "大娃", 4, 20.1));
        // 通过指定字段去重
        List<Student> distinct = students.stream().collect(
                // collectingAndThen
                collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(o -> o.getName()))),
                        ArrayList::new));
        System.out.println(distinct);

    }

    /***
     * 自定义收集
     */
    private static void test4(){
        List<Student> students = Arrays.asList(new Student(1L, "大娃", 2, 10.1), new Student(2L, "二娃", 4, 40.1),
                new Student(3L, "三娃", 3, 30.1), new Student(4L, "大娃", 4, 20.1));

        students.stream().collect(new Supplier<HashSet<String>>() {
                                   @Override
                                   public HashSet<String> get() {
                                       return new HashSet<>();
                                   }
                               },//第一个参数
                new BiConsumer<HashSet<String>, Student>() {
                    @Override
                    public void accept(HashSet<String> StudentNames, Student Student) {
                        StudentNames.add(Student.getName());
                    }
                },//第二个参数
                new BiConsumer<HashSet<String>, HashSet<String>>() {
                    @Override
                    public void accept(HashSet<String> StudentNames, HashSet<String> StudentNames2) {
                        StudentNames.addAll(StudentNames2);
                    }
                }//第三个参数
        ).forEach(System.out::println);
    }

    /***
     * 简化 lambda
     */
    private static void test5(){
        List<Student> students = Arrays.asList(new Student(1L, "大娃", 2, 10.1), new Student(2L, "二娃", 4, 40.1),
                new Student(3L, "三娃", 3, 30.1), new Student(4L, "大娃", 4, 20.1));

        students.stream().collect(HashSet::new,//第一个参数
                (StudentNames, Student) -> StudentNames.add(Student.getName()),//第二个参数
                (BiConsumer<HashSet<String>, HashSet<String>>)AbstractCollection::addAll//第三个参数
        ).forEach(System.out::println);
    }

    /***
     * 简化方法引用
     */
    private static void test6(){
        List<Student> students = Arrays.asList(new Student(1L, "大娃", 2, 10.1), new Student(2L, "二娃", 4, 40.1),
                new Student(3L, "三娃", 3, 30.1), new Student(4L, "大娃", 4, 20.1));

        students.stream().collect(HashSet::new,//第一个参数
                HashSet::add,//第二个参数
                HashSet::addAll //第三个参数，并行流需要
        ).forEach(System.out::println);
    }


    public static void main(String[] args) {

        String start = "2018-08-08";
        String end = "2018-09-08";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dStart = null;
        Date dEnd = null;
        try {
            dStart = sdf.parse(start);
            dEnd = sdf.parse(end);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<Date> dateList = findDates(dStart, dEnd);
        for (Date date : dateList) {
            System.out.println(sdf.format(date));
        }
    }

    //JAVA获取某段时间内的所有日期
    public static List<Date> findDates(Date dStart, Date dEnd) {
        Calendar cStart = Calendar.getInstance();
        cStart.setTime(dStart);

        List dateList = new ArrayList();
        //别忘了，把起始日期加上
        dateList.add(dStart);
        // 此日期是否在指定日期之后
        while (dEnd.after(cStart.getTime())) {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            cStart.add(Calendar.DAY_OF_MONTH, 1);
            dateList.add(cStart.getTime());
        }
        return dateList;
    }
}
