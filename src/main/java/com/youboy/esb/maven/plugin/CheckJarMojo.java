package com.youboy.esb.maven.plugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import com.youboy.esb.maven.utils.LoadPropUtils;
import com.youboy.esb.maven.utils.WarUtils;

@Mojo(name = "checkJar")
public class CheckJarMojo extends AbstractMojo {

	@Parameter(property = "project.build.directory")
	private String directory;
	@Parameter(property = "project.packaging")
	private String packaging;
	
	private static boolean checkFail = false;

	public void execute() throws MojoExecutionException {
		// getLog().info("Say Hello by maven-plugin.");
		// String path = System.getProperty("java.class.path");
		// URL url = this.getClass().getResource("");
		// getLog().info("directory:" + directory);
		// getLog().info("packaging:"+packaging);

		File target = new File(directory);
		File[] files = target.listFiles();

		File packageFile = null;
		for (File file : files) {
			if (file.getName().indexOf(packaging) > -1) {
				packageFile = file;
				break;
			}
		}

		if (packageFile == null) {
			getLog().error("get package error");
			return;
		}/*
		 * else{ getLog().info("get package right"); }
		 */
		String packageDir = packageFile.getAbsolutePath();
		String unzipPath = packageDir.replaceAll("\\.war", "_checkJar");
		try {
			//清空老目录
			FileUtils.deleteDirectory(new File(unzipPath));
			//解压创建新目录
			WarUtils.unzip(packageDir, unzipPath);

			//jar分析
			String jarsDir = unzipPath + File.separator + "WEB-INF" + File.separator + "lib";
			//System.out.println("jarsDir:" + jarsDir);
			File jars = new File(jarsDir);

			for (String name : jars.list()) {
				// System.out.println("jar:"+name+",flag:"+checkJar(name));
				checkJar(name);
			}
			
			if(checkFail){
				throw new Exception("你所加的jar，版本不合法");
			}
		} catch (Exception e) {
			if (!(e instanceof FileNotFoundException)) {
				throw new MojoExecutionException(e.getMessage());
			}
			getLog().warn(e.getMessage());
		}finally{
			//清空老目录
			try {
				FileUtils.deleteDirectory(new File(unzipPath));
			} catch (IOException e) {
				//e.printStackTrace();
				getLog().warn("清空老目录"+unzipPath+"失败");
			}
		}
	}

	private boolean checkJar(String name) throws Exception {
		if (name.indexOf(".jar") < 1) {
			return true;
		}

		int index = name.lastIndexOf("-");
		String jarName = name.substring(0, index);
		String jarVersion = name.substring(index + 1).replaceAll("\\.jar", "");

		String jarKey = LoadPropUtils.get(jarName);
		if (jarKey == null) {
			return true;
		}

		// 在规范jar的范围内，版本不正确
		/*if (!jarKey.equals(jarVersion)) {
			getLog().error("你当前使用版本：" + name + ",正确版本为:" + jarKey);
			checkFail = true;
		}*/
		
		Pattern pattern = Pattern.compile(jarKey);
		if(!pattern.matcher(jarVersion).find()){
			getLog().error("你当前使用版本：" + name + ",正确版本为:" + jarKey);
			checkFail = true;
		}

		return true;
	}

}