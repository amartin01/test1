package amartin.HyperQueue;

import java.util.Map.Entry;
import java.util.concurrent.ConcurrentSkipListMap;

public class MessageQueue {
	private ConcurrentSkipListMap<Integer, Message> list;
	private int timeout = 0;
	private int index = 0;
	
	public MessageQueue(int timeout) {
		this.timeout = timeout;
		list = new ConcurrentSkipListMap<Integer, Message>();
	}
	
	public Entry<Integer, Message> getNext(int offset) {
		Entry<Integer, Message> entry = null;
		
		if (offset == -1) {
			entry = list.lastEntry();
		} else {
			entry = list.higherEntry(offset);
		}
		
		if (null == entry) {
			
			waitNext();
			
			if (offset == -1) {
				entry = list.lastEntry();
			} else {
				entry = list.higherEntry(offset);
			}
		}
		
		return entry;
	}
	
	private void waitNext() {
		synchronized (list){
			try {
				list.wait(timeout);
			} catch (InterruptedException e) {
				// do nothing
			}
		}
	}
	
	public void add(Message message) {
		synchronized (list){
			list.put(++index, message);
			list.notifyAll();
		}
	}
}
