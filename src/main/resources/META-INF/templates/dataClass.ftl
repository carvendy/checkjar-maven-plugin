package com.youboy.esb.maven.utils;

import java.util.HashMap;
import java.util.Map;

public class ${class}{  
  
   public static Map<String, String> map = new HashMap<String, String>();
  
   static{
   	  <#list properties as prop>
   	   	 map.put("${prop.key}" , "${prop.value}");
   	  </#list>  
   }
   
}  