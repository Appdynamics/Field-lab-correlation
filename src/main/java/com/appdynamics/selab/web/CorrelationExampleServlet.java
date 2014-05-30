package com.appdynamics.selab.web;

import com.appdynamics.selab.jms.MessageQueue;
import com.appdynamics.selab.jms.MessageSender;
import com.appdynamics.selab.jms.SimpleMessage;
import com.appdynamics.selab.jms.SimpleQueueReceiver;
import com.appdynamics.selab.tcpclient.TCPClient;
import com.appdynamics.selab.util.Constants;

import javax.jms.Message;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Enumeration;

public class CorrelationExampleServlet extends HttpServlet {
    
	private String greeting = "Hello World! This is an update.";;

	private static Random rand = new Random();

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		PrintWriter out = response.getWriter();

        try {
            Thread.sleep(30);
        }
        catch (Exception e) {
        }

        writeHeader(out);
		writeBody(out, request);
		writeFooter(out);
	}

    public void writeHeader(PrintWriter out) {

        try {
            Thread.sleep(30);
        }
        catch (Exception e) {
        }

		out.println("<html>");
	} 

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { 
		
		doGet(request, response);
	}
	
	public void writeBody(PrintWriter out, HttpServletRequest request) throws IOException { 

		String response;
        String jmsResponse = "Didn't even get to invoking JMS :-(";
		
		try {
            response = sendTCPMessage();
            jmsResponse = sendJMS(out, request.getServletContext());
		}
		catch (Exception e) { 
			response = "No response from the Server"; 
		}
		
		out.println("<body>"); 
        out.println("<p>" + greeting + " request.getQueryString() = " + request.getQueryString() + "</p>");
		out.println("<p>Server Response = " + response + "</p>");
        out.println(jmsResponse);

		out.println("</ul><p><form method=\"POST\" action=\"" + request.getRequestURI() + "\">" + "Field 1 <input name=\"Field1\" size=20><br>" + "Field 2 <input name=\"Field2\" size=20><br>" + "<br><input type=\"submit\" value=\"Submit\"></form>");
 
		this.showRequestParameters(request, out);
		out.println("</body>");

	} 

	public void showRequestParameters(HttpServletRequest request, PrintWriter out) throws IOException { 
		//response.setContentType("text/html"); 
		//PrintWriter out = response.getWriter();
		out.println("<h1>Parameters</h1><ul>");
		for ( Enumeration e=request.getParameterNames(); e.hasMoreElements(); ) {
			String name = (String)e.nextElement(); 
			String value = request.getParameter( name ); 
			if (! value.equals("") )
				out.println("<li>"+ name +" = "+ value );
		}
		out.close( ); 
	}

    private String sendTCPMessage() throws Exception {
        String result = null;

        try {
            TCPClient client = new TCPClient("localhost", 6789);
            result = client.sendMessage(Integer.toString(this.rand.nextInt()));
        } catch (Exception e) {
            result = e.getMessage();
            e.printStackTrace();
        }

        return result;
    }

    private String sendJMS(PrintWriter out, ServletContext context) {
        StringBuilder sb = new StringBuilder("<p>sending JMS</p>");
        MessageQueue queue = (MessageQueue) context.getAttribute(Constants.JMS_QUEUE);
        SimpleQueueReceiver queueRcvr = (SimpleQueueReceiver) context.getAttribute(Constants.JMS_QUEUE_RCVR);
        MessageSender sender = new MessageSender(queue, queueRcvr);
        Message m = new SimpleMessage();
        sender.send(m);
        sb.append(" <p>JMS message sent!</p>");

        return sb.toString();
    }

	public void writeFooter(PrintWriter out) { 

		out.println("</html>");
	} 

	public void destroy() { 

		greeting = null;
	} 
}
