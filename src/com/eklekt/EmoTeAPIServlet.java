package com.eklekt;

import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.*;

import com.google.gson.Gson;

@SuppressWarnings("serial")
public class EmoTeAPIServlet extends HttpServlet {

	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		Gson gson = new Gson();
		String path = req.getPathInfo().toLowerCase();
		resp.setContentType("application/json");
		HashMap<String, String> responseObject = new HashMap<String, String>();
		
		if (path.equals("/store")) {
			try {
				String text = (req.getParameter("text")).toString();
				Cookie cookie = new Cookie("text", text);
				cookie.setMaxAge(3600);
				resp.addCookie(cookie);
				responseObject.put("status", "OK");
			}
			catch (Exception e) {
				responseObject.put("status", "error");
				responseObject.put("message", "please make sure cookies are enabled and your request contains the 'text' parameter");
			}
			finally {
				resp.getWriter().println(gson.toJson(responseObject));
			}
		}
		else if (path.startsWith("/jsonp")) {
			try {
				EmotionalStateWrapper emotions = null;
				for (Cookie cookie : req.getCookies()) {
					if (cookie.getName().equals("text")) {
						emotions = EmotionalStateWrapper.feel(cookie.getValue());
					}
				}
				
				String callback = req.getParameter("callback");
	
				
				if (callback != null) { resp.getWriter().print(callback + "("); }
				resp.getWriter().print(gson.toJson(emotions));
				if (callback != null) { resp.getWriter().print(");"); }
			}
			catch (Exception e) {
				responseObject.put("status", "error");
				responseObject.put("message", "please make sure cookies are enabled you have previously called the '/store' method");
				resp.getWriter().println(gson.toJson(responseObject));
			}
		}
	}
	
	//TODO: add cookie setting functionality to POST
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String text = req.getParameter("text");
		EmotionalStateWrapper emotions = EmotionalStateWrapper.feel(text);
		Gson gson = new Gson();
		
		resp.setContentType("application/json");
		resp.getWriter().println(gson.toJson(emotions));
	}
	
}
