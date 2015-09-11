package com.lhy.search;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.AndFilterBuilder;
import org.elasticsearch.index.query.BoolFilterBuilder;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.ExistsFilterBuilder;
import org.elasticsearch.index.query.FilterBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.NotFilterBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryFilterBuilder;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.facet.FacetBuilders;
import org.elasticsearch.search.facet.terms.TermsFacet;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.lhy.annotation.ElasticsSearchAno;
import com.lhy.utils.build.MySearchOption;
import com.lhy.utils.build.MySearchOption.DataFilter;
import com.lhy.utils.build.MySearchOption.SearchLogic;
import com.lhy.utils.build.QueryBuildFactory;

/**
 * @author: 李慧勇
 * @description:搜索工具类
 * @mail:jdlglhy@163.com
 * @2015年7月24日
 * @version 1.0
 */
@Component
public class ClientEliasticSearchUtils {

	@Autowired
	private Client client;
	
	private static final String PRE="alq_";
	
	private static final String CLASS="class";
	
	private static Logger logger = LoggerFactory.getLogger(ClientEliasticSearchUtils.class);
	
	
	
	/**
	 * @Title: createPreIndex
	 * @Description: 预定义索引创建；
	 * 				自定义字段属性分词信息；
	 * 				此方法只需要在test里面运行一次即可
	 * @param: @throws IOException   
	 * @return: void   
	 * @throws 
	 */
	public boolean createPreIndex(Class<?> clazz){
    	String indexName=getIndex(clazz);
    	try {
		CreateIndexRequestBuilder cib=client.admin().indices().prepareCreate(PRE+indexName);
		XContentBuilder mapping=getReMappingData(clazz);
			cib.addMapping(indexName, mapping);
			cib.execute().actionGet();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			client.close();
			logger.error("预定义索引创建失败");
			return false;
		}  
	}
	
	
	/**
	 * @Title: createIndexData
	 * @Description:  添加索引数据(方法一：适合简单对象添加)
	 * @param: @param object
	 * @param: @param uid   
	 * @return: void   
	 * @throws
	 */
	public boolean createIndexObjectData(Object object,String uid){
		Class<?> clazz=object.getClass();
    	String indexName=getIndex(clazz);
		try {
			/*IndexResponse response = */client.prepareIndex(PRE+indexName, indexName ,uid).setRefresh(true)
			        .setSource(getXContentBuilderData(object))
			        .execute()
			        .actionGet();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("添加索引数据失败");
			return false;
		}
	}
	/**
	 * @Title: createIndexJsonData
	 * @Description:  添加索引数据(方法二：手动添加json数据)
	 * jsonData
	 * @param: @param object
	 * @param: @param uid   
	 * @return: void   
	 * @throws
	 */
	public boolean createIndexJsonData(Class<?> clazz,String jsonData,String uid){
    	String indexName=getIndex(clazz);
		try {
			client.prepareIndex(PRE+indexName, indexName ,uid).setRefresh(true)
			        .setSource(jsonData)
			        .execute()
			        .actionGet();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("添加索引数据失败");
			return false;
		}
	}
	/**
	 * @throws IOException 
	 * @throws IntrospectionException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @param <T>
	 * @Title: updateIndexData
	 * @Description:  修改索引数据
	 * @param: @param object
	 * @param: @param uid   
	 * @return: void   
	 * @throws
	 */
	public boolean updataIndexData(Object object,String uid){
		Class<?> clazz=object.getClass();
		String indexName=getIndex(clazz);
		try{
			UpdateRequest updateRequest = new UpdateRequest().index(PRE+indexName).type(indexName).id(uid);
			updateRequest.doc(getXContentBuilderData(object));
			/*UpdateResponse updateResponse=*/client.update(updateRequest).get();
			return true;
		}catch(Exception ex){
			return false;
		}
	}
	/**
	 * @Title: getXContentBuilderDate
	 * @Description: 返回索引数据
	 * @param: @param object
	 * @param: @throws IntrospectionException
	 * @param: @throws IOException
	 * @param: @throws IllegalAccessException
	 * @param: @throws IllegalArgumentException
	 * @param: @throws InvocationTargetException   
	 * @return: void   
	 * @throws
	 */
	public XContentBuilder getXContentBuilderData(Object object) throws IntrospectionException, IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		XContentBuilder mapping = XContentFactory.jsonBuilder().startObject();
		Class<?> type = object.getClass();
		BeanInfo beanInfo = Introspector.getBeanInfo(type); 
        PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
        for (int i = 0; i< propertyDescriptors.length; i++) { 
            PropertyDescriptor descriptor = propertyDescriptors[i]; 
            String propertyName = descriptor.getName();
            if(!propertyName.equals(CLASS.toString())){
           	 Method readMethod = descriptor.getReadMethod();
           	 ElasticsSearchAno esa=readMethod.getAnnotation(ElasticsSearchAno.class);
           	 if(esa == null){
           		 Object result = readMethod.invoke(object, new Object[0]); 
           		 mapping.field(propertyName, result);
           	 }
            }
        }
        mapping.endObject();
        return mapping;
	}
	/**
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @Title: eliasticSearchQuery
	 * @Description: 搜索；此方法待优化（查询固定字段）
	 * @param: @param qs 查询条件
	 * @param: @param clazz 返回的实体类
	 * @param: @param page
	 * @param: @param rows
	 * @param: @throws InstantiationException
	 * @param: @throws IllegalAccessException
	 * @param: @throws InvocationTargetException
	 * @param: @throws IntrospectionException   
	 * @return: List<T>   
	 * @throws
	 */
	public <T> List<T> eliasticSearchQuery(String qs,Class<T> clazz,Integer page,Integer rows,String sortField,String sortType) throws InstantiationException, IllegalAccessException{
		    List<T> result=new ArrayList<T>(); 
			String indexName=getIndex(clazz);
			/*搜索productindex,prepareSearch(String... indices)注意该方法的参数,可以搜索多个索引*/
			SearchRequestBuilder builder= client.prepareSearch(PRE+indexName)
					.setTypes(indexName) 
					.setSearchType(SearchType.DEFAULT)
					.setFrom((page-1)*rows)  
					.setSize(rows); 
			QueryStringQueryBuilder queryStringQueryBuilder=getneekIkQueryFields(clazz,qs);
			 //BoolQueryBuilder boolQueryBuilder=QueryBuilders.boolQuery();
			//组装分词字段查询条件(必须)
			//boolQueryBuilder.must(queryStringQueryBuilder);
			/*//拼接must条件（这里拼接）
			HashMap<String, Object> mustMap=new HashMap<String,Object>();
			FilterBuilders.boolFilter().must(filterBuilder)*/
			
			
			
			builder.setQuery(QueryBuilders.filteredQuery(QueryBuilders.boolQuery().
			//QueryBuilders.matchQuery("mydefine", "李慧勇")默认是一元分词，这里要设置不分词（精确搜索）
			must(queryStringQueryBuilder)/*.must(QueryBuilders.termQuery("mydefine", "张三人"))*/, 
			//搜索范围
			FilterBuilders.andFilter(FilterBuilders.boolFilter().should(FilterBuilders.termFilter("time", "2004")).should(FilterBuilders.termFilter("time", "2015")),/*FilterBuilders.boolFilter().mustNot(FilterBuilders.termFilter("mydefine", "李慧勇")),*/FilterBuilders.rangeFilter("square").from("110").to("200"),FilterBuilders.rangeFilter("id").from("0").to("50000"))))
			//增加统计功能
			.addFacet(FacetBuilders.termsFacet("termFacet").fields("id"))
			
			
			
			
			//增加排序功能
			/*.addSort("time", SortOrder.ASC)*/;
					
			//增加排序功能
			if (!(StringUtils.isEmpty(sortField) || StringUtils.isEmpty(sortType))) {
                SortOrder sortOrder="desc".equalsIgnoreCase(sortType)?SortOrder.DESC:SortOrder.ASC;
                builder.addSort(sortField, sortOrder);
            }
			SearchResponse responsesearch = builder.execute().actionGet();
			TermsFacet f = (TermsFacet) responsesearch.getFacets().facetsAsMap().get("termFacet");
			System.out.println("总数量："+f.getTotalCount());
			System.out.println(f.toString());
			System.out.println("-------华丽分割线-----------");
			SearchHits searchHists = responsesearch.getHits();
			for (SearchHit hit : searchHists) {
				System.out.println(hit.getSourceAsString());
				T t = (T) clazz.newInstance();
				t=JSON.toJavaObject(JSONObject.parseObject(hit.getSourceAsString()), clazz);
				result.add(t);
			}
			return result;
	}
	/**
	 * @Title: getByIndexId
	 * @Description: 获取索引记录
	 * @param: @param clazz
	 * @param: @param uid
	 * @param: @return
	 * @param: @throws InstantiationException
	 * @param: @throws IllegalAccessException   
	 * @return: Object   
	 * @throws
	 */
	public <T> Object getByIndexId(Class<T> clazz,String uid) throws InstantiationException, IllegalAccessException {
		String indexName=getIndex(clazz);
		GetResponse response = client.prepareGet(PRE+indexName, indexName, uid).execute().actionGet();
		// 下面是不在多线程操作,他默认为.setOperationThreaded(true)
		// GetResponse response = client.prepareGet(PRE+indexName, indexName, uid).setOperationThreaded(false).execute().actionGet();
		T t=(T) clazz.newInstance();
		if(response.isExists() && !response.isSourceEmpty()){
			t=JSON.toJavaObject(JSONObject.parseObject(response.getSourceAsString()), clazz);
		}
		return t;
	}
	/**
	 * @Title: deleteIndex
	 * @Description: 删除索引
	 * @param: @param clazz
	 * @param: @param uid
	 * @return: boolean  返回true说明索引存在删除成功，否则则索引不存在
	 * @throws
	 */
	public boolean deleteIndex(Class<?> clazz,String uid){
		String indexName=getIndex(clazz);
		DeleteResponse response = client.prepareDelete(PRE+indexName, indexName, uid).execute().actionGet();
		// 下面是不在多线程操作,他默认为.setOperationThreaded(true)
		// DeleteResponse response = client.prepareDelete(PRE+indexName, indexName, "100").setOperationThreaded(false).execute().actionGet();
		return response.isFound();
	}
	/**
	 * @param <T>
	 * @Title: getneekIkQueryFields
	 * @Description: 组装需要分词的字段作用范围
	 * @param: @param clazz
	 * @param: @return   
	 * @return: List<String>   
	 * @throws
	 */
	public <T> QueryStringQueryBuilder getneekIkQueryFields(Class<T> clazz,String qs){
		QueryStringQueryBuilder qsqb=new QueryStringQueryBuilder(qs);
		Field[] fields = clazz.getDeclaredFields();  
		for(Field field:fields){
			ElasticsSearchAno meta = field.getAnnotation(ElasticsSearchAno.class);  
			if(meta!=null && meta.isNeedIkSearch()){ 
				qsqb.field(field.getName().toString());
			}  
		}
		return qsqb;
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * @Title: createIndexDate
	 * @Description: 添加索引数据；若字段在上面的方法中mapping定义了,所以该字段就有了自定义的属性；若字段mapping中没有定义,那么该字段的属性使用es默认的
	 * @param: @param object
	 * @param: @param uuid   
	 * @return: void   
	 * @throws
	 */
	public void createIndexDate(Object object,String uuid){
		Class<?> clazz=object.getClass();
    	String indexName=getIndex(clazz);
		try {
			   /*IndexResponse response = */client.prepareIndex(PRE+indexName, indexName,uuid)
					.setSource(XContentFactory.jsonBuilder()
							.startObject()
							.field("title", "abcd1")
							.field("description", "中国人nick3")
							.field("price", 232 )
							.field("onSale",true)
							.field("type",2)
							.field("createDate",new Date().getTime())
							.field("dfsfs","哈哈大师")
							.endObject()
							)
							.execute()
							.actionGet();
		} catch (ElasticsearchException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * @Title: getReMappingData
	 * @Description: 构建预定义索引数据（主要是方便分词）
	 * @param: @param object
	 * @param: @return
	 * @param: @throws IOException
	 * @param: @throws IntrospectionException   
	 * @return: XContentBuilder   
	 * @throws
	 */
	public XContentBuilder getReMappingData(Class<?> clazz) throws IOException, IntrospectionException{
		 XContentBuilder mapping;
			mapping = XContentFactory.jsonBuilder()
					.startObject()
						.startObject("properties");
			Field[] fields=clazz.getDeclaredFields();
			for(Field field:fields){
				ElasticsSearchAno esa=field.getAnnotation(ElasticsSearchAno.class);
				if(esa !=null && esa.isElasticsSearch()){
					String typeProperty=field.getType().getName().toLowerCase();
					 if(esa.isNeedIkSearch()){
            			 mapping.startObject(field.getName()).field("type",typeProperty.substring(typeProperty.lastIndexOf(".")+1, typeProperty.length()))
            			 .field("indexAnalyzer", "ik")  
						 .field("searchAnalyzer", "ik").endObject()  ;
            		 }else{
            			 mapping.startObject(field.getName()).field("type",typeProperty.substring(typeProperty.lastIndexOf(".")+1, typeProperty.length())).field("index","not_analyzed").endObject();
            		 }
				}
			}
			mapping.endObject().endObject(); 
			return mapping;
	}
		 /*Class<?> type = object.getClass();
		 BeanInfo beanInfo = Introspector.getBeanInfo(type); 
         PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
         for (int i = 0; i< propertyDescriptors.length; i++) { 
             PropertyDescriptor descriptor = propertyDescriptors[i]; 
             String propertyName = descriptor.getName();
             if(!propertyName.equals("class")){
            	 Method readMethod = descriptor.getReadMethod();
            	 ElasticsSearchAno esa=readMethod.getAnnotation(ElasticsSearchAno.class);
            	 if(esa != null && esa.isElasticsSearch()){
            		 if(esa.isNeedIkSearch()){
            			 mapping.startObject(propertyName).field("type",SearchTypeProperty.valueOf(descriptor.getPropertyType().getName().toLowerCase()))
            			 .field("indexAnalyzer", "ik")  
						 .field("searchAnalyzer", "ik").endObject()  ;
            		 }else{
            			 mapping.startObject(propertyName).field("type",SearchTypeProperty.valueOf(descriptor.getPropertyType().getName().toLowerCase())).endObject();
            		 }
            	 }
             }*/
        /* }*/
        /* mapping.endObject().endObject();    
         return mapping;    */    
	/*}*/
	
	
	/*XContentFactory.jsonBuilder()
    .startObject()
        .field("title", "abcd1")//该字段在上面的方法中mapping定义了,所以该字段就有了自定义的属性,比如 type等
        .field("description", "中国人nick3")
        .field("price", 232 )
        .field("onSale",true)
        .field("type",2)
        .field("createDate",new Date().getTime())
        .field("dfsfs","哈哈大师")//该字段在上面方法中的mapping中没有定义,所以该字段的属性使用es默认的.
    .endObject()*/
	/**
	 * @Title: getReMappingData
	 * @Description: 构建预定义索引数据
	 * @param: @param object
	 * @param: @return
	 * @param: @throws IOException
	 * @param: @throws IntrospectionException   
	 * @return: XContentBuilder   
	 * @throws
	 */
	public XContentBuilder getReMappingData2(Object object) throws IOException, IntrospectionException{
		 XContentBuilder mapping;
			mapping = XContentFactory.jsonBuilder()
					.startObject()
						.startObject("properties"); 
		 Class<?> type = object.getClass();
		 BeanInfo beanInfo = Introspector.getBeanInfo(type); 
         PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
         for (int i = 0; i< propertyDescriptors.length; i++) { 
             PropertyDescriptor descriptor = propertyDescriptors[i]; 
             String propertyName = descriptor.getName();
             if(!propertyName.equals("class")){
            	 Method readMethod = descriptor.getReadMethod();
            	 ElasticsSearchAno esa=readMethod.getAnnotation(ElasticsSearchAno.class);
            	 if(esa != null && esa.isElasticsSearch()){
            		 String typePorperty=descriptor.getPropertyType().getName();
            		 if(esa.isNeedIkSearch()){
            			 mapping.startObject(propertyName).field("type",typePorperty.substring(typePorperty.lastIndexOf(".")+1, typePorperty.length()).toLowerCase())
            			 .field("indexAnalyzer", "ik")  
						 .field("searchAnalyzer", "ik").endObject()  ;
            		 }else{
            			 mapping.startObject(propertyName).field("type",typePorperty.substring(typePorperty.lastIndexOf(".")+1, typePorperty.length()).toLowerCase()).endObject();
            		 }
            	 }
             }
         }
         mapping.endObject().endObject();    
         return mapping;        
	}
             
             /*if (!propertyName.equals("class")) { 
                 Method readMethod = descriptor.getReadMethod();
                 if(readMethod.getAnnotation(ElasticsSearchAno.class) != null){
                 	 System.out.println(readMethod.getAnnotation(ElasticsSearchAno.class).isElasticsSearch());
                 }
                 Object result = readMethod.invoke(object, new Object[0]); 
                 if (result != null) { 
                     returnMap.put(propertyName, result); 
                     System.out.println(propertyName);
                 } else { 
                 	 System.out.println(propertyName);
                     returnMap.put(propertyName, ""); 
                 } 
             } */
         
         
         
		 /*XContentBuilder mapping;
			mapping = XContentFactory.jsonBuilder()
					.startObject()
						.startObject("properties")*/
						
							/*.startObject("title").field("type", "string").field("store", "yes").endObject()    
							.startObject("description").field("type", "string").field("indexAnalyzer", "ik")  
							.field("searchAnalyzer", "ik").endObject()  
							.startObject("price").field("type", "double").endObject()  
							.startObject("onSale").field("type", "boolean").endObject()  
							.startObject("type").field("type", "integer").endObject()  
							.startObject("createDate").field("type", "date").field("format","YYYYMMddhhMMSS").endObject()*/
							
							
						/*.endObject()
					.endObject();*/
	
	
	
	
	
	
	
	
	
	
	
	
	
			
	 public List<Map<String, Object>> simpleSearch(String indexName
	            , HashMap<String, Object[]> searchContentMap, SearchLogic searchLogic
	            , HashMap<String, Object[]> filterContentMap, SearchLogic filterLogic
	            , int from, int offset, String sortField, String sortType)
	    {
	        if (offset <= 0) {
	            return null;
	        }
	        try {
	            QueryBuilder queryBuilder = null;
	            queryBuilder = this.createQueryBuilder(searchContentMap, searchLogic);
	            queryBuilder = this.createFilterBuilder(filterLogic, queryBuilder, searchContentMap, filterContentMap);
	            SearchRequestBuilder searchRequestBuilder = client.prepareSearch(PRE+indexName).setTypes(indexName).setSearchType(SearchType.DEFAULT)
	                    .setQuery(queryBuilder).setFrom(from).setSize(offset).setExplain(true);
	            if (sortField == null || sortField.isEmpty() || sortType == null || sortType.isEmpty()) {
	                /*如果不需要排序*/
	            }
	            else {
	                /*如果需要排序*/
	                org.elasticsearch.search.sort.SortOrder sortOrder = sortType.equals("desc") ? org.elasticsearch.search.sort.SortOrder.DESC : org.elasticsearch.search.sort.SortOrder.ASC;
	                searchRequestBuilder = searchRequestBuilder.addSort(sortField, sortOrder);
	            }
	            searchRequestBuilder = this.createHighlight(searchRequestBuilder, searchContentMap);
	            this.logger.debug(searchRequestBuilder.toString());
	            SearchResponse searchResponse = searchRequestBuilder.execute().actionGet();
	            for (SearchHit hit : searchResponse.getHits()) {
					System.out.println(hit.getSourceAsString());
				}
	            //return this.getSearchResult(searchResponse);
	            return null;
	        }
	        catch (Exception e) {
	            this.logger.error(e.getMessage());
	        }
	        return null;
	    }
	
	 /*
	     * 创建搜索条件
	     * */
	    private QueryBuilder createQueryBuilder(HashMap<String, Object[]> searchContentMap, SearchLogic searchLogic) {
	        try {
	            if (searchContentMap == null || searchContentMap.size() ==0) {
	                return null;
	            }
	            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
	            Iterator<Entry<String, Object[]>> iterator = searchContentMap.entrySet().iterator();
	            /*循环每一个需要搜索的字段和值*/
	            while (iterator.hasNext()) {
	                Entry<String, Object[]> entry = iterator.next();
	                String field = entry.getKey();
	                Object[] values = entry.getValue();
	                /*排除非法的搜索值*/
	                if (!this.checkValue(values)) {
	                    continue;
	                }
	                /*获得搜索类型*/
	                MySearchOption mySearchOption = this.getSearchOption(values);
	                QueryBuilder queryBuilder = this.createSingleFieldQueryBuilder(field, values, mySearchOption);
	                if (queryBuilder != null) {
	                    if (searchLogic == SearchLogic.should) {
	                        /*should关系，也就是说，在A索引里有或者在B索引里有都可以*/
	                        boolQueryBuilder = boolQueryBuilder.should(queryBuilder);
	                    }
	                    else {
	                        /*must关系，也就是说，在A索引里有，在B索引里也必须有*/
	                        boolQueryBuilder = boolQueryBuilder.must(queryBuilder);
	                    }
	                }
	            }
	            return boolQueryBuilder;
	        }
	        catch (Exception e) {
	            this.logger.error(e.getMessage());
	        }
	        return null;
	    }
	    /*
	     * 创建过滤条件
	     * */
	    private QueryBuilder createFilterBuilder(SearchLogic searchLogic, QueryBuilder queryBuilder, HashMap<String, Object[]> searchContentMap, HashMap<String, Object[]> filterContentMap) throws Exception
	    {
	        try {
	            Iterator<Entry<String, Object[]>> iterator = searchContentMap.entrySet().iterator();
	            AndFilterBuilder andFilterBuilder = null;
	            while (iterator.hasNext()) {
	                Entry<String, Object[]> entry = iterator.next();
	                Object[] values = entry.getValue();
	                /*排除非法的搜索值*/
	                if (!this.checkValue(values)) {
	                    continue;
	                }
	                MySearchOption mySearchOption = this.getSearchOption(values);
	                if (mySearchOption.getDataFilter() == DataFilter.exists) {
	                    /*被搜索的条件必须有值*/
	                    ExistsFilterBuilder existsFilterBuilder = FilterBuilders.existsFilter(entry.getKey());
	                    if (andFilterBuilder == null) {
	                        andFilterBuilder = FilterBuilders.andFilter(existsFilterBuilder);
	                    }
	                    else {
	                        andFilterBuilder = andFilterBuilder.add(existsFilterBuilder);
	                    }
	                }
	            }
	            if (filterContentMap == null || filterContentMap.isEmpty()) {
	                /*如果没有其它过滤条件，返回*/
	                return QueryBuilders.filteredQuery(queryBuilder, andFilterBuilder);
	            }
	            /*构造过滤条件*/
	            QueryFilterBuilder queryFilterBuilder = FilterBuilders.queryFilter(this.createQueryBuilder(filterContentMap, searchLogic));
	            /*构造not过滤条件，表示搜索结果不包含这些内容，而不是不过滤*/
	            NotFilterBuilder notFilterBuilder = FilterBuilders.notFilter(queryFilterBuilder);
	            return QueryBuilders.filteredQuery(queryBuilder, FilterBuilders.andFilter(andFilterBuilder, notFilterBuilder));
	        }
	        catch (Exception e) {
	            this.logger.error(e.getMessage());
	        }
	        return null;
	    }
	    /*简单的值校验*/
	    private boolean checkValue(Object[] values) {
	        if (values == null) {
	            return false;
	        }
	        else if (values.length == 0) {
	            return false;
	        }
	        else if (values[0] == null) {
	            return false;
	        }
	        else if (values[0].toString().trim().isEmpty()) {
	            return false;
	        }
	        return true;
	    }
	    /*获得搜索选项*/
	    private MySearchOption getSearchOption(Object[] values) {
	        try {
	            for (Object item : values) {
	                if (item instanceof MySearchOption) {
	                    return (MySearchOption) item;
	                }
	            }
	        }
	        catch (Exception e) {
	            this.logger.error(e.getMessage());
	        }
	        return new MySearchOption();
	    }
	    private SearchRequestBuilder createHighlight(SearchRequestBuilder searchRequestBuilder, HashMap<String, Object[]> searchContentMap) {
	        Iterator<Entry<String, Object[]>> iterator = searchContentMap.entrySet().iterator();
	        /*循环每一个需要搜索的字段和值*/
	        while (iterator.hasNext()) {
	            Entry<String, Object[]> entry = iterator.next();
	            String field = entry.getKey();
	            Object[] values = entry.getValue();
	            /*排除非法的搜索值*/
	            if (!this.checkValue(values)) {
	                continue;
	            }
	            /*获得搜索类型*/
	            MySearchOption mySearchOption = this.getSearchOption(values);
	            if (mySearchOption.isHighlight()) {
	                /*
	                 * http://www.elasticsearch.org/guide/reference/api/search/highlighting.html
	                 *
	                 * fragment_size设置成1000，默认值会造成返回的数据被截断
	                 * */
	               /* searchRequestBuilder = searchRequestBuilder.addHighlightedField(field, 1000)
	                        .setHighlighterPreTags("<"+this.highlightCSS.split(",")[0]+">")
	                        .setHighlighterPostTags("</"+this.highlightCSS.split(",")[1]+">");*/
	            }
	        }
	        return searchRequestBuilder;
	    }
	    private QueryBuilder createSingleFieldQueryBuilder(String field, Object[] values, MySearchOption mySearchOption) {
	        try {
	            if (mySearchOption.getSearchType() == com.lhy.utils.build.MySearchOption.SearchType.range) {
	                /*区间搜索*/
	                return this.createRangeQueryBuilder(field, values);
	            }
	            //        String[] fieldArray = field.split(",");/*暂时不处理多字段[field1,field2,......]搜索情况*/
	            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
	            for (Object valueItem : values) {
	                if (valueItem instanceof MySearchOption) {
	                    continue;
	                }
	                QueryBuilder queryBuilder = null;
	                String formatValue = valueItem.toString().trim().replace("*", "");//格式化搜索数据
	                if (mySearchOption.getSearchType() == com.lhy.utils.build.MySearchOption.SearchType.term) {
	                    queryBuilder = QueryBuilders.termQuery(field, formatValue).boost(mySearchOption.getBoost());
	                }
	                else if (mySearchOption.getSearchType() == com.lhy.utils.build.MySearchOption.SearchType.querystring) {
	                    if (formatValue.length() == 1) {
	                        /*如果搜索长度为1的非数字的字符串，格式化为通配符搜索，暂时这样，以后有时间改成multifield搜索，就不需要通配符了*/
	                        if (!Pattern.matches("[0-9]", formatValue)) {
	                            formatValue = "*"+formatValue+"*";
	                        }
	                    }
	                    QueryStringQueryBuilder queryStringQueryBuilder = QueryBuilders.queryString(formatValue).minimumShouldMatch(mySearchOption.getQueryStringPrecision());
	                    queryBuilder = queryStringQueryBuilder.field(field).boost(mySearchOption.getBoost());
	                }
	                if (mySearchOption.getSearchLogic() == SearchLogic.should) {
	                    boolQueryBuilder = boolQueryBuilder.should(queryBuilder);
	                }
	                else {
	                    boolQueryBuilder = boolQueryBuilder.must(queryBuilder);
	                }
	            }
	            return boolQueryBuilder;
	        }
	        catch (Exception e) {
	            this.logger.error(e.getMessage());
	        }
	        return null;
	    }
	    /**
	     * @Title: createRangeQueryBuilder
	     * @Description: 拼接区间搜索条件
	     * @param: @param field
	     * @param: @param values
	     * @param: @return   
	     * @return: RangeQueryBuilder   
	     * @throws
	     */
	    /*private RangeQueryBuilder createRangeQueryBuilder(String field, Object[] values) {
	        if (values.length == 1 || values[1] == null || values[1].toString().trim().isEmpty()) {
	            this.logger.warn("[区间搜索]必须传递两个值，但是只传递了一个值，所以返回null");
	            return null;
	        }
	        boolean timeType = false;
	        if (MySearchOption.isDate(values[0])) {
	            if (MySearchOption.isDate(values[1])) {
	                timeType = true;
	            }
	        }
	        String begin = "", end = "";
	        if (timeType) {
	            
	             * 如果时间类型的区间搜索出现问题，有可能是数据类型导致的：
	             *     （1）在监控页面（elasticsearch-head）中进行range搜索，看看什么结果，如果也搜索不出来，则：
	             *     （2）请确定mapping中是date类型，格式化格式是yyyy-MM-dd HH:mm:ss
	             *    （3）请确定索引里的值是类似2012-01-01 00:00:00的格式
	             *    （4）如果是从数据库导出的数据，请确定数据库字段是char或者varchar类型，而不是date类型（此类型可能会有问题）
	             * 
	            begin = MySearchOption.formatDate(values[0]);
	            end = MySearchOption.formatDate(values[1]);
	        }
	        else {
	            begin = values[0].toString();
	            end = values[1].toString();
	        }
	        return QueryBuilders.rangeQuery(field).from(begin).to(end);
	    }*/
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    /**
	     * @throws Exception 
		 * @throws IllegalAccessException 
		 * @throws InstantiationException 
		 * @Title: eliasticSearchQuery
		 * @Description: 搜索；此方法待优化（查询固定字段）
		 * @param: @param qs 查询条件
		 * @param: @param clazz 返回的实体类
		 * @param: @param page
		 * @param: @param rows
		 * @param: @throws InstantiationException
		 * @param: @throws IllegalAccessException
		 * @param: @throws InvocationTargetException
		 * @param: @throws IntrospectionException   
		 * @return: List<T>   
		 * @throws
		 */
		public <T> List<T> newEliasticSearchQuery(String qs,Class<T> clazz,Integer page,Integer rows,String sortField,String sortType) throws Exception{
			    List<T> result=new ArrayList<T>(); 
				String indexName=getIndex(clazz);
				/*搜索productindex,prepareSearch(String... indices)注意该方法的参数,可以搜索多个索引*/
				SearchRequestBuilder builder= client.prepareSearch(PRE+indexName)
						.setTypes(indexName) 
						.setSearchType(SearchType.DEFAULT)
						.setFrom((page-1)*rows)  
						.setSize(rows); 
				QueryStringQueryBuilder queryStringQueryBuilder=getneekIkQueryFields(clazz,qs);
				//组装分词字段查询条件(必须)
				//boolQueryBuilder.must(queryStringQueryBuilder);
				/*//拼接must条件（这里拼接）
				HashMap<String, Object> mustMap=new HashMap<String,Object>();
				FilterBuilders.boolFilter().must(filterBuilder)*/
				
				/*HashMap<String,Object[]> searchContentMap=new HashMap<String,Object[]>();
				searchContentMap.put("id", new Object[]{15000,20000,com.lhy.utils.build.MySearchOption.SearchType.range});
				searchContentMap.put("square", new Object[]{"110","200",com.lhy.utils.build.MySearchOption.SearchType.range});*/
				
				
				/*----------------------测试范围数据start---------------------*/
				HashMap<String, Object[]> rangeMap=new HashMap<String, Object[]>();
				rangeMap.put("id", new Object[]{"0","50000",new com.lhy.utils.build.MySearchOption(MySearchOption.SearchType.range
			            , MySearchOption.SearchLogic.must
			            , ""
			            , MySearchOption.DataFilter.all
			            , 1.0f
			            , 0)});
				rangeMap.put("square", new Object[]{"110","200",new com.lhy.utils.build.MySearchOption(MySearchOption.SearchType.range
			            , MySearchOption.SearchLogic.must
			            , ""
			            , MySearchOption.DataFilter.all
			            , 1.0f
			            , 0)});
				/*----------------------测试范围数据end---------------------*/
				
				/*----------------------测试过滤条件start---------------------*/
				HashMap<String, Object[]> filterMap=new HashMap<String, Object[]>();
				filterMap.put("mydefine", new Object[]{new com.lhy.utils.build.MySearchOption(MySearchOption.SearchType.term
			            , MySearchOption.SearchLogic.should
			            ,"李慧勇"
			            , MySearchOption.DataFilter.all
			            , 1.0f
			            , 0)});
				/*filterMap.put("time", new Object[]{new com.lhy.utils.build.MySearchOption(MySearchOption.SearchType.term
			            , MySearchOption.SearchLogic.should
			            , new Object[]{"2004","2015"}
			            ,""
			            , MySearchOption.DataFilter.all
			            , 1.0f
			            , 0)});*/
				/*----------------------测试过滤条件end---------------------*/
				
				
				/*----------------------华丽分割---------------------*/
				
				
				/*----------------------range搜索范围过滤 start---------------------*/
				BoolQueryBuilder boolQueryBuilder=QueryBuilders.boolQuery().must(queryStringQueryBuilder);
				Iterator<Entry<String, Object[]>> iterator = rangeMap.entrySet().iterator();
		            while (iterator.hasNext()) {
		                Entry<String, Object[]> entry = iterator.next();
		                Object[] values = entry.getValue();
		                if (!checkValue(values)) {
		                    continue;
		                }
		                
		                MySearchOption mySearchOption=getSearchOption(values);
		                System.out.println(values.toString()+"----------------------------11111111111111111");
		                if(mySearchOption.getSearchType().equals(MySearchOption.SearchType.range)){
		                	if(mySearchOption.getSearchLogic().equals(MySearchOption.SearchLogic.must)){
		                		boolQueryBuilder.must(createRangeQueryBuilder(entry.getKey(), values));
		                	}else if(mySearchOption.getSearchLogic().equals(MySearchOption.SearchLogic.mustNot)){
		                		boolQueryBuilder.mustNot(createRangeQueryBuilder(entry.getKey(), values));
		                	}else if(mySearchOption.getSearchLogic().equals(MySearchOption.SearchLogic.should)){
		                		boolQueryBuilder.should(createRangeQueryBuilder(entry.getKey(), values));
		                	}
		                }
		               /* ----------------------搜索范围过滤 end---------------------
		                
		                ----------------------term精确 搜索过滤 start---------------------*/
		                
		                
		                
		                /*----------------------term精确 搜索过滤 end---------------------*/
		            }
		            
		            AndFilterBuilder andFilterBuilder=null;
		            Iterator<Entry<String, Object[]>> iterator2 = filterMap.entrySet().iterator();
		            while (iterator2.hasNext()) {
		                Entry<String, Object[]> entry = iterator2.next();
		                Object[] values = entry.getValue();
		                if (!checkValue(values)) {
		                    continue;
		                }
		                System.out.println("key:"+entry.getKey());
		                MySearchOption mySearchOption=getSearchOption(values);
		                System.out.println("searchType:"+mySearchOption.getSearchType());
		                /*System.out.println(values.toString()+"----------------------------22222222222");
		                if(mySearchOption.getSearchType().equals(MySearchOption.SearchType.range)){
		                	if(mySearchOption.getSearchLogic().equals(MySearchOption.SearchLogic.must)){
		                		boolQueryBuilder.must(createRangeQueryBuilder(entry.getKey(), values));
		                	}else if(mySearchOption.getSearchLogic().equals(MySearchOption.SearchLogic.mustNot)){
		                		boolQueryBuilder.mustNot(createRangeQueryBuilder(entry.getKey(), values));
		                	}else if(mySearchOption.getSearchLogic().equals(MySearchOption.SearchLogic.should)){
		                		boolQueryBuilder.should(createRangeQueryBuilder(entry.getKey(), values));
		                	}
		                }*/
		               /* ----------------------搜索范围过滤 end---------------------
		                
		                ----------------------term精确 搜索过滤 start---------------------*/
		                
		                andFilterBuilder=createFilterQueryBuilder(entry.getKey(), values, mySearchOption, andFilterBuilder);
		                
		                /*----------------------term精确 搜索过滤 end---------------------*/
		            }
		            
		            
		            
		            
		            
		            builder.setQuery(QueryBuilders.filteredQuery(boolQueryBuilder,andFilterBuilder))
				//builder.setQuery(QueryBuilders.filteredQuery(QueryBuilders.boolQuery().
				//QueryBuilders.matchQuery("mydefine", "李慧勇")默认是一元分词，这里要设置不分词（精确搜索）
				//must(queryStringQueryBuilder).must(QueryBuilders.termQuery("mydefine", "张三人")), 
				//搜索范围
				//FilterBuilders.andFilter(FilterBuilders.boolFilter().should(FilterBuilders.termFilter("time", "2004")).should(FilterBuilders.termFilter("time", "2015"))/*,FilterBuilders.boolFilter().mustNot(FilterBuilders.termFilter("mydefine", "李慧勇"))*/,FilterBuilders.rangeFilter("square").from("110").to("200"),FilterBuilders.rangeFilter("time").from("2000").to("3006"))))
				//增加统计功能
				.addFacet(FacetBuilders.termsFacet("termFacet").fields("id"));
				
				
				
				
				//增加排序功能
				/*.addSort("time", SortOrder.ASC)*/;
						
				//增加排序功能
				if (!(StringUtils.isEmpty(sortField) || StringUtils.isEmpty(sortType))) {
	                SortOrder sortOrder="desc".equalsIgnoreCase(sortType)?SortOrder.DESC:SortOrder.ASC;
	                builder.addSort(sortField, sortOrder);
	            }
				SearchResponse responsesearch = builder.execute().actionGet();
				TermsFacet f = (TermsFacet) responsesearch.getFacets().facetsAsMap().get("termFacet");
				System.out.println("总数量："+f.getTotalCount());
				System.out.println(f.toString());
				System.out.println("-------华丽分割线-----------");
				SearchHits searchHists = responsesearch.getHits();
				for (SearchHit hit : searchHists) {
					System.out.println(hit.getSourceAsString());
					T t = (T) clazz.newInstance();
					t=JSON.toJavaObject(JSONObject.parseObject(hit.getSourceAsString()), clazz);
					result.add(t);
				}
				return result;
		}
		
		
		/**
		 * @Title: createRangeQueryBuilder
		 * @Description:过滤搜索范围，可同时过滤区间（must拼接）
		 * @param: @param field
		 * @param: @param values
		 * @param: @return   
		 * @return: RangeQueryBuilder   
		 * @throws
		 */
		public  RangeQueryBuilder createRangeQueryBuilder(String field, Object[] values) {
	        if (values.length == 1 || values[1] == null || values[1].toString().trim().isEmpty()) {
	            logger.warn("[区间搜索]必须传递两个值，但是只传递了一个值，所以返回null");
	            System.out.println("[区间搜索]必须传递两个值，但是只传递了一个值，所以返回null");
	            return null;
	        }
	        boolean timeType = false;
	        if (MySearchOption.isDate(values[0])) {
	            if (MySearchOption.isDate(values[1])) {
	                timeType = true;
	            }
	        }
	        String begin = "", end = "";
	        if (timeType) {
	            /*
	             * 如果时间类型的区间搜索出现问题，有可能是数据类型导致的：
	             * （1）在监控页面（elasticsearch-head）中进行range搜索，看看什么结果，如果也搜索不出来，则：
	             * （2）请确定mapping中是date类型，格式化格式是yyyy-MM-dd HH:mm:ss
	             * （3）请确定索引里的值是类似2012-01-01 00:00:00的格式
	             * （4）如果是从数据库导出的数据，请确定数据库字段是char或者varchar类型，而不是date类型（此类型可能会有问题）
	             * */
	            begin = MySearchOption.formatDate(values[0]);
	            end = MySearchOption.formatDate(values[1]);
	        }
	        else {
	            begin = values[0].toString();
	            end = values[1].toString();
	        }
	        return QueryBuilders.rangeQuery(field).from(begin).to(end);
	    }
		
		/**
		 * @Title: createFilterQueryBuilder
		 * @Description: TODO
		 * @param: @param field
		 * @param: @param values
		 * @param: @param mySearchOption
		 * @param: @param andFilterBuilder
		 * @param: @return   
		 * @return: AndFilterBuilder   
		 * @throws
		 */
		public AndFilterBuilder createFilterQueryBuilder(String field, Object[] values,MySearchOption mySearchOption,AndFilterBuilder andFilterBuilder) {
			System.out.println("================="+values.toString());
			BoolFilterBuilder boolFilterBuilder=FilterBuilders.boolFilter();
			
			if(mySearchOption.getSearchType().equals(MySearchOption.SearchType.term)){
            	if(mySearchOption.getSearchLogic().equals(MySearchOption.SearchLogic.must)){
            		boolFilterBuilder.must(FilterBuilders.termsFilter(field,StringUtils.split(mySearchOption.getQueryStringPrecision(), ",")));
            	}else if(mySearchOption.getSearchLogic().equals(MySearchOption.SearchLogic.mustNot)){
            		boolFilterBuilder.mustNot(FilterBuilders.termsFilter(field,StringUtils.split(mySearchOption.getQueryStringPrecision(), ",")));
            	}else if(mySearchOption.getSearchLogic().equals(MySearchOption.SearchLogic.should)){
            		boolFilterBuilder.should(FilterBuilders.termsFilter(field,StringUtils.split(mySearchOption.getQueryStringPrecision(), ",")));
            	}
            }
			return andFilterBuilder==null?FilterBuilders.andFilter(boolFilterBuilder):andFilterBuilder.add(boolFilterBuilder);
	    }
}
