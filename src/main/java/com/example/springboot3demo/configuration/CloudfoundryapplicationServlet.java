package com.example.springboot3demo.configuration;

import jakarta.servlet.GenericServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * CloudfoundryapplicationServlet
 *
 * @author HeQiang
 * @since 2022/11/28
 **/
@WebServlet(name = "CloudfoundryapplicationServlet", urlPatterns = "/cloudfoundryapplication")
public class CloudfoundryapplicationServlet extends GenericServlet {

	@Override
	public void service(ServletRequest servletRequest, ServletResponse servletResponse)
			throws ServletException, IOException {
		PrintWriter writer = servletResponse.getWriter();
		writer.write("OK");
		writer.flush();
	}

	@Override
	public void init() throws ServletException {
		super.init();
	}
}
