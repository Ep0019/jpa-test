package com.test;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lhy.utils.build.SearchService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath*:/applicationContext.xml")
public class Copy_2_of_JunitTest {
	@Autowired
	SearchService searchService;
	
	@Test
	public void insertData(){
		HashMap<String, Object[]> contentMap=new HashMap<String,Object[]>();
		contentMap.put("name", new String[]{"张三","李四","旺旺"});
		contentMap.put("address", new String[]{"福建厦门"});
		searchService.bulkInsertData("fenzhuang", contentMap);
	}
	
}
