package com.lhy.utils;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.JestResult;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.core.Delete;
import io.searchbox.core.Get;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.indices.mapping.PutMapping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.client.Requests;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
/**
 * @author: 李慧勇
 * @description:ElasticSearch示例
 * @mail:jdlglhy@163.com
 * @2015年7月21日
 * @version 1.0
 * @param <T>
 */
@Component
public class ElasticSearch {
	
	
     private static JestClient jestClient;
	 
	 private static final String PRE="alq_";
	 
	 static{
		    String connectionUrl = "http://localhost:9200";
			JestClientFactory factory = new JestClientFactory();
			factory.setHttpClientConfig(new HttpClientConfig
			       .Builder(connectionUrl)
			       .multiThreaded(true)
			       .build());
			jestClient = factory.getObject();
	 }
	 
	 /**
	     * @Title: createIndex
	     * @Description: 创建索引（命名规则为：index:alq_+"实体名小写"，type:实体名；例如：User-index:alq_user,type:user）
	     * @param: @param object
	     * @return: boolean   
	 * @throws IOException 
	     */
	    public boolean createPreIndex(Object object) throws IOException{
	    	Class<?> clazz=object.getClass();
	    	String indexName=getIndex(clazz);
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
			PutMappingRequest mappingRequest = Requests.putMappingRequest("index").type("index").source(mapping);
			Index index = new Index.Builder(mappingRequest).build(); 
	        try {
	        	JestResult result=jestClient.execute(index);
	        	return result.isSucceeded();
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
	     }
	 
	 
	 
	 
	 
     
     /**
     * @Title: createIndex
     * @Description: 创建索引（命名规则为：index:alq_+"实体名小写"，type:实体名；例如：User-index:alq_user,type:user）
     * @param: @param object
     * @return: boolean   
     */
    public boolean createIndex(Object object){
    	Class<?> clazz=object.getClass();
    	String indexName=getIndex(clazz);
    	Index index = new Index.Builder(object).index(PRE+indexName).type(indexName).build(); 
        try {
        	JestResult result=jestClient.execute(index);
        	return result.isSucceeded();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
     }
    /**
     * @Title: getClassById
     * @Description: 根据实体映射索引，搜索实体
     * @param: clazz
     * @param: id（@jestId）
     * @param: @return   
     * @return: Object   
     * @throws
     */
    public Object getClassById(Class<?> clazz, String id) {
        Get get = new Get.Builder(PRE+getIndex(clazz), id).build();
        try {
            JestResult rs = jestClient.execute(get);
            Object o=rs.getSourceAsObject(clazz);
            return o;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * @Title: deleteIndexById
     * @Description: 删除索引上的节点（根据id删除）
     * @param: @param clazz
     * @param: @param id
     * @param: @return   
     * @return: boolean   
     * @throws
     */
    public boolean deleteIndexById(Class<?> clazz, String id){
    	if(getClassById(clazz,id) == null){
    		return true;
    	}
    	String indexName=getIndex(clazz).toLowerCase();
    	try {
	    	 Delete delete=new Delete.Builder(id)
	        .index(PRE+indexName)
	        .type(indexName).build();
	    	 JestResult result=jestClient.execute(delete);
	    	 return result.isSucceeded();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
    }
    /**
     * @Title: searchByQueryString
     * @Description: 分页数据搜索
     * @param: @param qs
     * @param: @param clazz
     * @param: @param page
     * @param: @param rows
     * @param: @return   
     * @return: List<T>   
     * @throws
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> searchByQueryString(String qs,Class<?> clazz,Integer page,Integer rows){
    	String indexName=getIndex(clazz);
    	List<T> list=new ArrayList<T>();
    	try {
    		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
    		/*QueryStringQueryBuilder queryBuilder=new QueryStringQueryBuilder(qs);*/
    		/*searchSourceBuilder.query(queryBuilder.analyzer("ik").toString()).from((page-1)*rows).size(rows);*/
    		searchSourceBuilder.query(QueryBuilders.queryStringQuery(qs).analyzeWildcard(true).analyzer("ik")).from((page-1)*rows).size(rows);
/*    		searchSourceBuilder.query(QueryBuilders.fuzzyLikeThisQuery().likeText(qs).toString()).from((page-1)*rows).size(rows);*/
    		Search search = new Search.Builder(searchSourceBuilder.toString())
    	                                .addIndex(PRE+indexName)
    	                                .addType(indexName)
    	                                .build();
			SearchResult result = jestClient.execute(search);
			if(result.isSucceeded()){
				list=(List<T>) result.getSourceAsObjectList(clazz);
			}
			return list;
		} catch (IOException e) {
			return null;
		}
    }
     /**
     * @Title: getIndex
     * @Description: 索引命名规则
     * @param: @param object
     * @return: String   
     */
    public String getIndex(Class<?> clazz){
    	 String className=clazz.getName().toString();
    	 String index=StringUtils.substring(className, StringUtils.lastIndexOf(className, ".")+1, className.length());
    	 return index.toLowerCase();
     }
	
}
