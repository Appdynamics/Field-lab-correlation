import java.io.*;
import java.util.*; 
import javax.servlet.*;
import javax.servlet.http.*;

public class EUMExampleServlet extends HttpServlet { 
	
	public void init() { 
		
	
	
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { 
	
		ResourceBundle rb = ResourceBundle.getBundle("LocalStrings", request.getLocale()); 
		
		response.setContentType("text/html"); 
		
		PrintWriter out = response.getWriter(); 
		
		out.println("<html>"); 
		out.println("<head>"); 
		
		String title = rb.getString("helloworld.title"); 
		
		out.println("<title>" + title + "</title>"); 
		
		if (request.getAttribute("AppDynamics_JS_HEADER") != null) { 
			out.println(request.getAttribute("AppDynamics_JS_HEADER")); 
		}
		else { 
			out.println("<p>AppD EUM header not found</p>"); 
		}
		
		out.println("</head>"); 
		out.println("<body bgcolor=\"white\">");
		
		if (request.getAttribute("AppDynamics_JS_FOOTER") != null) { 
			out.println(request.getAttribute("AppDynamics_JS_FOOTER").toString()); 
		}
		else { 
			out.println("<p>AppD EUM footer not found</p>"); 
		}
		
		out.println("</body>"); 
		out.println("</html>"); 	
	}
	
}
