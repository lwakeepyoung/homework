package com.lwa.week.datasourceconfig;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.Map;

/**
 * @Author: lwa
 * @Date: 2021/11/7 16:23
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    private static final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<>();

    public DynamicDataSource(DataSource defaultDataSource, Map<Object, Object> targetDataSources){
        super.setDefaultTargetDataSource(defaultDataSource);
        super.setTargetDataSources(targetDataSources);
        super.afterPropertiesSet();
    }


    @Override
    protected Object determineCurrentLookupKey() {
        return getDataSource();
    }

    public static void setDataSource(String dataSource){
        CONTEXT_HOLDER.set(dataSource);
    }

    public static String getDataSource(){
        return CONTEXT_HOLDER.get();
    }

    public static void clearDataSource(){
        CONTEXT_HOLDER.remove();
    }
}
