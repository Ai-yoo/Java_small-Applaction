package selectdegree;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Ben {
	public static void main(String[] args) throws UnsupportedEncodingException,
			IOException {
		Scanner sc = new Scanner(System.in);
		System.out.println("请输入学号：");
		String zjh = sc.next();
		// System.out.println("请输入密码（默认是1）：");
		// String mm = sc.next();
		// 带着用户名密码获取cookie，否则后面的网页不能验证,报空指针
		String surl = "http://60.219.165.24/loginAction.do?zjh=" + zjh
				+ "&mm=1";
		// String surl =
		// "http://60.219.165.24/loginAction.do?zjh=2015025639&mm=1";
		// 创建url对象，就是可以理解打开网址
		URL url = new URL(surl);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();

		// 获取cookie，由于网站不知道什么bug后面总是跟一个分号和path，所以截取
		int i = (connection.getHeaderField("Set-Cookie")).indexOf(";");
		String cookieVal = (String) (connection.getHeaderField("Set-Cookie"))
				.subSequence(0, i);
		// System.out.println(cookieVal);
		// 带着cookie打开成绩的网页
		String s = "http://60.219.165.24/bxqcjcxAction.do";
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
		Document doc = Jsoup.parse(html);
		Elements links = doc.select("tr[class]");
		// 解析成节点数组遍历筛选符合条件的标签，取值
		List list = null;
		Set<String> set = new HashSet<String>();
		for (Element link : links) {
			list = link.childNodes();
			for (int i1 = 0; i1 < list.size(); i1++) {
				Degree d = new Degree();
				String name = list.get(5).toString();
				String degree = list.get(13).toString();
				int begin = name.indexOf(">");
				int end = name.lastIndexOf("<");
				int begin1 = degree.indexOf(">");
				int end1 = degree.lastIndexOf("<");
				d.setName(name.substring(begin, end));
				d.setDegree(degree.substring(begin1 + 1, end1));
				set.add(d.toString());
			}
		}
		for (String string : set) {
			System.out.println(string);
		}
//		for (Object f : s1) {
//			System.out.println(f.toString());
//		}
	}
}
