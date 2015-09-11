package com.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lhy.service.RoleService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath*:/applicationContext.xml")
public class RoleJunitTest {
	@Autowired
	private RoleService roleService;
	/*@Autowired
	private RedisTemplate<String,Object> RedisTemplate;
	@Autowired
	private StringRedisTemplate stringRedisTemplate;*/
	
	/*@Test
	public void TestRedis(){
		ValueOperations<String, Object> valueOperations=RedisTemplate.opsForValue();
		RedisEntity redisEntity=new RedisEntity(1l,"lihuiyong","我叫李慧勇");
		valueOperations.set("redisEntity", redisEntity);
		RedisEntity redisEntity2=(RedisEntity) valueOperations.get("redisEntity");
		System.out.println(redisEntity2.toString());
		
		System.out.println(stringRedisTemplate+"--------------");
		
		
		
	}
	
	@Test
	public void Test(){
		List<Role> list=new ArrayList<Role>();
		for(int i=0;i<20;i++){
			Role role=new Role();
			role.setDescript("role"+i);
			role.setName("roleName"+i);
			list.add(role);
		}
		roleService.updateBatch(list);
	}*/
	
	@Test
	public void Test2(){
		roleService.getRoleListByName("");
	}
}
