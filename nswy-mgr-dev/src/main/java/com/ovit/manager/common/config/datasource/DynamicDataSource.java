package com.ovit.manager.common.config.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * Created by ${huipei.x} on 2017-8-14.
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
	protected Object determineCurrentLookupKey() {
		return DataSourceSwitcher.getDataSource();
	}
}