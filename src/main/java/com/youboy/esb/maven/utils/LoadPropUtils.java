package com.youboy.esb.maven.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

import org.springframework.core.io.ClassPathResource;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/** 
 * @author hailin
 * @version 1.0
 * @date 2016年7月27日 下午3:42:38 
 * 类说明 :获取jar版本数据
 *      ps：打包后maven插件之后，读取classpath有问题，不能加载prop。（故换了方式）
 *      使用spring 使用的路径加载类
 */

public class LoadPropUtils {
	
	private static final String classTemplate = "dataClass.ftl";
	private static final String className = "CheckJarMap";
	private static final String propName = "/check-jars.properties";
	private static Properties prop;
	
	//你本地项目的路径
	private static final String proPath = "D:\\workspace_new\\esb.youboy.com\\esb-maven-plugin\\src\\main\\java";
	

	public static String get(String key) throws FileNotFoundException {
		/*if(CheckJarMap.map != null){
			return CheckJarMap.map.get(key);
		}*/
		if(prop == null){
			load();
		}
		return prop.getProperty(key);
	}
	
	private static void load() throws FileNotFoundException{
		try {
			ClassPathResource classPathResource = new ClassPathResource(propName);
			InputStream in = classPathResource.getInputStream();
			prop = new Properties();
			prop.load(in);
			
			/*for(Object obj :prop.values()){
				System.out.println(obj);
			}*/
		} catch (Exception e) {
			throw new FileNotFoundException("配置文件"+propName+"加载失败");
		}
	}
	
	
	
	/*
	 *  代码生成类
	 *  读取
	 */
	private static void createJavaFile(){
		Configuration cfg = new Configuration(); 
		String classpath = null;
	    try {
	    	URL url = Object.class.getClass().getResource("/");
	    	classpath = url.getPath().replaceAll("file:\\\\", "");
	    	File templatesFile = new File(classpath+"/META-INF/templates");
			cfg.setDirectoryForTemplateLoading(templatesFile);
			cfg.setObjectWrapper(new DefaultObjectWrapper());  
			
			//
			Template temp = cfg.getTemplate(classTemplate);  
			/* Create a data-model */  
	        Map<String, Object> root = new HashMap<String, Object>();  
	        root.put("class", className);  
	        Collection<Map<Object, Object>> properties = new HashSet<Map<Object, Object>>();  
	        root.put("properties", properties);  
	  
	       
	        Properties prop = loadProp();
	        for(Object key : prop.keySet()){
	        	Map<Object, Object> currency = new HashMap<Object, Object>(); 
	        	String value = prop.get(key).toString();
	        	//检查正则
	        	Pattern.compile(value);
	        	currency.put("key", key);
	        	/*
	        	 * 字符串中用2个反斜杠表示一个反斜杠。
				 * 正则表达式中都需要用4个反斜杠表示一个反斜杠 
	        	 **/
	        	String spritStr = "\\\\";
	        	currency.put("value", value.replaceAll(spritStr, spritStr+spritStr));
	        	
		        properties.add(currency);  
	        }
	  
	        /* Merge data-model with template */
	        String classFile =  proPath + "/com/youboy/esb/maven/utils/"+className+".java";
			OutputStream os = new FileOutputStream(classFile );
			Writer out = new OutputStreamWriter(os );  
	        temp.process(root, out);  
	        out.flush();  
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}  
	}
	
	private static Properties loadProp() throws IOException{
		InputStream in = Object.class.getClass().getResourceAsStream(propName);
		Properties prop = new Properties();
		prop.load(in);
		return prop;
	}
	
	public static void main(String[] args) {
		//createJavaFile();
		
		//System.out.println(CheckJarMap.map);
	}

	
}


