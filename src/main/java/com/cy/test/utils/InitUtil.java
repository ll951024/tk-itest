package com.cy.test.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class InitUtil {
    public static final String oldDay30="2018-05-30";
    public static final String oldDay29="2018-05-29";
    public static final String oldDay28="2018-05-28";
    public static final String oldDay27="2018-05-27";
    public String newDay30,newDay29,newDay28,newDay27;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public  String formatDate(Date date){
        return sdf.format(date);
    }

    public Date parseDate(String date){
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public InitUtil() {
        Calendar c= Calendar.getInstance();
        c.add(Calendar.DATE,-1);
        this.newDay30=formatDate(c.getTime());
        c.add(Calendar.DATE,-1);
        this.newDay29=formatDate(c.getTime());
        c.add(Calendar.DATE,-1);
        this.newDay28=formatDate(c.getTime());
        c.add(Calendar.DATE,-1);
        this.newDay27=formatDate(c.getTime());
    }

}
