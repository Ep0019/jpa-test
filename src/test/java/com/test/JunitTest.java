package com.test;

import java.beans.IntrospectionException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.InvocationTargetException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lhy.entity.Role;
import com.lhy.entity.User;
import com.lhy.utils.ElasticSearch;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath*:/applicationContext.xml")
public class JunitTest {
	/*@Autowired
	private Client client;*/
	/*@Autowired
	private ClientEliasticSearchUtils cll;*/
	@Autowired
	private ElasticSearch elasticSearch;
	/*@Autowired
	private JestClient jestclient;*/
	/*@Autowired
	private RoleService roleService;*/
	/**
	 * @Title: aopTest
	 * @Description: 前置通知，后置通知，后置通知返回
	 * @param:    
	 * @return: void   
	 * @throws
	 */
	@Test
	public void aopTest(){
		
		System.out.println("-----aop start---");
		/*------无参数的拦截-
		roleService.insert1();
		------参数为String的拦截-
		roleService.insert2("zhangsan");
		------返回值为role对象的拦截-
		roleService.insert3();
		Role role=new Role();
		role.setDescript("aop test");
		role.setName("hello");
	    ------参数和返回值为role对象的拦截-
		roleService.insert4(role);
		------前置通知（拦截两个参数）-*/
		/*roleService.insert6("around通知测试", 111l);
		System.out.println("-----aop end---");*/
	}
	@Test
	public void getUUid(){
		System.out.println(UUID.randomUUID());
	} 
	@Test
	public void upload() throws IOException{
		/*FileInputStream fis=new FileInputStream(new File("G://src基于Netty5.0中级案例九之NettyServer收取数据存库【缓存到队列由单独线程处理】.zip"));
		FileOutputStream fos=new FileOutputStream(new File("G://fileupload//file-server//temp.zip"));
		FileChannel fileInChannel=fis.getChannel();
		FileChannel fileOutChannel=fos.getChannel();
		fileInChannel.transferTo(0, fileInChannel.size()/2, fileOutChannel);
		if(fis!=null)fis.close();
		if(fileInChannel!=null)fileInChannel.close();
		if(fos!=null)fos.close();
		if(fileOutChannel!=null)fileOutChannel.close();*/
		
		
		/*File file = new File("G://src基于Netty5.0中级案例九之NettyServer收取数据存库【缓存到队列由单独线程处理】.zip");
		FileInputStream in=new FileInputStream(file);
		FileChannel fin=in.getChannel();
		RandomAccessFile ra=new RandomAccessFile(new File("G://fileupload//file-server//temp.zip"), "rw");
		ra.setLength(fin.size());
		ra.seek(fin.size()/2);
		ByteBuffer buff=ByteBuffer.allocate((int)fin.size());
		fin.read(buff);
		ra.read(buff.array(), buff.remaining()/2, buff.remaining());
		ra.close();
		ra=null;*/
		
		
		/*FileOutputStream fos=new FileOutputStream(new File("G://fileupload//file-server//temp.temp"));
		FileChannel fin=in.getChannel();
		FileChannel fon=fos.getChannel();
		fin.transferTo(fin.size()/2, fin.size(), fon);
		if(in!=null)in.close();
		if(fin!=null)fin.close();
		if(fos!=null)fos.close();
		if(fon!=null)fon.close();*/
	
	}
	
/*	@Autowired
	private SearchService searchService;
	创建索引
	@Test
	public void create(){
		searchService.indexSampleArticles();
	}
	根据id查询
	@Test
	public void getById(){
		searchService.get("articles", "AU6uN7iYCiJAMA8ZWQqd");
	}
	简单查询测试
	@Test
	public void search(){
		String queryText="o";
		List<Article> list=new ArrayList<Article>();
		list=searchService.searchArticles("the");
		System.out.println(list.size()+"----");
	}
	单记录增加
	@Test
	public void addSingle(){
		searchService.add();
	}
	批量增加
	@Test
	public void indexBulk(){
		searchService.indexBulk("user");
	}
	根据id删除索引节点
	@Test
	public void deleteById(){
		searchService.deleteIndexById();
	}
	@Test
	public void api(){
		searchService.searchApi();
	}*/
	/*-----------------------------------------------*/
	@Test
	public void createIndex(){
		User user=new User();
		user.setId(4l);
		user.setAddress("addas运动鞋子");
		user.setMobile("15280252084");
		Role role=new Role();
		role.setDescript("国家都是你的");
		role.setName("zhanddddg");
		role.setId(2l);
		user.setRole(role);
		elasticSearch.createIndex(user);
	}
	@Test
	public void getClassById(){
		/*User user=(User) elasticSearch.getClassById(User.class, "2");
		System.out.println(user.getMobile());
		System.out.println(user.getRealName());
		System.out.println(user.getRole());
		System.out.println(user.getRole().getId());*/
	}
	@Test
	public void deleteIndexById(){
		/*System.out.println(elasticSearch.deleteIndexById(User.class, "1"));*/
	}
	@Test
	public void searchByQueryString(){
		List<User> users=elasticSearch.searchByQueryString("吃炸鸡",User.class,1,100);
		System.out.println(users.size()+"--size--");
		for(User user:users){
			System.out.println(user.getAddress());
		}
	}
	/*@Test
	public void ddd() throws ElasticsearchException, IOException{
		Settings settings = ImmutableSettings.settingsBuilder().put("client.transport.sniff", true).put("cluster.name", "elasticsearch").build();  
		Client client = new TransportClient(settings).addTransportAddress(new InetSocketTransportAddress("localhost", 9300)); 
		预定义一个索引开始
		CreateIndexRequestBuilder cib=client.admin().indices().prepareCreate("productindex");
		XContentBuilder mapping = XContentFactory.jsonBuilder()
				.startObject()
						.startObject("properties")//properties下定义的title等等就是属于我们需要的自定义字段了,相当于数据库中的表字段 ,此处相当于创建数据库表
							.startObject("title").field("type", "string").field("store", "yes").endObject()    
							.startObject("description").field("type", "string").field("indexAnalyzer", "ik")  
		                    .field("searchAnalyzer", "ik").endObject()  
							.startObject("price").field("type", "double").endObject()  
							.startObject("onSale").field("type", "boolean").endObject()  
							.startObject("type").field("type", "integer").endObject()  
							.startObject("createDate").field("type", "date").field("format","YYYYMMddhhMMSS").endObject()
						.endObject()
				.endObject();  
		cib.addMapping("productindex", mapping);
		cib.execute().actionGet();*/
		/*预定义一个索引结束*/
		/*IndexResponse response = client.prepareIndex("twitter", "tweet")
		        .setSource(XContentFactory.jsonBuilder() 
		                  .startObject("title")  
		                    .field("type", "string")             
		                    .field("indexAnalyzer", "ik")  
		                    .field("searchAnalyzer", "ik")  
		                  .endObject()   
		                  .startObject("code")  
		                    .field("type", "string")           
		                    .field("indexAnalyzer", "ik")  
		                    .field("searchAnalyzer", "ik")  
		                  .endObject()       
		                .endObject()  
		                  )
		        .execute()
		        .actionGet();
		client.close();*/
	/*}
	@Test
	public void testddd() throws ElasticsearchException, IOException{
		Settings settings = ImmutableSettings.settingsBuilder().put("client.transport.sniff", true).put("cluster.name", "elasticsearch").build();  
		Client client = new TransportClient(settings).addTransportAddress(new InetSocketTransportAddress("localhost", 9300)); 
		// productindex为上个方法中定义的索引,prindextype为类型.jk8231为id,以此可以代替memchche来进行数据的缓存
		 IndexResponse response = client.prepareIndex("productindex", "productindex" ,"3")
			        .setSource(XContentFactory.jsonBuilder()
			                    .startObject()
			                        .field("title", "abcd1")//该字段在上面的方法中mapping定义了,所以该字段就有了自定义的属性,比如 type等
			                        .field("description", "中国人nick3")
			                        .field("price", 232 )
			                        .field("onSale",true)
			                        .field("type",2)
			                        .field("createDate",new Date().getTime())
			                        .field("dfsfs","哈哈大师")//该字段在上面方法中的mapping中没有定义,所以该字段的属性使用es默认的.
			                    .endObject()
			                  )
			        .setTTL(8000)//这样就等于单独设定了该条记录的失效时间,单位是毫秒,必须在mapping中打开_ttl的设置开关
			        .execute()
			        .actionGet();
		 IndexResponse response2 = client.prepareIndex("productindex", "productindex" ,"4")
			        .setSource(XContentFactory.jsonBuilder()
			                    .startObject()
			                        .field("title", "abcd2")
			                        .field("description", "中国李宁人2啊大师法撒旦法")
			                        .field("price", 232 )
			                        .field("onSale",true)
			                        .field("type",22)
			                        .field("createDate",new Date().getTime())
			                    .endObject()
			                  )
			        .execute()
			        .actionGet();
	}*/
	
	/*@Test
	public void search(){
		Settings settings = ImmutableSettings.settingsBuilder().put("client.transport.sniff", true).put("cluster.name", "elasticsearch").build();  
		Client client = new TransportClient(settings).addTransportAddress(new InetSocketTransportAddress("localhost", 9300)); 
		System.out.println("删除");
		DeleteResponse responsedd = client.prepareDelete("productindex", "productindex", "34dds1")
		        .setOperationThreaded(false)
		        .execute()
		        .actionGet();
		System.out.println("根据主键搜索得到值");
		GetResponse responsere = client.prepareGet("productindex", "productindex", "oht-Sp87SA6J5l3yo9dagw")
		        .execute()
		        .actionGet();
		System.out.println("完成读取--"+responsere.getSourceAsString());
		System.out.println("搜索");
		SearchRequestBuilder builder= client.prepareSearch("productindex")  //搜索productindex,prepareSearch(String... indices)注意该方法的参数,可以搜索多个索引
                .setTypes("productindex")  
                .setSearchType(SearchType.DEFAULT)  
                .setFrom(0)  
                .setSize(50);  		
		 QueryBuilder qb2 = QueryBuilders.boolQuery() //  boolQuery() 就相当于 sql中的and
                   .must(new QueryStringQueryBuilder("大师").field("description"));//QueryStringQueryBuilder是单个字段的搜索条件,相当于组织sql的  where后面的   字段名=字段值
                   .should(new QueryStringQueryBuilder("3").field("dfsfs")) 
		 			.must(QueryBuilders .termQuery("dfsfs", "里"));//关于QueryStringQueryBuilder及termQuery等的使用可以使用es插件head来进行操作体会个中query的不同
		 builder.setQuery(qb2);
		 SearchResponse responsesearch = builder.execute().actionGet(); 
		 System.out.println(""+responsesearch);
		try{String jsondata= responsesearch.getHits().getHits()[0].getSourceAsString();
		System.out.println("搜索出来的数据jsondata-- "+jsondata);
		}catch(Exception es){
			
		}
	}*/
	/*@Autowired
	private ClientEliasticSearchUtils cc;*/
	@Test
	public void preIndex() throws IOException, IllegalAccessException, InvocationTargetException, IntrospectionException{
		/*User user=new User();
		user.setAddress("111");
		user.setEmail("333");
		BeanToMapUtil.convertBean(user);*/
		/*cll.getReMappingData2(User.class);*/
		
		
		
		
		
		/*cc.createPreIndex();*/
		/*User user=new User();
		elasticSearch.createPreIndex(user);
		System.out.println("-------end--------");*/
	}
	
}
