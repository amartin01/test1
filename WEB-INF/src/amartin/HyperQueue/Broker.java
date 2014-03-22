package amartin.HyperQueue;

import java.util.Hashtable;
import java.util.Map.Entry;

public class Broker {
	private Hashtable<String, MessageQueue> queues;
	
	public Broker(int timeout) {
		queues = new Hashtable<String, MessageQueue>();
		
		String[] names = {"a", "b", "c"};
		for (String name : names) {
			MessageQueue queue = new MessageQueue(timeout);
			queues.put(name, queue);
		}
	}
	
	public void addMessage(String name, String message) {
		if (queues.containsKey(name)) {
			MessageQueue queue = queues.get(name);
			
			queue.add(new Message(message));
		} else {
			System.out.println(name);
			System.out.println(message);
		}
	}
	
	public String getNext(String name, Consumer consumer) {
		String message = "";
		if (queues.containsKey(name)) {
			MessageQueue queue = queues.get(name);
			
			Entry<Integer, Message> entry = queue.getNext(consumer.getIndex(name));
			
			if (entry != null) {
				message = entry.getValue().toString();
				consumer.setIndex(name, entry.getKey());
			}
		}

		return message;
	}
}
