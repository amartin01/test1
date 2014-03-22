package amartin.HyperQueue;

import java.io.IOException;
import java.util.Hashtable;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class brokerServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4657686696042031798L;
	
	private int timeout = 5000;
	private Hashtable<String, Consumer> consumers = null;
	private Broker broker = null;

    public void init(ServletConfig config) throws ServletException
    {
        System.out.println("init");
        broker = new Broker(timeout);
        consumers = new Hashtable<String, Consumer>();
        
        broker.addMessage("a", "test1");
        broker.addMessage("a", "test2");
        broker.addMessage("a", "test3");
    }

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String name = request.getPathInfo().substring(1);
		String message = request.getParameter("message");
		broker.addMessage(name, message);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String name = request.getPathInfo().substring(1);
		Consumer consumer = getConsumer(request);
		String message = broker.getNext(name, consumer);
		
		response.addHeader("SESSION", consumer.getId());
		response.getWriter().print(message);
	}
	
	private Consumer getConsumer(HttpServletRequest request) {
		String sessionId = request.getHeader("SESSION");
		Consumer consumer = null;
		
		if (sessionId != null) {
			consumer = consumers.get(sessionId);
		}
		
		if (consumer == null) {
			consumer = new Consumer();
			consumers.put(consumer.getId(), consumer);
		}
		
		return consumer;
	}
}
