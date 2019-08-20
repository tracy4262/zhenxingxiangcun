package com.ovit.manager.common.config.datasource;

import org.springframework.util.Assert;

/**
 * Created by ${huipei.x} on 2017-8-14.
 */
public class DataSourceSwitcher {
	private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

	public static void setDataSource(String dataSource) {
		Assert.notNull(dataSource, "dataSource cannot be null");
		contextHolder.set(dataSource);
	}

	public static void setMaster() {
	}

	public static void setSlave() {
		setDataSource("slave");
	}

	public static void setNswy() {
		setDataSource("nswy");
	}

	public static String getDataSource() {
		return (String) contextHolder.get();
	}

	public static void clearDataSource() {
		contextHolder.remove();
	}

}
