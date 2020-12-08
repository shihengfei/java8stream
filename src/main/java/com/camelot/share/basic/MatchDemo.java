package com.camelot.share.basic;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * <p>
 * Description:[匹配操作，终止流]
 * </p>
 *
 * @author shf
 * @version 1.0
 */
public class MatchDemo {

    public static void main(String[] args) {
//        test();
//        test2();
        test3();
    }

    /***
     * 是否匹配
     */
    public static void test(){
        List<Student> students = Arrays.asList(new Student(1L, "大娃", 2, 10.1), new Student(2L, "二娃", 4, 40.1),
                new Student(3L, "三娃", 3, 30.1), new Student(4L, "大娃", 4, 20.1));
        boolean b = students.stream().allMatch(item -> item.getName().equals("三娃"));
        System.out.println("全部匹配结果:"+b);

        boolean b1 = students.stream().anyMatch(item -> item.getName().equals("三娃"));
        System.out.println("任意匹配结果:"+b1);

        boolean b2 = students.stream().noneMatch(item -> item.getName().equals("三娃"));
        System.out.println("全部不匹配结果:"+b2);

    }

    /** 正则转 Predicate，筛选出匹配项 */
    public static void test2(){
        // 将正则表达式转化为 Predicate
        Predicate<String> emailFilter = Pattern
                .compile("^(.+)@camelot.com$")
                .asPredicate();
        // 需要正则校验的数据
        List<String> emails = Arrays.asList("fei@camelot.com", "bob@163.com",
                "cat@google.com", "david@camelot.com");
        // 校验
        List<String> desiredEmails = emails
                .stream()
                .filter(emailFilter)
                .collect(Collectors.toList());
        desiredEmails.forEach(System.out::println);
    }

    /***
     * 排序
     */
    public static void test3(){
        List<Student> students = Arrays.asList( new Student(2L, "二娃", 4, 100.1),new Student(2L, "二娃", 4, 40.1),
                new Student(3L, "三娃", 3, 30.1), new Student(4L, "大娃", 4, 20.1));
        // 默认排序，按自然顺序
        students.stream().map(Student::getMoney).sorted().forEach(System.out::println);

        // 正序 从大到小
        List<Student> collect = students.stream().sorted(Comparator.comparing(Student::getName)).collect(
            Collectors.toList());

        // 倒序 从大到小
        List<Student> collect2 = students.stream().sorted(Comparator.comparing(Student::getName).reversed()).collect(
            Collectors.toList());

        System.out.println("----------------------------------");
        collect2.forEach(item -> System.out.println(item));

        System.out.println("-----------");
        // 自定义排序,排序规则，1.按指定姓名排序2.通过金额倒序
        List<String> sortList =  Arrays.asList("大娃","二娃","三娃");
        students.stream().sorted(
                // 通过名称排
                Comparator.comparing(Student::getName,(x, y)->{
                    //按照读取的list顺序排序
                    for(String sort : sortList){
                        if(sort.equals(x) || sort.equals(y)){
                            if(x.equals(y)){
                                return 0;
                            }else if(sort.equals(x)){
                                return -1;
                            }else{
                                return 1;
                            }
                        }
                    }
                    return 0;
                }).thenComparing(
                        // 通过金额继续排序
                        Comparator.comparing(Student::getMoney,Comparator.nullsFirst(Double::compareTo)).reversed())
        ).collect(Collectors.toList()).forEach(System.out::println);

    }
}
