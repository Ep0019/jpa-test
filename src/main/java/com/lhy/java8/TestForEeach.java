/*package com.lhy.java8;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

*//**
 * @author: 李慧勇
 * @description:
 * @mail:jdlglhy@163.com
 * @2015年7月27日
 * @version 1.0
 *//*
public class TestForEeach {
	
	public static void main(String[] args) {
		List<String> names = Arrays.asList("peter", "anna", "maike", "xenia");
		java 1.7
		Collections.sort(names, new Comparator<String>() {
		    @Override
		    public int compare(String a, String b) {
		        return b.compareTo(a);
		    }
		});
		System.out.println(names.toString());
		
		java 1.8
		Collections.sort(names, (String a, String b) ->{
			return b.compareTo(a);
		});
		java 1.8 方法体只有一行代码
		Collections.sort(names, (String a, String b) ->b.compareTo(a));
		
		System.out.println(names.toString());
	}
}
*/