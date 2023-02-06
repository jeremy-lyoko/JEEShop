package com.Jeremy.utils;


import com.Jeremy.domain.User;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

//converter接口 类型转换器
public class UserBeanUtils {
    public static void populate(User user, Map<String, String[]> parameterMap) throws InvocationTargetException, IllegalAccessException {

        ConvertUtils.register(new Converter(){
            @Override
            public Object convert(Class aClass, Object value) {
                SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-DD");
                Date parse = null;
                try {
                    parse = format.parse(value.toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return parse;
            }
        }, Date.class);
        BeanUtils.populate(user,parameterMap);
    }
}
