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
		System.out.println("������ѧ�ţ�");
		String zjh = sc.next();
		// System.out.println("���������루Ĭ����1����");
		// String mm = sc.next();
		// �����û��������ȡcookie������������ҳ������֤,����ָ��
		String surl = "http://60.219.165.24/loginAction.do?zjh=" + zjh
				+ "&mm=1";
		// String surl =
		// "http://60.219.165.24/loginAction.do?zjh=2015025639&mm=1";
		// ����url���󣬾��ǿ���������ַ
		URL url = new URL(surl);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();

		// ��ȡcookie��������վ��֪��ʲôbug�������Ǹ�һ���ֺź�path�����Խ�ȡ
		int i = (connection.getHeaderField("Set-Cookie")).indexOf(";");
		String cookieVal = (String) (connection.getHeaderField("Set-Cookie"))
				.subSequence(0, i);
		// System.out.println(cookieVal);
		// ����cookie�򿪳ɼ�����ҳ
		String s = "http://60.219.165.24/bxqcjcxAction.do";
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
		Document doc = Jsoup.parse(html);
		Elements links = doc.select("tr[class]");
		// �����ɽڵ��������ɸѡ���������ı�ǩ��ȡֵ
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
