package DataStructures;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Set;

public class HashList<K, D> {
	private Hashtable<K, LinkedList<D>> table;

	public HashList(int size) {
		super();
		// TODO Auto-generated constructor stub

		table = new Hashtable<>(size);

	}
	
	public void addData(K key, D data){
		LinkedList<D> list;
		if((list = table.get(key)) == null){
			list = new LinkedList<D>();
		}
		list.add(data);
		table.put(key, list);
	}
	
	public Set<K> getKeySet(){
		return table.keySet();
	}
	
	public LinkedList<D> getList(K key){
		LinkedList<D> list;
		if((list = table.get(key)) != null){
			return list;
		}
		
		return null;
	}
	
}
