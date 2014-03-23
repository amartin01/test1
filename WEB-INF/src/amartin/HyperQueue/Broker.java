package amartin.HyperQueue;

import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

public class Broker {
	private ConcurrentHashMap<String, MessageQueue> queues;
	private int timeout = 0;
	private static Broker instance = null;
	
	public Broker(int timeout) {
		this.timeout = timeout;
		
		queues = new ConcurrentHashMap<String, MessageQueue>();
	}
	
	public static Broker getInstance(int timeout) {
		if (instance == null) {
			instance = new Broker(timeout);
		}
		
		return instance;
	}
	
	public void addMessage(String name, String message) {
		if (queues.containsKey(name)) {
			MessageQueue queue = queues.get(name);
			queue.add(new Message(message));
		}
	}
	
	public String getNext(String name, Consumer consumer) {
		String message = "";
		
		MessageQueue queue = getQueue(name);
		
		Entry<Integer, Message> entry = queue.getNext(consumer.getIndex(name));
		
		if (entry != null) {
			message = entry.getValue().toString();
			consumer.setIndex(name, entry.getKey());
		}
		
		return message;
	}
	
	private MessageQueue getQueue(String name) {
		MessageQueue queue = null;
	
		if (queues.containsKey(name)) {
			queue = queues.get(name);
		} else {
			queue = addQueue(name);
		}
		
		return queue;
	}
	
	public MessageQueue addQueue(String name) {
		MessageQueue queue = new MessageQueue(timeout);
		queues.put(name, queue);
		return queue;
	}
	
	public SortedSet<String> listQueue() {
		Set<String> keys =  queues.keySet();
		SortedSet<String> sortedkeys = new TreeSet<String>(keys);
		return sortedkeys;
	}
}
