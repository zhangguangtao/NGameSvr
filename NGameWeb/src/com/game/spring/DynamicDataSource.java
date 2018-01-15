package com.game.spring;

import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource{

	 @Override  
     public Logger getParentLogger() {  
            return null;  
     }  
	
	@Override
	protected Object determineCurrentLookupKey() {
		// TODO Auto-generated method stub
		 return DataSourceContextHolder.getDbType();  
	}

}
