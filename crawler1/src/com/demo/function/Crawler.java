package com.demo.function;
/**
 * ��ȡ������ѧ�������гɼ�
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
		System.out.println("������ѧ�ţ�");
		String zjh = sc.next();
		System.out.println("���������루Ĭ����1����");
		String mm = sc.next();
		// �����û��������ȡcookie������������ҳ������֤,����ָ��
		String surl = "http://60.219.165.24/loginAction.do?zjh=" + zjh + "&mm="
				+ mm + "";
		// ����url���󣬾��ǿ���������ַ
		URL url = new URL(surl);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();

		// ��ȡcookie��������վ��֪��ʲôbug�������Ǹ�һ���ֺź�path�����Խ�ȡ
		int i = (connection.getHeaderField("Set-Cookie")).indexOf(";");
		String cookieVal = (String) (connection.getHeaderField("Set-Cookie"))
				.subSequence(0, i);
//		System.out.println(cookieVal);
		// ����cookie�򿪳ɼ�����ҳ
		String s = "http://60.219.165.24/gradeLnAllAction.do?type=ln&oper=fainfo&fajhh=3896";
		// ���´�һ������
		url = new URL(s);
		HttpURLConnection resumeConnection = (HttpURLConnection) url
				.openConnection();
		if (cookieVal != null) {
			// ����cookie��Ϣ��ȥ���Ա����Լ�����ݣ�����ᱻ��Ϊû��Ȩ��
			resumeConnection.setRequestProperty("Cookie", cookieVal);
		}
		resumeConnection.connect();
		// ����ͨ����������󹹽����������������ʵ����������л��Ķ���
		InputStream urlStream = resumeConnection.getInputStream();
		// ������վhtml�����������ģ����Բ���ʹ���ֽ�����ת�����ַ�������
		InputStreamReader u = new InputStreamReader(urlStream);
		int j = 0;
		StringBuffer str = new StringBuffer();
		while ((j = u.read()) != -1) {
			str.append((char) j);
		}
		/**
		 * ����Jsoup����html��ҳ��Jsoup���Դ�һ����ҳ��ȡhtml���������Ҳ����ֱ�ӽ�����ҳhtml���ַ���
		 * ��Jsoup���е�ѡ�񷽷�ɸѡ��ǩ
		 * 
		 */
		String html = str.toString();
		System.out.println("�γ���                ����");
		Document doc = Jsoup.parse(html);
		// ������ʽƥ��.��������ĸ���ֵĿγ���
		Pattern pattern = Pattern.compile("^[.������a-zA-Z\u4E00-\u9FA5��1234]+$");
		Elements links = doc.select("td[align]");
		Elements links1 = doc.select("p[align]");
		Map map = new HashMap();
		List<String> course = new LinkedList<String>();
		List<String> fenshu = new LinkedList<String>();
		// �����ɽڵ��������ɸѡ���������ı�ǩ��ȡֵ
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
		// ��վhtml�����p��ǩ�������ͳɼ���ǩ������֣���վ��������html�ո���룬���������Ǹ��������Խ�ȡ
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
		// ���������ֱ��γ����ͳɼ��ļ��ϣ�ȡֵ����map������
		Iterator it = course.iterator();
		Iterator it1 = fenshu.iterator();
		while (it.hasNext() && it1.hasNext()) {
			map.put(it.next(), it1.next());
		}
		// map���ϵı���Ҫת����set�����ڶ�set���Ͻ��б�������ȻҲ���Էֱ�Լ���ֵ����
		Set set = map.entrySet();
		Iterator it3 = set.iterator();
		while (it3.hasNext()) {
			System.out.println(it3.next());
		}
	}

}
