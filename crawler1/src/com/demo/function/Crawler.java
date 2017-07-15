package com.demo.function;
/**
 * 爬取教务网学生的所有成绩
 */
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Crawler {
	public static void main(String[] args) throws UnsupportedEncodingException,
			IOException {
		Scanner sc = new Scanner(System.in);
		System.out.println("请输入学号：");
		String zjh = sc.next();
		System.out.println("请输入密码（默认是1）：");
		String mm = sc.next();
		// 带着用户名密码获取cookie，否则后面的网页不能验证,报空指针
		String surl = "http://60.219.165.24/loginAction.do?zjh=" + zjh + "&mm="
				+ mm + "";
		// 创建url对象，就是可以理解打开网址
		URL url = new URL(surl);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();

		// 获取cookie，由于网站不知道什么bug后面总是跟一个分号和path，所以截取
		int i = (connection.getHeaderField("Set-Cookie")).indexOf(";");
		String cookieVal = (String) (connection.getHeaderField("Set-Cookie"))
				.subSequence(0, i);
//		System.out.println(cookieVal);
		// 带着cookie打开成绩的网页
		String s = "http://60.219.165.24/gradeLnAllAction.do?type=ln&oper=fainfo&fajhh=3896";
		// 重新打开一个连接
		url = new URL(s);
		HttpURLConnection resumeConnection = (HttpURLConnection) url
				.openConnection();
		if (cookieVal != null) {
			// 发送cookie信息上去，以表明自己的身份，否则会被认为没有权限
			resumeConnection.setRequestProperty("Cookie", cookieVal);
		}
		resumeConnection.connect();
		// 现在通过输出流对象构建对象输出流对象，以实现输出可序列化的对象。
		InputStream urlStream = resumeConnection.getInputStream();
		// 由于网站html代码中有中文，所以不能使用字节流，转换成字符流处理
		InputStreamReader u = new InputStreamReader(urlStream);
		int j = 0;
		StringBuffer str = new StringBuffer();
		while ((j = u.read()) != -1) {
			str.append((char) j);
		}
		/**
		 * 利用Jsoup解析html网页，Jsoup可以打开一个网页获取html代码解析，也可以直接解析网页html的字符串
		 * 用Jsoup类中的选择方法筛选标签
		 * 
		 */
		String html = str.toString();
		System.out.println("课程名                分数");
		Document doc = Jsoup.parse(html);
		// 正则表达式匹配.、（）字母汉字的课程名
		Pattern pattern = Pattern.compile("^[.、（）a-zA-Z\u4E00-\u9FA5Ⅱ1234]+$");
		Elements links = doc.select("td[align]");
		Elements links1 = doc.select("p[align]");
		Map map = new HashMap();
		List<String> course = new LinkedList<String>();
		List<String> fenshu = new LinkedList<String>();
		// 解析成节点数组遍历筛选符合条件的标签，取值
		int i1 = 0;
		for (Element link : links) {
			String l = link.text();
			if (pattern.matcher(l).matches() == true && l.length() > 2) {
				// System.out.println(l);
				course.add(l);
				i1++;
			}
			// System.out.println(link.text());
		}
		// 网站html代码的p标签有两个和成绩标签交替出现，网站代码中有html空格代码，解析出来是个？，所以截取
		int i2 = 0;
		for (Element link : links1) {
			String l = link.text();
			if (i2 % 2 == 0) {
				// System.out.println(l.substring(0, l.length() - 1));
				fenshu.add(l.substring(0, l.length() - 1));
			}
			i2++;
		}
		// System.out.println(course.size());
		// System.out.println(fenshu.size());
		// 遍历两个分别存课程名和成绩的集合，取值放入map集合中
		Iterator it = course.iterator();
		Iterator it1 = fenshu.iterator();
		while (it.hasNext() && it1.hasNext()) {
			map.put(it.next(), it1.next());
		}
		// map集合的遍历要转换成set集合在对set集合进行遍历，当然也可以分别对键和值遍历
		Set set = map.entrySet();
		Iterator it3 = set.iterator();
		while (it3.hasNext()) {
			System.out.println(it3.next());
		}
	}

}
