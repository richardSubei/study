package com.subei.classload;

import java.io.IOException;

public class ClassLoaderView {

	public static void main(String[] args) throws ClassNotFoundException, IOException {
		System.out.println("核心类库加载器：" + ClassLoaderView.class.getClassLoader().loadClass("java.lang.String").getClassLoader());
	
		System.out.println("扩展类加载器：" + ClassLoaderView.class.getClassLoader().loadClass("com.sun.nio.zipfs.ZipCoder").getClassLoader());
		
		System.out.println("应用程序类加载器：" + ClassLoaderView.class.getClassLoader().loadClass("com.subei.classload.ClassLoaderView").getClassLoader());
	
		System.out.println("应用程序类加载器父类：" + ClassLoaderView.class.getClassLoader().loadClass("com.subei.classload.ClassLoaderView").getClassLoader().getParent());
	
		System.out.println("应用程序类加载器的父类的父类" + ClassLoaderView.class.getClassLoader().loadClass("com.subei.classload.ClassLoaderView").getClassLoader().getParent().getParent());
	
		System.in.read();
	
	}
}
