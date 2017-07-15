package com.demo.function;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
/**
 * 利用java用系统默认浏览器打开指定网址
 * @author asus
 *
 */
public class Demo1 {
	public static void main(String[] args) throws IOException, URISyntaxException {
		URI uri = new URI("http://blog.csdn.net/l1028386804");  
        Desktop.getDesktop().browse(uri);  
	}

}
