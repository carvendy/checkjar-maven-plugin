package com.youboy.esb.maven.utils;

import java.util.HashMap;
import java.util.Map;

public class CheckJarMap{  
  
   public static Map<String, String> map = new HashMap<String, String>();
  
   static{
   	   	 map.put("spring-core" , "3.1.\\d{1,2}.RELEASE");
   	   	 map.put("cxf-rt-rs-client" , "3.1.6");
   	   	 map.put("spring-beans" , "3.1.\\d{1,2}.RELEASE");
   	   	 map.put("cxf-rt-frontend-jaxrs" , "3.1.6");
   	   	 map.put("spring-context-support" , "3.1.\\d{1,2}.RELEASE");
   	   	 map.put("spring-test" , "3.1.\\d{1,2}.RELEASE");
   	   	 map.put("cxf-rt-frontend-jaxws" , "3.1.6");
   	   	 map.put("mysql-connector-java" , "5.1.8");
   	   	 map.put("spring-tx" , "3.1.\\d{1,2}.RELEASE");
   	   	 map.put("spring-orm" , "3.1.\\d{1,2}.RELEASE");
   	   	 map.put("log4j" , "1.2.17");
   	   	 map.put("aspectjweaver" , "1.6.12");
   	   	 map.put("cxf-rt-features-clustering" , "3.1.6");
   	   	 map.put("spring-webmvc" , "3.1.\\d{1,2}.RELEASE");
   	   	 map.put("spring-aop" , "3.1.\\d{1,2}.RELEASE");
   	   	 map.put("mybatis-spring" , "1.2.2");
   	   	 map.put("spring-web" , "3.1.\\d{1,2}.RELEASE");
   	   	 map.put("cxf-rt-transports-http" , "3.1.6");
   	   	 map.put("mybatis" , "3.1.0");
   	   	 map.put("memcached" , "2.6.3");
   	   	 map.put("aspectjrt" , "1.6.12");
   	   	 map.put("cxf-rt-rs-extension-providers" , "3.1.6");
   }
   
}  