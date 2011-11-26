package com.eklekt;

import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.*;

import com.google.gson.Gson;

@SuppressWarnings("serial")
public class EmoTeAPIServlet extends HttpServlet {

	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		Gson gson = new Gson();
		resp.setContentType("application/json");
		
		String text = req.getParameter("text");
		EmotionalStateWrapper emotions = EmotionalStateWrapper.feel(text);

		resp.setContentType("application/json");
		String callback = req.getParameter("callback");
		
		
		if (callback != null) { resp.getWriter().print(callback + "("); }
		resp.getWriter().print(gson.toJson(emotions));
		if (callback != null) { resp.getWriter().print(");"); }
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
