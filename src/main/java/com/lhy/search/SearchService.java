package com.lhy.search;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Bulk;
import io.searchbox.core.Delete;
import io.searchbox.core.Get;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.indices.CreateIndex;
import io.searchbox.indices.DeleteIndex;
import io.searchbox.indices.IndicesExists;

import java.io.IOException;
import java.util.List;

import org.elasticsearch.action.deletebyquery.DeleteByQueryAction;
import org.elasticsearch.action.deletebyquery.DeleteByQueryRequest;
import org.elasticsearch.action.deletebyquery.DeleteByQueryResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lhy.entity.User;

/**
 * es简单服务接口
 * 
 * @author hk
 * 
 *         2013-1-12 下午11:47:16
 */
@Service 
public class SearchService { 
 
 
     /*final static Logger logger = LoggerFactory.getLogger(SearchService.class); 
     @Autowired 
     JestClient jestClient; 
     *//**
     * @Title: indexSampleArticles
     * @Description: 创建索引
     * @param:    
     * @return: void   
     * @throws
     *//*
    public void indexSampleArticles() { 
 
 
         Article article1 = new Article();
		 article1.setId(1L); 
         article1.setAuthor("lhy"); 
         article1.setContent("lihuiyong test 搜索."); 
 
         Article article2 = new Article(); 
         article2.setId(2L); 
         article2.setAuthor("cxf"); 
         article2.setContent("looking test 搜索"); 
 
 
         try { 
             // Delete articles index if it is exists 
             //DeleteIndex deleteIndex = new DeleteIndex.Builder("articles").build(); 
             //jestClient.execute(deleteIndex); 
        	 
        	 
 
             IndicesExists indicesExists = new IndicesExists.Builder("articles").build(); 
             JestResult result = jestClient.execute(indicesExists); 
 
 
             if (!result.isSucceeded()) { 
                 // Create articles index 
                 CreateIndex createIndex = new CreateIndex.Builder("articles").build(); 
                 jestClient.execute(createIndex); 
             } 

 
            *//** 
              *  if you don't want to use bulk api use below code in a loop. 
              * 
             *  Index index = new Index.Builder(source).index("articles").type("article").build(); 
              *  jestClient.execute(index); 
              * 
             *//* 
             Bulk bulk = new Bulk.Builder() 
                     .addAction(new Index.Builder(article1).index("articles").type("article").build()) 
                     .addAction(new Index.Builder(article2).index("articles").type("article").build()) 
                     .build(); 
             result = jestClient.execute(bulk); 

 
             System.out.println(result.getJsonString()); 
 
 
         } catch (IOException e) { 
             logger.error("Indexing error", e); 
         } catch (Exception e) { 
             logger.error("Indexing error", e); 
         } finally{
        	 jestClient.shutdownClient();
         }
 
 
     } 

     *//**
     * @Title: searchArticles
     * @Description: 搜索(.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
     *  .setQuery(QueryBuilders.termQuery("name", "fox")) // Query 
     *  .setFilter(FilterBuilders.rangeFilter("age").from(20).to(30)) // Filter 
     *  .setFrom(0).setSize(60).setExplain(true).execute().actionGet(); )
     * @param: @param param
     * @param: @return   
     * @return: List<Article>   
     * @throws
     *//*
    public List<Article> searchArticles(String param) { 
        try { 
             SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder(); 
             searchSourceBuilder.query(QueryBuilders.matchAllQuery()); 
             Search search = new Search.Builder(searchSourceBuilder.toString()) 
                     .build();  
             JestResult result = jestClient.execute(search); 
             System.out.println(result.getJsonString());
             return result.getSourceAsObjectList(Article.class); 
         } catch (IOException e) { 
             logger.error("Search error", e); 
         } catch (Exception e) { 
             logger.error("Search error", e); 
         } finally{
        	 jestClient.shutdownClient();
         }
         return null; 
     } 
    根据id搜索
     public void get(String indexName, String query) {
         Get get = new Get.Builder(indexName, query).build();
         try {
             JestResult rs = jestClient.execute(get);
             System.out.println(rs.getJsonString());
             jestClient.shutdownClient();
         } catch (IOException e) {
             e.printStackTrace();
         }
     }
     添加单挑记录
     public void add() {
	         User user = new User();
			 user.setId(1000L); 
	         user.setAddress("张厚门");
	         user.setEmail("1212121门1");
    	     Index index = new Index.Builder(user).index("user").type("user").build(); 
             try {
				jestClient.execute(index);
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				jestClient.shutdownClient();
			}
     }
     
     *//**
     * @Title: indexBulk
     * @Description: 批量添加
     * @param: @param indexName   
     * @return: void   
     * @throws
     *//*
    public void indexBulk(String indexName) {
        try {
            // drop
            DeleteIndex dIndex = new DeleteIndex(new DeleteIndex.Builder(
                    indexName));
            jestClient.execute(dIndex);
            // create
            CreateIndex cIndex = new CreateIndex(new CreateIndex.Builder(
                    indexName));
            jestClient.execute(cIndex);
            // add doc
            Bulk.Builder bulkBuilder = new Bulk.Builder();
            for (int i = 10; i < 20; i++) {
                User user = new User();
                user.setId(new Long(i));
                user.setAddress("福建厦门"+i);
                user.setEmail("4558656@qq.com"+i);
                user.setRealName("lhy"+i);
                Index index = new Index.Builder(user).index(indexName)
                        .type(indexName).build();
                bulkBuilder.addAction(index);
            }
            jestClient.execute(bulkBuilder.build());
            jestClient.shutdownClient();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    根据id删除索引节点
    public void deleteIndexById(){
    	try {
	    	 // Delete articles index if it is exists 
	    	 Delete delete=new Delete.Builder("1000")
	        .index("user")
	        .type("user").build();
	    	 JestResult result=jestClient.execute(delete);
	    	 System.out.println(result.isSucceeded()+"------------");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
          jestClient.shutdownClient();
       
    }
    ------------------------------------------------------------------
    
    
    public void searchApi(){
    	try {
    		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
    		分页排序查询数据
    		//searchSourceBuilder.query(QueryBuilders.queryStringQuery("建州").toString()).sort("id", SortOrder.ASC).from(5).size(5);
    		searchSourceBuilder.query(QueryBuilders.queryStringQuery("建州").toString()).sort("id", SortOrder.ASC).from(5).size(5);
    		Search search = new Search.Builder(searchSourceBuilder.toString())
    	                                // multiple index or types can be added.
    	                                .addIndex("user")
    	                                .addIndex("user")
    	                                .build();
			SearchResult result = jestClient.execute(search);
			List<User> list=result.getSourceAsObjectList(User.class);
			for(User user:list){
				System.out.println(user.getAddress());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }*/
    
    
    
    
    
 } 
