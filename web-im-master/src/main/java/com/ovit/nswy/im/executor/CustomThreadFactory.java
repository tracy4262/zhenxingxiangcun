package com.ovit.nswy.im.executor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * DESCRIPTION:
 * 自定义ThreadFactory
 * @author zouyan
 * @create 2017-06-13 9:49
 * Created by fuck~ on 2017/6/13.
 */
public class CustomThreadFactory implements java.util.concurrent.ThreadFactory {
    //thread list
    private List<Thread> threadsRecordList;
    //最大线程数
    private int maximumPoolSize;
    //线程name prefix
    private String namePrefix;

    java.util.concurrent.ThreadFactory factory = Executors.defaultThreadFactory();

    public CustomThreadFactory(String namePrefix, int maximumPoolSize) {
        this.namePrefix = namePrefix;
        threadsRecordList = new ArrayList<Thread>();
        this.maximumPoolSize = maximumPoolSize;
    }

    @Override
    public Thread newThread(Runnable r) {
        checkRecordThreadsSize();
        Thread thread = factory.newThread(r);
        thread.setName(namePrefix + "-" + thread.getName());
        threadsRecordList.add(thread);
        return thread;
    }

    /**
     * 在每次线程池新建线程之前 首先检查已经记录的线程的个数
     * 如果当前线程的个数已经大于或者等于线程池所允许的最大线程的大小
     * 则需要移除部分记录的线程 线程池不允许线程的个数大于maximumPoolSize的大小
     */
    private void checkRecordThreadsSize() {
        if (threadsRecordList.size() >= maximumPoolSize) {
            // System.out.println("threads record list size more than maximumPoolSize");
            Iterator<Thread> iterator = threadsRecordList.iterator();
            while (iterator.hasNext()) {
                Thread eachThread = iterator.next();
                if (Thread.State.TERMINATED.equals(eachThread.getState())) {
                    // System.out.println("Thread:" + eachThread.getName() + " terminated, should remove");
                    iterator.remove();
                }
            }
        }
    }
}
