package amartin.HyperQueue;

import java.util.Hashtable;
import java.util.UUID;

public class Consumer {
	private Hashtable<String, Integer> indexes;
	private String id;
	
	public Consumer() {
		indexes = new Hashtable<String, Integer>();
		id = UUID.randomUUID().toString();
		
	}
	
	public void setIndex(String name, int index) {
		indexes.put(name, index);
	}
	
	public int getIndex(String name) {
		int index = 0;
		if (indexes.containsKey(name)) {
			index = indexes.get(name);
		}
		
		return index;
	}
	
	public String getId() {
		return id;
	}
}
