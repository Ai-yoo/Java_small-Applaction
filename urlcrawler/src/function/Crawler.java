package function;


import dao.UrlDao;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import queue.Queue;

import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;


public class Crawler extends Thread{
	private static volatile Queue q1 = new Queue();
	private static volatile Queue q2 = new Queue();
	private static volatile Set<String> set = new TreeSet<String>();
	private static volatile int i = 0;
	private static volatile Pattern pattern = Pattern.compile("^/html/+(.)+.html");
	private static volatile Pattern pattern0 = Pattern.compile("http://www.dytt8.net/html/+(.)+.html");
	private static volatile Pattern pattern1 = Pattern.compile("^ftp://+((.)+)+");

	public static void main(String[] args) {
		Document doc = null;
		try {
			long begin = System.currentTimeMillis();
			doc = Jsoup.connect("http://www.dytt8.net/").get();
			Elements links = doc.select("a[href]");
			for (Element link : links) {
				String linkHref = link.attr("href");
				if (pattern.matcher(linkHref).matches() == true
						|| pattern0.matcher(linkHref).matches() == true) {
					q1.insertQueue(linkHref);
					q2.insertQueue(linkHref);
					open("http://www.dytt8.net" + q1.outQueue());
				}
			}
			System.out.println("一共爬取" + q2.size() + "条链接");
			long end = System.currentTimeMillis();
			System.out.println("用时" + (end - begin) + "ms");
			System.out.println("一共" + set.size() + "条下载链接");
			long b = System.currentTimeMillis();
			for (String url : set) {
				UrlDao.add(url);
			}
			long e = System.currentTimeMillis();
			System.out.println("数据库用时"+(e-b)+"ms");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void open(String url) {
		Document doc = null;
		try {
			doc = Jsoup.connect(url).get();
			Elements links = doc.select("a[href]");
			for (Element link : links) {
				String linkHref = link.attr("href");
				if (pattern.matcher(linkHref).matches() == true
						|| pattern0.matcher(linkHref).matches() == true) {
					q1.insertQueue(linkHref);
					q2.insertQueue(linkHref);
					if (q2.contains(linkHref) == false) {
						open("http://www.dytt8.net" + q1.outQueue());
					}
				} else if (pattern1.matcher(linkHref).matches() == true) {
					System.out.println(linkHref);
					set.add(linkHref);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
