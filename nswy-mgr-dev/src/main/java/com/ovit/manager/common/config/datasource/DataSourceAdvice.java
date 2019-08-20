package com.ovit.manager.common.config.datasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.ThrowsAdvice;

import java.lang.reflect.Method;

/**
 * Created by ${huipei.x} on 2017-8-14.
 */
public class DataSourceAdvice implements MethodBeforeAdvice, AfterReturningAdvice, ThrowsAdvice {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	public void before(Method method, Object[] args, Object target) throws Throwable {
		if ((method.getName().startsWith("add")) || (method.getName().startsWith("create"))
				|| (method.getName().startsWith("save")) || (method.getName().startsWith("add"))
				|| (method.getName().startsWith("edit")) || (method.getName().startsWith("update"))
				|| (method.getName().startsWith("del")) || (method.getName().startsWith("insert"))
				|| (method.getName().startsWith("delete")) || (method.getName().startsWith("remove"))) {
			DataSourceSwitcher.setMaster();
			logger.info("使用写库： master");
		} else {
			if (method.getName().startsWith("nswy")) {
				logger.info("使用农事无忧数据库：nswy");
				DataSourceSwitcher.setNswy();
			} else {
				logger.info("使用读库： slave");
				DataSourceSwitcher.setSlave();
			}
		}
	}

	public void afterReturning(Object arg0, Method method, Object[] args, Object target) throws Throwable {
	}

	public void afterThrowing(Method method, Object[] args, Object target, Exception ex) throws Throwable {
		DataSourceSwitcher.setSlave();
		System.out.println("抛出Exception之后被调用:切入点: " + target.getClass().getName() + "类中" + method.getName() + "方法");
		System.out.println("出现异常,切换到: slave");
	}
}