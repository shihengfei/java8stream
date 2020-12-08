package com.camelot.share.functioninterface;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * <p>
 * Description:[正则表达式使用 断言式函数接口 Predicate 表示]
 * </p>
 *
 * @author shf
 */
public class PredicateExample {

    public static void main(String[] args) {
        usePredicate();
//        patternToPredicate();
    }

    /** 使用 Predicate */
    public static void usePredicate(){
        int[] numbers= {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
        List<Integer> list=new ArrayList<>();
        for(int i:numbers) {
            list.add(i);
        }
        //  p1 ，i >5
        Predicate<Integer> p1=i->i>5;
        //  p2 ，i <20
        Predicate<Integer> p2=i->i<20;
        //  p3 ，i是2的倍数
        Predicate<Integer> p3=i->i%2==0;

        // 需求：筛选大于 5且小于 20 且2的倍数
        // and 对应 &&
        List test=list.stream().filter(p1.and(p2).and(p3)).collect(Collectors.toList());
        // 等价
//        List test=list.stream().filter(i->i>5).filter(i->i<20).filter(i->i%2==0).collect(Collectors.toList());

        // 需求：筛选大于5或2的倍数
        // or 对应 ||
//        List test=list.stream().filter(p1.or(p3)).collect(Collectors.toList());

        // 需求: 筛选非2的倍数
        // negate 对应 !
        // isEqual 对应 ==
//        List test=list.stream().filter(p3.negate().and(Predicate.isEqual(7))).collect(Collectors.toList());
        System.out.println(test.toString());
    }


}
