package com.camelot.share.parallel;

import java.util.concurrent.RecursiveTask;

/**
 * <p>
 * Description:[fork/join 计算类]
 * </p>
 *
 * @author shf
 * @version 1.0
 * @date Created on  2020/6/2 9:58
 */
public class ForkJoinCalculate extends RecursiveTask<Long> {

    private long start;
    private long end;

    /** 分割阈值，实际情况大小需要调节，分片越小，会因为分片操作导致耗时增加 */
    private static final long THRESHOLD = 100000000;

    public ForkJoinCalculate(long start, long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        // 计算分片中数据量
        long length = end - start;
        // 根据预估的数据量获取最小处理单元的大小阈值，
        // 当数据量已经小于这个阈值的时候进行计算，
        if (length <= THRESHOLD) {
            long sum = 0;
            for (long i = start; i <= end; i++) {
                sum += i;
            }
            return sum;
        }else {
            // 否则进行fork 将任务划分成更小的数据块
            long middle = (start + end) / 2;
            ForkJoinCalculate left = new ForkJoinCalculate(start, middle);
            left.fork();
            ForkJoinCalculate right = new ForkJoinCalculate(middle + 1, end);
            right.fork();
            return left.join() + right.join();
        }
    }
}
