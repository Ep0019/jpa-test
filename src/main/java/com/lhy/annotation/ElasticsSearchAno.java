package com.lhy.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: 李慧勇
 * @description:自定义注解实现
 * isElasticsSearch:默认为true 不需要修改
 * isNeedIkSearch默认不使用ik分词,这时会使用standard（应该是一元分词）分词
 * isExactSearch默认不启动精确搜索，为true时启动精确搜索，这里需要设置mapping  "index": "not_analyzed",屏蔽掉默认的分词
 * @mail:jdlglhy@163.com
 * @2015年7月15日
 * @version 1.0
 */
@Target(value={ElementType.FIELD,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ElasticsSearchAno {
	
    boolean isElasticsSearch() default true;
    
    boolean isNeedIkSearch() default false;
    
    boolean isExactSearch() default false;
}
