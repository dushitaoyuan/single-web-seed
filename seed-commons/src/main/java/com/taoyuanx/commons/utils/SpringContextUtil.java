package com.taoyuanx.commons.utils;

import cn.hutool.core.lang.Snowflake;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author dushitaoyuan
 * @date 2019/9/1111:12
 * @desc: spring容器工具
 */
public class SpringContextUtil   implements ApplicationContextAware ,InitializingBean {
    private  String configLocatioin;
    private  Integer workNodeId;
    private  Integer dataCenterId;


    private static ApplicationContext applicationContext;
    private static Snowflake snowflake;



    @Override
    public void afterPropertiesSet() throws Exception {
        //初始化配置
        PropertiesUtil.initSystemConfig(PropertiesUtil.loadProperties(configLocatioin));
        //初始化主键生成器
        if(workNodeId!=null&&dataCenterId!=null){
            snowflake=new Snowflake(workNodeId,dataCenterId);
        }else {
            snowflake=new Snowflake(1,1);
        }


    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static Object getBean(String name){
        return getApplicationContext().getBean(name);
    }

    public static <T> T getBean(Class<T> clazz){
        return getApplicationContext().getBean(clazz);
    }

    public static <T> T getBean(String name,Class<T> clazz){
        return getApplicationContext().getBean(name, clazz);
    }


    public static  Snowflake getSnowflake() {
        return snowflake;
    }

    public String getConfigLocatioin() {
        return configLocatioin;
    }

    public void setConfigLocatioin(String configLocatioin) {
        this.configLocatioin = configLocatioin;
    }

    public Integer getWorkNodeId() {
        return workNodeId;
    }

    public void setWorkNodeId(Integer workNodeId) {
        this.workNodeId = workNodeId;
    }

    public Integer getDataCenterId() {
        return dataCenterId;
    }

    public void setDataCenterId(Integer dataCenterId) {
        this.dataCenterId = dataCenterId;
    }



}
