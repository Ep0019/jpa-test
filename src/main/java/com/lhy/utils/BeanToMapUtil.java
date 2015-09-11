package com.lhy.utils;
/**
 * @author: 李慧勇
 * @description:
 * @mail:jdlglhy@163.com
 * @2015年7月24日
 * @version 1.0
 */
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.lhy.annotation.ElasticsSearchAno;

public class BeanToMapUtil { 
   
    /** 
     * 将一个 Map 对象转化为一个 JavaBean 
     * @param type 要转化的类型 
     * @param map 包含属性值的 map 
     * @return 转化出来的 JavaBean 对象 
     * @throws IntrospectionException 
     *             如果分析类属性失败 
     * @throws IllegalAccessException 
     *             如果实例化 JavaBean 失败 
     * @throws InstantiationException 
     *             如果实例化 JavaBean 失败 
     * @throws InvocationTargetException 
     *             如果调用属性的 setter 方法失败 
     */ 
    public static Object convertMap(Class type, Map map) 
            throws IntrospectionException, IllegalAccessException, 
            InstantiationException, InvocationTargetException { 
        BeanInfo beanInfo = Introspector.getBeanInfo(type); // 获取类属性 
        Object obj = type.newInstance(); // 创建 JavaBean 对象 

        // 给 JavaBean 对象的属性赋值 
        PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors(); 
        for (int i = 0; i< propertyDescriptors.length; i++) { 
            PropertyDescriptor descriptor = propertyDescriptors[i]; 
            String propertyName = descriptor.getName(); 

            if (map.containsKey(propertyName)) { 
                // 下面一句可以 try 起来，这样当一个属性赋值失败的时候就不会影响其他属性赋值。 
                Object value = map.get(propertyName); 

                Object[] args = new Object[1]; 
                args[0] = value; 

                descriptor.getWriteMethod().invoke(obj, args); 
            } 
        } 
        return obj; 
    } 

    /** 
     * 将一个 JavaBean 对象转化为一个  Map 
     * @param bean 要转化的JavaBean 对象 
     * @return 转化出来的  Map 对象 
     * @throws IntrospectionException 如果分析类属性失败 
     * @throws IllegalAccessException 如果实例化 JavaBean 失败 
     * @throws InvocationTargetException 如果调用属性的 setter 方法失败 
     */ 
    public static Map convertBean(Object bean) 
            throws IntrospectionException, IllegalAccessException, InvocationTargetException { 
        Class type = bean.getClass();
        Map returnMap = new HashMap(); 
        BeanInfo beanInfo = Introspector.getBeanInfo(type); 
        PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors(); 
        for (int i = 0; i< propertyDescriptors.length; i++) { 
            PropertyDescriptor descriptor = propertyDescriptors[i]; 
            String propertyName = descriptor.getName();
           System.out.println(descriptor.getPropertyType().getName());
            System.out.println(propertyName+"---------");
            if (!propertyName.equals("class")) { 
                Method readMethod = descriptor.getReadMethod();
                if(readMethod.getAnnotation(ElasticsSearchAno.class) != null){
                	 System.out.println(readMethod.getAnnotation(ElasticsSearchAno.class).isElasticsSearch());
                }
                Object result = readMethod.invoke(bean, new Object[0]); 
                if (result != null) { 
                    returnMap.put(propertyName, result); 
                    System.out.println(propertyName);
                } else { 
                	 System.out.println(propertyName);
                    returnMap.put(propertyName, ""); 
                } 
            } 
        } 
        return returnMap; 
    } 
    public static void main(String[] args) throws IllegalAccessException, InvocationTargetException, IntrospectionException {
		/*User user=new User();
		user.setAddress("111");
		user.setEmail("333");
		convertBean(user);*/
    	String str="java.lang.String";
    	System.out.println(str.substring(str.lastIndexOf(".")+1, str.length()));
    	//System.out.println(SearchTypeProperty.valueOf("java.lang.String".substring("java.lang.String".lastIndexOf(".")+1, "java.lang.String".length())));
	}
} 
 

