package com.camelot.share.functioninterface;

import java.util.function.Function;

/**
 * <p>
 * Description:[函数式接口 Function ]
 * </p>
 *
 * @author shf
 */
public class FunctionExample {
    public static void main(String[] args) {
        apply();
//        compose();
    }

    /** 1.apply
     首先我们已经知道了Function是一个泛型类，其中定义了两个泛型参数T和R，在Function中，T代表输入参数，R代表返回的结果。也许你很好奇，为什么跟别的java源码不一样，Function 的源码中并没有具体的逻辑呢？
     其实这很容易理解，Function 就是一个函数，其作用类似于数学中函数的定义 ，（x,y）跟<T,R>的作用几乎一致。
     y=f(x)
     所以Function中没有具体的操作，具体的操作需要我们去为它指定，因此apply具体返回的结果取决于传入的lambda表达式 */
    public static void apply(){
        // 单个函数使用
        // 定义一个数学函数  x = x * 2
        Function<Integer,Integer> function = i -> i * 2;
        // apply 为函数传入入参
        Integer apply = function.apply(5);
        System.out.println(apply);

        // 多逻辑，多函数嵌套.
        Function<Integer,Integer> A=i->i+1;
        Function<Integer,Integer> B=i->i*i;
        // F1 = (i+1)*(i+1)
        System.out.println("F1:"+B.apply(A.apply(5)));
        // F2 = (i * i) + 1
        System.out.println("F2:"+A.apply(B.apply(5)));
    }

    /** compose接收一个Function参数，返回时先用传入的逻辑执行apply，然后使用当前Function的apply。 */
    /** andThen 与 compose 相反 */
    public static void compose(){
        Function<Integer,Integer> A=i->i+1;
        Function<Integer,Integer> B=i->i*i;
        // F1 = (i+1) * (i+1)
        System.out.println("F1:"+B.apply(A.apply(5)));
        System.out.println("F1:"+B.compose(A).apply(5));
        // F2 = (i * i) + 1
        System.out.println("F2:"+A.apply(B.apply(5)));
        System.out.println("F2:"+B.andThen(A).apply(5));
    }
}
