package com.lhy.utils.build;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import org.apache.lucene.queryparser.xml.builders.BooleanFilterBuilder;
import org.elasticsearch.index.query.AndFilterBuilder;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.lhy.utils.build.MySearchOption.DataFilter;
import com.lhy.utils.build.MySearchOption.SearchLogic;
import com.lhy.utils.build.MySearchOption.SearchType;

/**
 * @author: 李慧勇
 * @description:搜索条件拼接
 * @mail:jdlglhy@163.com
 * @2015年8月13日
 * @version 1.0
 */
public class QueryBuildFactory {
	
	
	private static Logger logger = LoggerFactory.getLogger(QueryBuildFactory.class);
	 /**
     * @Title: createRangeQueryBuilder
     * @Description: 拼接区间搜索条件（范围搜索）
     * @param: @param field
     * @param: @param values
     * @param: @return   
     * @return: RangeQueryBuilder   
     * @throws
     */
    public static RangeQueryBuilder createRangeQueryBuilder(String field, Object[] values) {
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
    /*
     * 创建过滤条件
     * */
    public static QueryBuilder createFilterBuilder(SearchLogic searchLogic, QueryBuilder queryBuilder, HashMap<String, Object[]> searchContentMap, HashMap<String, Object[]> filterContentMap) throws Exception
    {
        try {
            Iterator<Entry<String, Object[]>> iterator = searchContentMap.entrySet().iterator();
            AndFilterBuilder andFilterBuilder = null;
            while (iterator.hasNext()) {
                Entry<String, Object[]> entry = iterator.next();
                Object[] values = entry.getValue();
                /*排除非法的搜索值*/
                if (!checkValue(values)) {
                    continue;
                }
                MySearchOption mySearchOption =getSearchOption(values);
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
            QueryFilterBuilder queryFilterBuilder = FilterBuilders.queryFilter(createQueryBuilder(filterContentMap, searchLogic));
            /*构造not过滤条件，表示搜索结果不包含这些内容，而不是不过滤*/
            NotFilterBuilder notFilterBuilder = FilterBuilders.notFilter(queryFilterBuilder);
            return QueryBuilders.filteredQuery(queryBuilder, FilterBuilders.andFilter(andFilterBuilder, notFilterBuilder));
        }
        catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }
    /*
     * 创建搜索条件
     * */
    public static QueryBuilder createQueryBuilder(HashMap<String, Object[]> searchContentMap, SearchLogic searchLogic) {
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
                if (!checkValue(values)) {
                    continue;
                }
                /*获得搜索类型*/
                MySearchOption mySearchOption = getSearchOption(values);
                QueryBuilder queryBuilder =createSingleFieldQueryBuilder(field, values, mySearchOption);
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
            logger.error(e.getMessage());
        }
        return null;
    }
    public static QueryBuilder createSingleFieldQueryBuilder(String field, Object[] values, MySearchOption mySearchOption) {
        try {
            if (mySearchOption.getSearchType() == com.lhy.utils.build.MySearchOption.SearchType.range) {
                /*区间搜索*/
                return createRangeQueryBuilder(field, values);
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
            logger.error(e.getMessage());
        }
        return null;
    }
    /*简单的值校验*/
    public static boolean checkValue(Object[] values) {
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
    public static MySearchOption getSearchOption(Object[] values) {
        try {
            for (Object item : values) {
                if (item instanceof MySearchOption) {
                    return (MySearchOption) item;
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage());
        }
        return new MySearchOption();
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    /*----------------------------------华丽分割线--------------------------------------*/
    /*
     * 创建过滤条件
     * */
   /* public static QueryBuilder newCreateFilterBuilder(SearchLogic searchLogic, QueryBuilder queryBuilder, HashMap<String, Object[]> searchContentMap, HashMap<String, Object[]> filterContentMap) throws Exception
    {
        try {
            Iterator<Entry<String, Object[]>> iterator = searchContentMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Entry<String, Object[]> entry = iterator.next();
                Object[] values = entry.getValue();
                排除非法的搜索值
                if (!checkValue(values)) {
                    continue;
                }
                MySearchOption mySearchOption =getSearchOption(values);
                if (mySearchOption.getSearchType() == SearchType.range) {
                    	被搜索的条件必须有值
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
                如果没有其它过滤条件，返回
                return QueryBuilders.filteredQuery(queryBuilder, andFilterBuilder);
            }
            构造过滤条件
            QueryFilterBuilder queryFilterBuilder = FilterBuilders.queryFilter(newCreateQueryBuilder(filterContentMap, searchLogic));
            构造not过滤条件，表示搜索结果不包含这些内容，而不是不过滤
            //NotFilterBuilder notFilterBuilder = FilterBuilders.notFilter(queryFilterBuilder);
            return QueryBuilders.filteredQuery(queryBuilder, FilterBuilders.andFilter(andFilterBuilder, notFilterBuilder));
        }
        catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }*/
    public static QueryBuilder newCreateQueryBuilder(HashMap<String, Object[]> searchContentMap, SearchLogic searchLogic) {
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
                if (!checkValue(values)) {
                    continue;
                }
                /*获得搜索类型*/
                MySearchOption mySearchOption = getSearchOption(values);
                QueryBuilder queryBuilder =newCreateSingleFieldQueryBuilder(field, values, mySearchOption);
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
            logger.error(e.getMessage());
        }
        return null;
    }
    public static QueryBuilder newCreateSingleFieldQueryBuilder(String field, Object[] values, MySearchOption mySearchOption) {
        try {
            if (mySearchOption.getSearchType() == com.lhy.utils.build.MySearchOption.SearchType.range) {
                /*区间搜索*/
                return createRangeQueryBuilder(field, values);
            }
            //String[] fieldArray = field.split(",");/*暂时不处理多字段[field1,field2,......]搜索情况*/
           /* BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
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
                        如果搜索长度为1的非数字的字符串，格式化为通配符搜索，暂时这样，以后有时间改成multifield搜索，就不需要通配符了
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
            return boolQueryBuilder;*/
            return null;
        }
        catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }
}
