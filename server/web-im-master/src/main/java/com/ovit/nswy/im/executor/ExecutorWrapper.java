package com.ovit.nswy.im.executor;

import java.util.concurrent.*;

/**
 * DESCRIPTION:
 * ThreadPoolExecutor包装类
 * @author zouyan
 * @create 2017-06-13 9:46
 * Created by fuck~ on 2017/6/13.
 */
public class ExecutorWrapper {

    /**
     * 根据参数定制线程池
     * 定制线程池的ThreadFactory统一使用自定义的MasThreadFactory
     * @param corePoolSize
     * @param maximumPoolSize
     * @param keepAliveTime
     * @param unit
     * @param workQueue
     * @param threadFactoryPrefix
     * @param handlerType
     * @return
     */
    public static ThreadPoolExecutor createThreadPoolExecutor(
            int corePoolSize, int maximumPoolSize,
            long keepAliveTime, TimeUnit unit,
            BlockingQueue<Runnable> workQueue, String threadFactoryPrefix,
            RejectedHandlerType handlerType) {
        // 使用自定义的NamedRecordThreadFactory来创建线程池
        java.util.concurrent.ThreadFactory threadFactory = new CustomThreadFactory(threadFactoryPrefix, maximumPoolSize);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
        // 设置线程池的拒绝策略
        if (RejectedHandlerType.CallerRuns.equals(handlerType)) {
            executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        } else if (RejectedHandlerType.Discard.equals(handlerType)) {
            executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
        } else if (RejectedHandlerType.DiscardOldest.equals(handlerType)) {
            executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardOldestPolicy());
        } else {
            executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        }
        return executor;
    }

    /**
     * 根据参数定制线程池,不包含拒绝策略
     * 定制线程池的ThreadFactory统一使用自定义的MasThreadFactory
     * @param corePoolSize
     * @param maximumPoolSize
     * @param keepAliveTime
     * @param unit
     * @param threadFactoryPrefix
     * @return
     */
    public static ThreadPoolExecutor createThreadPoolExecutor(
            int corePoolSize, int maximumPoolSize,
            long keepAliveTime, TimeUnit unit,
            String threadFactoryPrefix) {
        // 使用自定义的NamedRecordThreadFactory来创建线程池
        java.util.concurrent.ThreadFactory threadFactory = new CustomThreadFactory(threadFactoryPrefix, maximumPoolSize);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, new LinkedBlockingQueue<Runnable>(), threadFactory);
        return executor;
    }
}
