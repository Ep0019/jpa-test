/*package com.lhy.java8;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;

import com.lhy.entity.Role;
import com.sun.org.glassfish.gmbal.ParameterNames;


*//**
 * @author: 李慧勇
 * @description:java1.8新特性  方法映射
 * Converter
 * 1.只能有一个抽象方法（多个需要使用default关键字且需要方法体）
 * 2.Java 8 允许你使用 :: 关键字来传递方法或者构造函数引用			
 * @mail:jdlglhy@163.com
 * @2015年7月27日
 * @version 1.0
 *//*
public class Test {
	
	public static void main(String[] args) throws NoSuchMethodException, SecurityException {
		Converter<String, Integer> converter = (from) -> Integer.valueOf(from);
		System.out.println(converter.convert("123"));
		converter.saidHello();
		
		
		Converter<String, Integer> converter2 = Integer::valueOf;
		System.out.println(converter2.convert("345"));
		
		
		Factory<Role> factory=Role::new;
		Role role=factory.create("zhangs","我是张三");
		System.out.println(role.getDescript());
		
		
		
		
		
		List<String> stringCollection = new ArrayList<>();
		stringCollection.add("ddd2");
		stringCollection.add("aaa2");
		stringCollection.add("bbb1");
		stringCollection.add("aaa1");
		stringCollection.add("bbb3");
		stringCollection.add("ccc");
		stringCollection.add("bbb2");
		stringCollection.add("ddd1");
		stringCollection
	    .stream()
	    .filter((s) -> s.startsWith("a"))
	    .forEach(System.out::println);
		
		System.out.println("-----------11111111-----------------");
		
		stringCollection.stream().forEach(
					(new Consumer<String>(){
							@Override
							public void accept(String str) {
								System.out.println(str);
							}
					})
				);
		System.out.println("-----------222222222-----------------");
		stringCollection.stream().forEach((name)->{System.out.println(name);});
		
		
		
		System.out.println("-----------333333333-----------------");
		stringCollection.stream().forEach((name)->System.out.println(name));
		
		  Method method = Test.class.getMethod( "main", String[].class );
	        for( final Parameter parameter: method.getParameters() ) {
	            System.out.println( "Parameter: " + parameter.getName() );
	        }

	        long[] arrayOfLong = new long [ 20000 ];        
	         
	        Arrays.parallelSetAll( arrayOfLong, 
	            index -> ThreadLocalRandom.current().nextInt( 1000000 ) );
	        Arrays.stream( arrayOfLong ).limit( 100 ).forEach( 
	            i -> System.out.print( i + " " ) );
	        System.out.println();
	         
	        Arrays.parallelSort( arrayOfLong );     
	        Arrays.stream( arrayOfLong ).limit( 100 ).forEach( 
	            i -> System.out.print( i + " " ) );
	        System.out.println();
	        
	        

	}
}
*/