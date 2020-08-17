package com.cy.test.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class AppConfigUtil extends PropertyPlaceholderConfigurer {

    private static Map<String, Object> ctxPropertiesMap;

    /**
     * 重写processProperties方法
     */
    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props) throws BeansException {
        super.processProperties(beanFactoryToProcess, props);
        ctxPropertiesMap = new HashMap<String, Object>();
        for (Object key : props.keySet()) {
            String keyStr = key.toString();
            String value = props.getProperty(keyStr);
            ctxPropertiesMap.put(keyStr, value);
        }
    }

    public static Object getContextProperty(String name) {
        return ctxPropertiesMap.get(name);
    }

    public static Object setContextProperty(String name,Object value) {
        return ctxPropertiesMap.put(name, value);
    }

    public static Map<String, Object> getContextPropertyMap() {
        return ctxPropertiesMap;
    }

}

