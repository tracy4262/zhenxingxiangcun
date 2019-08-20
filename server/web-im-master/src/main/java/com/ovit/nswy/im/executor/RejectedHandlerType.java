package com.ovit.nswy.im.executor;

/**
 * DESCRIPTION:
 * 枚举类 线程池rejectedHandler类型
 * Abort:默认的AbortPolicy 处理程序遭到拒绝将抛出运行时RejectedExecutionException
 * CallerRuns:直接在execute方法的调用线程中运行被拒绝的任务,如果执行程序已关闭，则会丢弃该任务。此策略提供简单的反馈控制机制，能够减缓新任务的提交速度
 * Discard:丢弃被拒绝的任务
 * DiscardOldest:放弃最旧的未处理请求，然后重试 execute；如果执行程序已关闭，则会丢弃该任务
 * @author zouyan
 * @create 2017-06-13 9:59
 * Created by fuck~ on 2017/6/13.
 */
public enum RejectedHandlerType {
    Abort,CallerRuns,Discard,DiscardOldest
}
