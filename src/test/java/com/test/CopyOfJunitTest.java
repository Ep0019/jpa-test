package com.test;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lhy.annotation.GetJsonData;
import com.lhy.entity.Home;
import com.lhy.entity.Product;
import com.lhy.entity.Role;
import com.lhy.entity.User;
import com.lhy.search.ClientEliasticSearchUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath*:/applicationContext.xml")
public class CopyOfJunitTest {
	@Autowired
	private ClientEliasticSearchUtils ces;
	@Test
	public void createPreIndex(){
		ces.createPreIndex(User.class);
	}
	/*简单对象推荐使用这种方式*/
	@Test 
	public void putmapingData(){
		User user=new User();
		user.setId(4l);
		user.setAddress("鸡");
		user.setMobile("15280252084");
		Role role=new Role();
		role.setDescript("国家都是你的。。");
		role.setName("zhanddd。。。。dg");
		role.setId(2l);
		user.setRole(role);
		ces.createIndexObjectData(user, String.valueOf(user.getId()));
	}
	/*复杂对象推荐使用这种方式*/
	@Test 
	public void jsonData(){
		User user=new User();
		user.setId(3l);
		user.setAddress("吃炸鸡");
		user.setMobile("15280252084");
		Role role=new Role();
		role.setDescript("国家都是你的。。");
		role.setName("zhanddd。。。。dg");
		role.setId(2l);
		user.setRole(role);
		ces.createIndexJsonData(User.class,GetJsonData.getBuilderJson(user), String.valueOf(user.getId()));
	}
	@Test
	public void query() throws InstantiationException, IllegalAccessException, InvocationTargetException, IntrospectionException{
		/*List<User> list=ces.searchQuery("女鞋", User.class, 1, 100);
		System.out.println(list.size());
		for(User user:list){
			System.out.println(user.getAddress());
		}*/
		/*List<User> list=ces.eliasticSearchQuery("炸鸡", User.class, 1, 100);
		System.out.println(list.size());
		for(User user:list){
			System.out.println(user.getAddress());
			System.out.println(user.getRole());
		}*/
		/*ces.simpleSearch("home", searchContentMap, SearchLogic.must, filterContentMap, ilterLogic, from, offset, sortField, sortType)*/
	}
	@Test
	public void delete(){
		ces.deleteIndex(User.class, "5");
	}
	@Test
	public void getIndex() throws InstantiationException, IllegalAccessException{
		User u=(User) ces.getByIndexId(User.class, "6");
		System.out.println(u.getAddress());
	}
	@Test
	public void updateIndex() throws Exception{
		User user=new User();
		user.setId(1l);
		user.setAddress("李宁耐1磨运动鞋子1");
		user.setMobile("13303030234");
		user.setEmail("jdlglhy@163.com");
		ces.createIndexObjectData(user, "1");
	}
	
	
	
	/*************************华丽分割******************/
	/**
	 * @Title: createProductPreIndex
	 * @Description: 创建商品预定义索引
	 * @param:    
	 * @return: void   
	 * @throws
	 */
	@Test
	public void createProductPreIndex(){
		ces.createPreIndex(Home.class);
	}
	@Test
	public void addHomeIndex(){
		for(int i=10000;i<50000;i++){
			Home home=new Home();
			home.setId(Long.valueOf(String.valueOf(i)));
			home.setTitle("满五唯一 免税海景房");
			home.setVlillageName("珠江帝景");
			home.setSquare("175");
			home.setHouseType("三室二厅");
			home.setTime("2025");
			home.setFeature("满5年唯一 独家有钥匙");
			home.setMydefine("需忘我人");
			ces.createIndexObjectData(home, String.valueOf(home.getId()));
		}
		System.out.println("---end---");
	}
	@Test
	public void queryHomeIndex() throws InstantiationException, IllegalAccessException{
		List<Home> pros=ces.eliasticSearchQuery("满五", Home.class, 1, 10,"time","desc");
	}
	@Test
	public void queryNewHomeIndex() throws Exception{
		List<Home> pros=ces.newEliasticSearchQuery("满五", Home.class, 1, 10,"time","desc");
	}
	@Test
	public void addProductIndex(){
		for(int i=100000;i<1000010;i++){
			Product product=new Product();
			product.setId(Long.valueOf(String.valueOf(i)));
			product.setBrandName("李宁"+String.valueOf(i));
			product.setDescriber("你好中"+String.valueOf(i)+"国的的的");
			product.setDetails("没有一个"+String.valueOf(i)+"襄阳的的的");
			product.setName("李"+String.valueOf(i)+"宁精品运动鞋子");
			product.setOriginPlace("中国台湾"+String.valueOf(i));
			product.setKeywords("我是一个兵"+i);
			product.setSubtitle("并不认识你"+i);
			ces.createIndexObjectData(product, String.valueOf(product.getId()));
		}
		/*for(int i=2;i<100000;i++){
			Product product=new Product();
			product.setId(Long.valueOf(String.valueOf(i)));
			product.setBrandName("李宁"+String.valueOf(i));
			product.setDescriber("你好中"+String.valueOf(i)+"国的的的");
			product.setDetails("没有一个"+String.valueOf(i)+"襄阳的的的");
			product.setName("李"+String.valueOf(i)+"宁精品运动鞋子");
			product.setOriginPlace("中国台湾"+String.valueOf(i));
			ces.createIndexObjectData(product, String.valueOf(product.getId()));
		}*/
		System.out.println("----------------end====================");
	}
	@Test
	public void queryProductIndex() throws InstantiationException, IllegalAccessException{
		/*List<Product> pros=ces.eliasticSearchQuery("认识", Product.class, 1, 10);
		for(Product p:pros){
			System.out.println(p.getDescriber());
		}*/
	}
	
	
}
