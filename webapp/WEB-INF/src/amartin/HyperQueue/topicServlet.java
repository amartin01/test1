package amartin.HyperQueue;

import java.io.IOException;
import java.util.SortedSet;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class topicServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4025238390182768296L;
	private Broker broker = null;

    public void init(ServletConfig config) throws ServletException
    {
        int timeout = Integer.parseInt(config.getInitParameter("timeout"));
        
        broker = Broker.getInstance(timeout);
    }
    
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String name = request.getParameter("topic");
		broker.addQueue(name);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		SortedSet<String> topics = broker.listQueue();
		
		JsonArrayBuilder json = Json.createArrayBuilder();
		for (String topic: topics) {
			json.add(topic);
		}
		
		JsonArray jasonObj = json.build();
		response.getWriter().print(jasonObj);
	}
}
