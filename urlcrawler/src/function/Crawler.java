package function;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import queue.Queue;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;


public class Crawler {
	public static Queue q1 = new Queue();
	public static Queue q2 = new Queue();
	public static Set<String> set = new TreeSet<String>();
	public static int i = 0;

	public static void main(String[] args) {
		Document doc = null;
		try {
			long begin = System.currentTimeMillis();
			doc = Jsoup.connect("http://www.dytt8.net/index.htm").get();
			Elements links = doc.select("a[href]");
			for (Element link : links) {
				String linkHref = link.attr("href");
				Pattern pattern = Pattern.compile("^/html/+(.)+.html");
				Pattern pattern0 = Pattern
						.compile("http://www.dytt8.net/html/+(.)+.html");
				Pattern pattern1 = Pattern.compile("^ftp://+((.)+)+");
				if (pattern.matcher(linkHref).matches() == true
						|| pattern0.matcher(linkHref).matches() == true) {
					q1.insertQueue(linkHref);
					q2.insertQueue(linkHref);
					open("http://www.dytt8.net" + q1.outQueue());
				}
			}
			Iterator<String> it = set.iterator();
			// while(it.hasNext()){
			// String url=(String)it.next();
			// int last=url.lastIndexOf(".");
			// int last1=url.lastIndexOf("]");
			// // System.out.print(url.substring(last1+1, last)+"     ");
			// System.out.println(URLEncoders.encode(url,"utf-8"));
			// }
			System.out.println("一共爬取" + q2.size() + "条链接");
			long end = System.currentTimeMillis();
			System.out.println("用时" + (end - begin) + "ms");
			System.out.println("一共" + set.size() + "条下载链接");
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
				Pattern pattern = Pattern.compile("^/html/+(.)+.html");
				Pattern pattern0 = Pattern
						.compile("http://www.dytt8.net/html/+(.)+.html");
				Pattern pattern1 = Pattern.compile("^ftp://+((.)+)+");
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
