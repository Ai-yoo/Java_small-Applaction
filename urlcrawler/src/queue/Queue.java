package queue;

import java.util.LinkedList;
import java.util.List;

public class Queue {
	public int i=0;
	public List<String> list=new LinkedList<String>();
	public void insertQueue(String url){
		list.add(i, url);
		i++;
	}
	public String outQueue(){
		i--;
		String a=list.get(0);
		list.remove(0);
		return a;
	}
	public boolean contains(String url){
		return url.contains(url);
	}
	public int size(){
		return list.size();
	}
	public boolean isEmpty(){
		return list.isEmpty();
	}
}
