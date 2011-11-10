package com.eklekt;

import java.io.IOException;
import javax.servlet.http.*;
import synesketch.emotion.*;
import com.google.gson.Gson;

@SuppressWarnings("serial")
public class EmoTeAPIServlet extends HttpServlet {
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String text = (req.getParameter("text")).toString();
		
		Empathyscope empathyScope = Empathyscope.getInstance();
		EmotionalState emotions = empathyScope.feel(text);
		Gson gson = new Gson();
		
		resp.setContentType("application/json");
		resp.getWriter().println(gson.toJson(emotions));
	}
	
}
